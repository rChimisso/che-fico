package com.kreinto.chefico.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.components.data.ButtonData

/**
 * Simple transparent button.
 *
 * @param buttonData [ButtonData].
 */
@Composable
fun SimpleButton(buttonData: ButtonData) {
  IconButton(
    modifier = Modifier.size(40.dp),
    onClick = buttonData.onClick
  ) {
    Icon(
      imageVector = buttonData.icon,
      contentDescription = buttonData.contentDescription,
      tint = buttonData.tint
    )
  }
}

/**
 * [Preview] for [SimpleButton].
 */
@Composable
@Preview
private fun SimpleButtonPreview() {
  SimpleButton(
    ButtonData(
      icon = Icons.Default.Add,
      contentDescription = "Simple button",
      onClick = {}
    )
  )
}
