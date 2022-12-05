package com.kreinto.chefico.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
  content: @Composable () -> Unit
) {
  Surface(
    elevation = 10.dp,
    modifier = Modifier
      .fillMaxWidth()
      .height(60.dp)
  ) {
    content()
  }
}