package com.kreinto.chefico.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * Che Fico! custom theme.
 *
 * @param content
 */
@Composable
fun CheFicoTheme(content: @Composable () -> Unit) {
  MaterialTheme(
    colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
    shapes = Shapes,
    typography = Typography,
    content = content
  )
}
