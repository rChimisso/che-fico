package com.kreinto.chefico.components.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuItem(
  label: String,
  onClick: (() -> Unit)? = null,
  content: (@Composable () -> Unit)? = null
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(60.dp)
      .padding(16.dp)
      .clickable(enabled = onClick != null, onClick = onClick ?: {}),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(label)
    if (content != null) {
      content()
    }
  }
}