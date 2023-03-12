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
 * Transparent icon button.
 *
 * @param icon Icon to display.
 * @param contentDescription Text used by accessibility services to describe what the icon button represents.
 * @param colors [IconButtonColors].
 * @param height Button height.
 * @param width Button width.
 * @param enabled Whether the button is enabled.
 * @param onClick Callback called when the button is clicked.
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

/**
 * Transparent icon button.
 *
 * @param buttonData [ButtonData].
 */
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
