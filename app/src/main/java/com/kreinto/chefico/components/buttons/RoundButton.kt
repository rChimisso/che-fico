package com.kreinto.chefico.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoundButton(
  icon: ImageVector,
  modifier: Modifier = Modifier,
  tint: Color = Color(0xff4caf50),
  contentDescriptor: String = "",
  onClick: () -> Unit,
  color: Color = Color.White,
) {
  Surface(
    shape = CircleShape,
    shadowElevation = 12.dp
  ) {
    IconButton(
      onClick = onClick,
      modifier = Modifier.size(40.dp)
    ) {
      Icon(
        imageVector = icon,
        contentDescription = contentDescriptor,
        tint = tint,
      )
    }
  }
}

@Composable
@Preview()
fun RoundButtonPreview() {
  RoundButton(icon = Icons.Default.Add, onClick = {})
}