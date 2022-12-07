package com.kreinto.chefico.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppBottomBar(
  actions: @Composable () -> Unit
) {
  Surface(
    elevation = 12.dp,
    modifier = Modifier
      .fillMaxWidth()
      .height(56.dp)
  ) {
    Row(
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically
    ) {
      actions()
    }
  }

}
