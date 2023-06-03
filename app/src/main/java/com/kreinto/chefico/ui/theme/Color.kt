package com.kreinto.chefico.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val LightColors = lightColorScheme(
  primary = Color(0xFF006C4E),
  onBackground = Color(0xFF006C4E),
  surface = Color(0xFFCAFFD9),
  onSurface = Color(0xFF006C4E),
  surfaceVariant = Color.White,
  onSurfaceVariant = Color(0xFF006C4E),
  inverseSurface = Color(0xFFCAFFD9)
)

val DarkColors = darkColorScheme(
  primary = Color(0xFF84F8CC),
  onBackground = Color(0xFF84F8CC),
  surface = Color(0xFF002116),
  onSurface = Color(0xFF84F8CC),
  surfaceVariant = Color.White,
  onSurfaceVariant = Color(0xFF002116),
  inverseSurface = Color.White
)