package com.kreinto.chefico.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val LightColors = lightColorScheme(
//  onBackground = Color(0xFF006C4E),
//  surfaceVariant = Color.White,
//  onSurfaceVariant = Color(0xFF006C4E),
//  inverseSurface = Color(0xFFCAFFD9)
  primary = Color(0xFF2B9584),
  onPrimary = Color(0xFFCAFFD9),
//  primaryContainer = Color(0xFF84F8CC),
//  onPrimaryContainer = Color(0xFF84F8CC),
//  inversePrimary = Color(0xFF84F8CC),
//  secondary = Color(0xFF84F8CC),
//  onSecondary = Color(0xFF84F8CC),
//  secondaryContainer = Color(0xFF84F8CC),
//  onSecondaryContainer = Color(0xFF84F8CC),
//  tertiary = Color(0xFF84F8CC),
//  onTertiary = Color(0xFF84F8CC),
//  tertiaryContainer = Color(0xFF84F8CC),
//  onTertiaryContainer = Color(0xFF84F8CC),
//  background = Color(0xFF84F8CC),
//  onBackground = Color(0xFF84F8CC),
  surface = Color(0xFFCAFFD9),
  onSurface = Color(0xFF2B9584),
//  surfaceVariant = Color.White,
  onSurfaceVariant = Color(0xFF2B9584),
//  surfaceTint = Color(0xFF84F8CC),
  inverseSurface = Color(0xFF2B9584),
//  onBackground = Color(0xFF84F8CC),
//  surfaceVariant = Color(0xFF84F8CC),
//  onSurfaceVariant = Color(0xFF84F8CC),
//  inverseSurface = Color(0xFF84F8CC),
//  inverseOnSurface = Color(0xFF84F8CC),
//  error = Color(0xFF84F8CC),
//  onError = Color(0xFF84F8CC),
//  errorContainer = Color(0xFF84F8CC),
//  onErrorContainer = Color(0xFF84F8CC),
  outline = Color.White,
//  outlineVariant = Color(0xFF84F8CC),
//  scrim = Color(0xFF84F8CC)
)

val DarkColors = darkColorScheme(
  primary = Color(0xFF84F8CC),
  onPrimary = Color(0xFF002116),
//  primaryContainer = Color(0xFF84F8CC),
//  onPrimaryContainer = Color(0xFF84F8CC),
//  inversePrimary = Color(0xFF84F8CC),
//  secondary = Color(0xFF84F8CC),
//  onSecondary = Color(0xFF84F8CC),
//  secondaryContainer = Color(0xFF84F8CC),
//  onSecondaryContainer = Color(0xFF84F8CC),
//  tertiary = Color(0xFF84F8CC),
//  onTertiary = Color(0xFF84F8CC),
//  tertiaryContainer = Color(0xFF84F8CC),
//  onTertiaryContainer = Color(0xFF84F8CC),
//  background = Color(0xFF84F8CC),
//  onBackground = Color(0xFF84F8CC),
  surface = Color(0xFF002116),
  onSurface = Color(0xFF84F8CC),
//  surfaceVariant = Color.White,
  onSurfaceVariant = Color(0xFF84F8CC),
//  surfaceTint = Color(0xFF84F8CC),
  inverseSurface = Color(0xFF84F8CC),
//  onBackground = Color(0xFF84F8CC),
//  surfaceVariant = Color(0xFF84F8CC),
//  onSurfaceVariant = Color(0xFF84F8CC),
//  inverseSurface = Color(0xFF84F8CC),
//  inverseOnSurface = Color(0xFF84F8CC),
  error = Color.Red,
//  onError = Color(0xFF84F8CC),
//  errorContainer = Color(0xFF84F8CC),
//  onErrorContainer = Color(0xFF84F8CC),
  outline = Color.White,
//  outlineVariant = Color(0xFF84F8CC),
//  scrim = Color(0xFF84F8CC)
)

fun colorToHue(color: Color): Float {
  val red = color.red / 255
  val green = color.green / 255
  val blue = color.blue / 255

  val max = maxOf(red, green, blue)
  val min = minOf(red, green, blue)

  if (red == max) {
    return ((green - blue) / (max - min)) * 60
  }
  if (green == max) {
    return (2f + (blue - red) / (max - min)) * 60
  }
  return (4f + (red - green) / (max - min)) * 60
}
