package com.kreinto.chefico.components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.data.ButtonData

/**
 * Multipurpose icon button.
 *
 * @param icon
 * @param contentDescription
 * @param colors
 * @param height
 * @param width
 * @param enabled
 * @param onClick
 */
@Composable
fun TransparentButton(
  @DrawableRes icon: Int,
  contentDescription: String,
  colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
  height: Dp = 40.dp,
  width: Dp = 40.dp,
  enabled: Boolean = true,
  onClick: () -> Unit
) {
  IconButton(
    modifier = Modifier
      .height(height)
      .width(width),
    enabled = enabled,
    colors = colors,
    onClick = onClick
  ) {
    Icon(
      painter = painterResource(icon),
      contentDescription = contentDescription,
      modifier = Modifier.size(24.dp)
    )
  }
}

@Composable
fun TransparentButton(buttonData: ButtonData) {
  TransparentButton(
    icon = buttonData.icon,
    contentDescription = buttonData.contentDescription,
    colors = buttonData.colors ?: IconButtonDefaults.iconButtonColors(),
    height = buttonData.height,
    width = buttonData.width,
    enabled = buttonData.enabled,
    onClick = buttonData.onClick
  )
}

/**
 * [Preview] for [TransparentButton].
 */
@Composable
@Preview
private fun TransparentButtonPreview() {
  TransparentButton(
    icon = R.drawable.ic_close,
    contentDescription = "Transparent Button Preview"
  ) {}
}