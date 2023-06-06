package com.kreinto.chefico.components.buttons

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.ui.theme.IconSizeMedium
import com.kreinto.chefico.ui.theme.InteractSizeMedium

/**
 * Transparent icon button.
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
fun TransparentButton(
  @DrawableRes icon: Int,
  @StringRes contentDescription: Int? = null,
  iconColor: Color? = null,
  width: Dp = InteractSizeMedium,
  height: Dp = width,
  enabled: Boolean = true,
  onClick: () -> Unit
) {
  IconButton(
    onClick,
    Modifier
      .height(height)
      .width(width),
    enabled,
    colors = if (iconColor != null) IconButtonDefaults.filledIconButtonColors(Color.Transparent, iconColor)
    else IconButtonDefaults.filledIconButtonColors(Color.Transparent)
  ) {
    Icon(
      painterResource(icon),
      if (contentDescription == null) null else stringResource(contentDescription),
      Modifier.size(IconSizeMedium)
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
 * [Preview] for [TransparentButton].
 */
@Composable
@Preview
private fun TransparentButtonPreview() {
  TransparentButton(R.drawable.ic_close, R.string.preview) {}
}
