package com.kreinto.chefico.components.frames

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.components.frames.topbars.StandardTopBar

/**
 * Standard Frame following Material3 guidelines.
 *
 * @param onNavigate Function called when the top bar navigation button is clicked.
 * @param title [Composable] to show at [StandardTopBar] center.
 * @param actions list of actions.
 * @param bottomBar Optional overlayed bottom bar to display.
 * @param content [Composable] to display as main content.
 */
@ExperimentalMaterial3Api
@Composable
fun StandardFrame(
  onNavigate: (String) -> Unit,
  title: @Composable () -> Unit,
  actions: @Composable (RowScope.() -> Unit) = {},
  bottomBar: @Composable () -> Unit = {},
  content: @Composable ((PaddingValues) -> Unit)
) {
  Scaffold(topBar = { StandardTopBar(onNavigate, title, actions) }, bottomBar = bottomBar, content = content)
}

/**
 * [Preview] for [StandardFramePreview].
 */
@ExperimentalMaterial3Api
@Composable
@Preview
private fun StandardFramePreview() {
  StandardFrame({}, {}) {}
}
