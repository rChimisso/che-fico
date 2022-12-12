package com.kreinto.chefico.components.frames

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.components.frames.topbars.StandardTopBar

/**
 * Standard Frame following Material3 guidelines.
 *
 * @param isDashboard Whether this top bar is for the dashboard. Controls whether to show the
 * "Go back" icon or the "Settings" icon.
 * @param onClick Function called when the top bar button is clicked.
 * @param title [Composable] to show at [StandardTopBar] center.
 * @param bottomBar Optional overlayed [SimpleBottomBar] to display.
 * @param content [Composable] to display as main content.
 */
@ExperimentalMaterial3Api
@Composable
fun StandardFrame(
  isDashboard: Boolean = false,
  onClick: () -> Unit,
  title: @Composable () -> Unit,
  bottomBar: @Composable () -> Unit = {},
  content: @Composable (ColumnScope.() -> Unit)
) {
  Scaffold(
    topBar = {
      StandardTopBar(
        isDashboard = isDashboard,
        title = title,
        onClick = onClick
      )
    },
    bottomBar = bottomBar,
    content = {
      Column(
        modifier = Modifier
          .padding(
            top = it.calculateTopPadding(),
            bottom = if (isDashboard) it.calculateBottomPadding() else 0.dp
          )
          .verticalScroll(rememberScrollState()),
        content = content
      )
    }
  )
}

/**
 * [Preview] for [StandardFramePreview].
 */
@ExperimentalMaterial3Api
@Composable
@Preview
private fun StandardFramePreview() {
  StandardFrame(title = {}, onClick = {}) {}
}
