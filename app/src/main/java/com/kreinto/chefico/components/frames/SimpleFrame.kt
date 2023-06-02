package com.kreinto.chefico.components.frames

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.components.frames.topbars.SimpleTopBar

/**
 * Simple Frame following Material3 guidelines.
 *
 * @param onNavigate Function called when back button is clicked.
 * @param bottomBar Optional overlayed bottom bar to display.
 * @param content [Composable] to display as main content.
 */
@ExperimentalMaterial3Api
@Composable
fun SimpleFrame(
  onNavigate: (String) -> Unit,
  bottomBar: @Composable () -> Unit = {},
  content: @Composable ((PaddingValues) -> Unit)
) {
  Scaffold(
    topBar = { SimpleTopBar(onNavigate) },
    bottomBar = bottomBar,
    content = content
  )
}

/**
 * [Preview] for [SimpleFrame].
 */
@ExperimentalMaterial3Api
@Composable
@Preview
private fun SimpleFramePreview() {
  SimpleFrame({}) {}
}
