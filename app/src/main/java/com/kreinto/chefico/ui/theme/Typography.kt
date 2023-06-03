package com.kreinto.chefico.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.R

val LuckiestGuyFont = FontFamily(Font(R.font.luckiest_guy))
val ChewyFont = FontFamily(Font(R.font.chewy))

val headlineStyle = TextStyle.Default.copy(
  fontFamily = LuckiestGuyFont,
  fontSize = 32.sp,
  lineHeight = 32.sp,
  letterSpacing = 2.sp,
)

val titleStyle = TextStyle.Default.copy(
  fontFamily = LuckiestGuyFont,
  fontSize = 32.sp,
  lineHeight = 32.sp,
  letterSpacing = 2.sp,
)

val bodyStyle = TextStyle.Default.copy(
  fontFamily = ChewyFont,
  fontWeight = FontWeight.Normal,
  fontSize = 12.sp,
  lineHeight = 10.sp,
  letterSpacing = 1.sp,
)

val labelStyle = TextStyle.Default.copy(
  fontFamily = ChewyFont,
  fontWeight = FontWeight.Normal,
  fontSize = 12.sp,
  lineHeight = 10.sp,
  letterSpacing = 1.sp,
)

val Typography = Typography(
  displayLarge = TextStyle.Default.copy(
    fontFamily = ChewyFont,
    fontWeight = FontWeight.Normal,
    fontSize = 32.sp,
    lineHeight = 32.sp,
    letterSpacing = 2.sp,
  ),
  displayMedium = TextStyle.Default.copy(
    fontFamily = ChewyFont,
    fontWeight = FontWeight.Normal,
    fontSize = 32.sp,
    lineHeight = 32.sp,
    letterSpacing = 2.sp,
  ),
  displaySmall = TextStyle.Default.copy(
    fontFamily = ChewyFont,
    fontWeight = FontWeight.Normal,
    fontSize = 32.sp,
    lineHeight = 32.sp,
    letterSpacing = 2.sp,
  ),
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
