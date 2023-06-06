package com.kreinto.chefico.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubmitButton(
  text: String,
  enabled: Boolean = true,
  onClick: () -> Unit
) {
  TextButton(
    enabled = enabled,
    colors = ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary
    ),
    contentPadding = PaddingValues(0.dp),
    shape = RoundedCornerShape(12.dp),
    modifier = Modifier
      .width(208.dp)
      .height(40.dp),
    onClick = onClick
  ) {
    Text(text = text, fontSize = 16.sp)
  }
}