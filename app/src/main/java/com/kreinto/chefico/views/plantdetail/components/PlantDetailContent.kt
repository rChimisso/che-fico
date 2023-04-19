package com.kreinto.chefico.views.plantdetail.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import java.io.File

@Composable
fun PlantDetailContent(image: File) {
  Image(
    bitmap = fixOrientaton(BitmapFactory.decodeFile(image.absolutePath)),
    contentDescription = "",
    contentScale = ContentScale.Crop,
    modifier = Modifier
      .fillMaxSize()
  )
}

private fun fixOrientaton(source: Bitmap): ImageBitmap {
  val matrix = Matrix()
  matrix.postRotate(90f)
  return Bitmap.createBitmap(
    source, 0, 0, source.width, source.height,
    matrix, true
  ).asImageBitmap()
}