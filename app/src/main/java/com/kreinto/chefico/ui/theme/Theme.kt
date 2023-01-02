package com.kreinto.chefico.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
  primary = TestGreen
)

private val LightColorPalette = lightColorScheme(
  primary = TestGreen
)

@Composable
fun CheFicoTheme(content: @Composable () -> Unit) {
  MaterialTheme(
    colorScheme = if (isSystemInDarkTheme()) DarkColorPalette else LightColorPalette,
    shapes = Shapes,
    typography = Typography,
    content = content
  )
}