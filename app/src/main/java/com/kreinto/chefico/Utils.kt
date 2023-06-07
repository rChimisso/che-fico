package com.kreinto.chefico

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Patterns
import androidx.compose.ui.graphics.Color

/**
 * Checks if current string is a valid email.
 *
 * @return
 */
fun String.isValidEmail(): Boolean {
  return isNotEmpty() && matches(Patterns.EMAIL_ADDRESS.toRegex())
}

/**
 * Checks if current string is a valid password.
 *
 * @return
 */
fun String.isValidPassword(): Boolean {
  return isNotBlank() && length >= 6
}

/**
 * Fix image orientation.
 *
 * @return
 */
fun Bitmap.fixOrientation(): Bitmap {
  val matrix = Matrix()
  matrix.postRotate(90f)
  return Bitmap.createBitmap(
    this, 0, 0, this.width, this.height,
    matrix, true
  )
}

/**
 * Returns the corresponding Hue.
 *
 * @return Hue value.
 */
fun Color.toHue(): Float {
  val red = this.red / 255
  val green = this.green / 255
  val blue = this.blue / 255
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