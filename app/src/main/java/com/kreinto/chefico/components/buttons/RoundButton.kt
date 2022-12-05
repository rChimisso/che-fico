package com.kreinto.chefico.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun RoundButton(icon: ImageVector, tint: Color = Color.Green) {
  IconButton(
    modifier = Modifier.then(Modifier.size(50.dp)).clip(CircleShape),
    onClick = {

    },
  ) {
    Icon(
      imageVector = icon,
      contentDescription =  "",
      tint = tint
    )
  }
}