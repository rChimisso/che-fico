package com.kreinto.chefico.components.frames

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.components.frames.topbars.SimpleTopBar

/**
 * Simple Frame following Material3 guidelines.
 *
 * @param bottomBar Optional overlayed [SimpleBottomBar] to display.
 * @param content [Composable] to display as main content.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun SimpleFrame(
  bottomBar: @Composable () -> Unit = {},
  content: @Composable (ColumnScope.() -> Unit)
) {
  Scaffold(
    topBar = { SimpleTopBar() },
    bottomBar = bottomBar,
    content = {
      Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        content = content
      )
    }
  )
}

/**
 * [Preview] for [SimpleFrame].
 */
@ExperimentalMaterial3Api
@Composable
@Preview
private fun SimpleFramePreview() {
  SimpleFrame {}
}