package com.kreinto.chefico.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.components.data.ButtonData
import com.kreinto.chefico.components.data.ButtonLeadingData

/**
 * Simple transparent button.
 *
 * @param buttonData [ButtonData].
 */
@Composable
fun LeadingButton(buttonData: ButtonLeadingData) {
  IconButton(
    modifier = Modifier.size(40.dp),
    onClick = buttonData.onClick
  ) {
    Icon(
      imageVector = buttonData.icon,
      contentDescription = buttonData.contentDescription,
      tint = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}

/**
 * [Preview] for [SimpleButton].
 */
@Composable
@Preview
private fun LeadingButtonPreview() {
  LeadingButton(
    ButtonLeadingData(
      icon = Icons.Default.Add,
      contentDescription = "LeadingButton",
      onClick = {}
    )
  )
}
