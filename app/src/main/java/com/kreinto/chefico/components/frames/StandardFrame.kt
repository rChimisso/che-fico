package com.kreinto.chefico.components.frames

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.components.frames.topbars.StandardTopBar

/**
 * Standard Frame following Material3 guidelines.
 *
 * @param isDashboard Whether this top bar is for the dashboard. Controls whether to show the
 * "Go back" icon or the "Settings" icon.
 * @param onClick Function called when the top bar button is clicked.
 * @param title [Composable] to show at [StandardTopBar] center.
 * @param bottomBar Optional overlayed bottom bar to display.
 * @param content [Composable] to display as main content.
 */
@ExperimentalMaterial3Api
@Composable
fun StandardFrame(
  isDashboard: Boolean = false,
  onClick: () -> Unit,
  title: @Composable () -> Unit,
  bottomBar: @Composable () -> Unit = {},
  content: @Composable ((PaddingValues) -> Unit)
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
    content = content
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
