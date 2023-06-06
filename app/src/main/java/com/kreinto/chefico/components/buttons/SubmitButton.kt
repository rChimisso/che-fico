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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Standard submit button.
 *
 * @param text
 * @param enabled
 * @param onClick
 */
@Composable
fun SubmitButton(
  text: String,
  textOnly: Boolean = false,
  isDanger: Boolean = false,
  enabled: Boolean = true,
  onClick: () -> Unit
) {
  TextButton(
    enabled = enabled,
    colors = ButtonDefaults.buttonColors(
      containerColor = if (textOnly) Color.Transparent else MaterialTheme.colorScheme.primary,
      contentColor = if (isDanger) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary
    ),
    contentPadding = PaddingValues(0.dp),
    shape = RoundedCornerShape(12.dp),
    modifier = Modifier
      .width(208.dp)
      .height(40.dp),
    onClick = onClick
  ) {
    Text(text, fontSize = 16.sp)
  }
}

/**
 * [Preview] for [SubmitButton].
 */
@Composable
@Preview
private fun SubmitButtonPreview() {
  SubmitButton("Preview") {}
}