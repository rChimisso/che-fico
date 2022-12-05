package com.kreinto.chefico.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RoundButton(
  icon: ImageVector,
  tint: Color = Color.DarkGray,
  contentDescriptor: String = "",
  size: Dp = 32.dp,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  IconButton(
    modifier = modifier
      .clip(CircleShape)
      .background(Color.White),
    onClick = onClick,
  ) {
    Icon(
      modifier = Modifier.size(size),
      imageVector = icon,
      contentDescription = contentDescriptor,
      tint = tint
    )
  }
}