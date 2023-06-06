package com.kreinto.chefico.components.buttons.data

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.kreinto.chefico.ui.theme.InteractSizeMedium

/**
 * Data class for buttons configuration.
 *
 * @property icon Icon to display.
 * @property contentDescription Text used by accessibility services to describe what the icon button represents.
 * @property iconColor Optional Icon color.
 * @property height Button height.
 * @property width Button width.
 * @property enabled Whether the button is enabled.
 * @property onClick Callback called when the button is clicked.
 */
data class ButtonData(
  val icon: Int,
  @StringRes val contentDescription: Int? = null,
  val iconColor: Color? = null,
  val width: Dp = InteractSizeMedium,
  val height: Dp = InteractSizeMedium,
  val enabled: Boolean = true,
  val onClick: () -> Unit
)
