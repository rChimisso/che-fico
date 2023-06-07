package com.kreinto.chefico.components.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.R
import com.kreinto.chefico.ui.theme.*

/**
 * Standard submit button.
 *
 * @param text
 * @param textOnly
 * @param isDanger
 * @param enabled
 * @param onClick
 */
@Composable
fun SubmitButton(
  @StringRes text: Int,
  textOnly: Boolean = false,
  isDanger: Boolean = false,
  enabled: Boolean = true,
  onClick: () -> Unit
) {
  TextButton(
    enabled = enabled,
    colors = ButtonDefaults.buttonColors(
      if (textOnly) Color.Transparent else MaterialTheme.colorScheme.primary,
      if (isDanger) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary
    ),
    contentPadding = PaddingValues(PaddingNone),
    shape = MaterialTheme.shapes.small,
    modifier = Modifier
      .padding(PaddingLarge)
      .width(WideButtonWidth)
      .height(InteractSizeMedium),
    onClick = onClick
  ) {
    Text(stringResource(text), fontSize = LabelLarge)
  }
}

/**
 * [Preview] for [SubmitButton].
 */
@Composable
@Preview
private fun SubmitButtonPreview() {
  SubmitButton(R.string.preview) {}
}