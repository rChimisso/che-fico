package com.kreinto.chefico.components.items

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.R

/**
 * Standard menu item.
 *
 * @param text
 * @param onClick
 * @param content
 */
@Composable
fun MenuItem(@StringRes text: Int, onClick: (() -> Unit)? = null, content: (@Composable () -> Unit)? = null) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(64.dp)
      .clickable(enabled = onClick != null, onClick = onClick ?: {})
      .padding(16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(stringResource(text))
    if (content != null) {
      content()
    }
  }
}

/**
 * [Preview] for [MenuItem].
 */
@Composable
@Preview
private fun MenuItemPreview() {
  MenuItem(R.string.preview)
}
