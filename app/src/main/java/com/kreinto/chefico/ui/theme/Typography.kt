package com.kreinto.chefico.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.R

internal val LuckiestGuyFont = FontFamily(Font(R.font.luckiest_guy))
internal val ChewyFont = FontFamily(Font(R.font.chewy))

internal val displayStyle = TextStyle.Default.copy(
  fontFamily = ChewyFont,
  fontWeight = FontWeight.Normal,
  fontSize = 32.sp,
  lineHeight = 32.sp,
  letterSpacing = 2.sp,
)

internal val headlineStyle = TextStyle.Default.copy(
  fontFamily = LuckiestGuyFont,
  fontSize = 24.sp,
  lineHeight = 24.sp,
  letterSpacing = 2.sp
)

internal val titleStyle = TextStyle.Default.copy(
  fontFamily = LuckiestGuyFont,
  fontSize = 24.sp,
  lineHeight = 24.sp,
  letterSpacing = 2.sp,
)

internal val bodyStyle = TextStyle.Default.copy(
  fontFamily = ChewyFont,
  fontWeight = FontWeight.Normal,
  fontSize = 12.sp,
  lineHeight = 10.sp,
  letterSpacing = 1.sp,
)

internal val labelStyle = TextStyle.Default.copy(
  fontFamily = ChewyFont,
  fontWeight = FontWeight.Normal,
  fontSize = 12.sp,
  lineHeight = 10.sp,
  letterSpacing = 1.sp,
)

internal val Typography = Typography(
  displayLarge = displayStyle,
  displayMedium = displayStyle,
  displaySmall = displayStyle,
  headlineLarge = headlineStyle,
  headlineMedium = headlineStyle,
  headlineSmall = headlineStyle,
  titleLarge = titleStyle,
  titleMedium = titleStyle,
  titleSmall = titleStyle,
  bodyLarge = bodyStyle,
  bodyMedium = bodyStyle,
  bodySmall = bodyStyle,
  labelLarge = labelStyle,
  labelMedium = labelStyle,
  labelSmall = labelStyle
)
