package com.kreinto.chefico.components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
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
 * Filled icon button.
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
fun FilledButton(
  @DrawableRes icon: Int,
  contentDescription: String,
  colors: IconButtonColors = IconButtonDefaults.filledIconButtonColors(),
  height: Dp = 40.dp,
  width: Dp = 40.dp,
  enabled: Boolean = true,
  onClick: () -> Unit
) {
  FilledTonalIconButton(
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
 * Filled icon button.
 *
 * @param buttonData [ButtonData].
 */
@Composable
fun FilledButton(buttonData: ButtonData) {
  FilledButton(
    icon = buttonData.icon,
    contentDescription = buttonData.contentDescription,
    colors = buttonData.colors ?: IconButtonDefaults.filledIconButtonColors(),
    height = buttonData.height,
    width = buttonData.width,
    enabled = buttonData.enabled,
    onClick = buttonData.onClick
  )
}

/**
 * [Preview] for [FilledButton].
 */
@Composable
@Preview
private fun FilledButtonPreview() {
  FilledButton(
    icon = R.drawable.ic_close,
    contentDescription = "Filled Button Preview"
  ) {}
}
