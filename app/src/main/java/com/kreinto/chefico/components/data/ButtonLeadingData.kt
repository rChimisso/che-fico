package com.kreinto.chefico.components.data

import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Data class for buttons.
 *
 * @property icon [Icon][ImageVector] for the button to display.
 * @property contentDescription Text used by accessibility services to describe what this icon
 * represents. This should always be provided unless this icon is used for decorative purposes,
 * and does not represent a meaningful action that a user can take. This text should be localized.
 * @property tint Icon color.
 * @property onClick Function called when the button is clicked.
 */
data class ButtonLeadingData(
  val icon: ImageVector,
  val contentDescription: String,
  val onClick: () -> Unit
)
