package com.kreinto.chefico.components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
 * @param iconColor Optional Icon color.
 * @param height Button height.
 * @param width Button width.
 * @param enabled Whether the button is enabled.
 * @param onClick Callback called when the button is clicked.
 */
@Composable
fun FilledButton(
  @DrawableRes icon: Int,
  contentDescription: String? = null,
  iconColor: Color? = null,
  width: Dp = 40.dp,
  height: Dp = width,
  enabled: Boolean = true,
  onClick: () -> Unit
) {
  FilledIconButton(
    onClick,
    Modifier
      .height(height)
      .width(width),
    enabled,
    colors = if (iconColor != null) IconButtonDefaults.filledIconButtonColors(contentColor = iconColor) else IconButtonDefaults.filledIconButtonColors()
  ) { Icon(painterResource(icon), contentDescription, Modifier.size(24.dp)) }
}

/**
 * Filled icon button.
 *
 * @param buttonData [ButtonData].
 */
@Composable
fun FilledButton(buttonData: ButtonData) {
  FilledButton(
    buttonData.icon,
    buttonData.contentDescription,
    buttonData.iconColor,
    buttonData.height,
    buttonData.width,
    buttonData.enabled,
    buttonData.onClick
  )
}

/**
 * [Preview] for [FilledButton].
 */
@Composable
@Preview
private fun FilledButtonPreview() {
  FilledButton(R.drawable.ic_close, "Filled Button Preview") {}
}
