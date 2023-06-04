package com.kreinto.chefico.components.misc

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Standard fullscreen loader.
 *
 * @param visible
 */
@Composable
fun Loader(visible: Boolean) {
  AnimatedVisibility(visible, Modifier.fillMaxSize(), EnterTransition.None, fadeOut()) {
    CircularProgressIndicator(
      Modifier
        .background(MaterialTheme.colorScheme.background)
        .wrapContentSize()
    )
  }
}

/**
 * [Preview] for [Loader].
 */
@Composable
@Preview
private fun LoaderPreview() {
  Loader(true)
}
