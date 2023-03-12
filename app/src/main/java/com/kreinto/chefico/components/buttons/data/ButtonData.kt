package com.kreinto.chefico.components.buttons.data

import androidx.compose.material3.IconButtonColors
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Data class for buttons configuration.
 *
 * @property icon Icon to display.
 * @property contentDescription Text used by accessibility services to describe what the icon button represents.
 * @property colors [IconButtonColors].
 * @property height Button height.
 * @property width Button width.
 * @property enabled Whether the button is enabled.
 * @property onClick Callback called when the button is clicked.
 */
data class ButtonData(
  val icon: Int,
  val contentDescription: String,
  val colors: IconButtonColors? = null,
  val height: Dp = 40.dp,
  val width: Dp = 40.dp,
  val enabled: Boolean = true,
  val onClick: () -> Unit
)
