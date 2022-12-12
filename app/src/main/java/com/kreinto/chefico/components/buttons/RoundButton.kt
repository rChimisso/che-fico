package com.kreinto.chefico.components.buttons

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Round button.
 *
 * @param icon [Icon][ImageVector] to display.
 * @param tint Icon color.
 * @param contentDescription Text used by accessibility services to describe what this icon
 * represents. This should always be provided unless this icon is used for decorative purposes,
 * and does not represent a meaningful action that a user can take. This text should be localized.
 * @param onClick Function called when this button is clicked.
 */
@Composable
fun RoundButton(
  icon: ImageVector,
  tint: Color = Color(0xff4caf50),
  contentDescription: String,
  onClick: () -> Unit
) {
  Surface(
    shape = CircleShape,
    shadowElevation = 12.dp
  ) {
    SimpleButton(
      icon = icon,
      tint = tint,
      contentDescription = contentDescription,
      onClick = onClick
    )
  }
}

/**
 * [Preview] for [RoundButton].
 */
@Composable
@Preview()
private fun RoundButtonPreview() {
  RoundButton(icon = Icons.Default.Add, contentDescription = "", onClick = {})
}