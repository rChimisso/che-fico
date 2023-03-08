package com.kreinto.chefico.components.buttons.data

import androidx.compose.material3.IconButtonColors
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ButtonData(
  val icon: Int,
  val contentDescription: String,
  val colors: IconButtonColors? = null,
  val height: Dp = 40.dp,
  val width: Dp = 40.dp,
  val enabled: Boolean = true,
  val onClick: () -> Unit
)
