package com.kreinto.chefico.components.frames

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.components.frames.topbars.StandardTopBar

/**
 * Standard Frame following Material3 guidelines.
 *
 * @param showBackAction Whether to show the [IconButton] to go back.
 * @param showSettingsAction Whether to show the [IconButton] to go open the app settings.
 * @param title [Composable] to show at [StandardTopBar] center.
 * @param content [Composable] to display as main content.
 */
@ExperimentalMaterial3Api
@Composable
fun StandardFrame(
  showBackAction: Boolean = true,
  showSettingsAction: Boolean = false,
  title: @Composable () -> Unit,
  content: @Composable (ColumnScope.() -> Unit)
) {
  Scaffold(
    topBar = {
      StandardTopBar(
        showBackAction = showBackAction,
        showSettingsAction = showSettingsAction,
        title = title
      )
    },
    bottomBar = {},
    content = {
      Column(
        modifier = Modifier.padding(it),
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
fun StandardFramePreview() {
  StandardFrame(title = {}) {}
}