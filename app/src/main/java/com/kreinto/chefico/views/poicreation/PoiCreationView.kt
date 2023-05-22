package com.kreinto.chefico.views.poicreation

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.components.misc.PoiDetailContent
import com.kreinto.chefico.room.CheFicoViewModel
import java.io.OutputStream
import java.net.URLDecoder
import java.util.*

fun saveImage(path: String, contentResolver: ContentResolver, onUriCreated: (Uri?) -> Unit) {
  if (path.isNotBlank() && path.contains("cache")) {
    val inputStream = contentResolver.openInputStream(Uri.parse(URLDecoder.decode(path, "utf-8")))
    val image = BitmapFactory.decodeStream(inputStream)
    val outputStream: OutputStream?
    val contentValues = ContentValues()
    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "${System.currentTimeMillis()}.jpg")
    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
    val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    if (imageUri != null) {
      outputStream = contentResolver.openOutputStream(imageUri)
      if (outputStream != null) {
        image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
      }
      onUriCreated(imageUri)
    }
  } else {
    onUriCreated(null)
  }
}

@SuppressLint("MissingPermission")
@ExperimentalMaterial3Api
@Composable
fun PoiCreationView(
  onNavigate: (String) -> Unit,
  viewModel: CheFicoViewModel,
  fusedLocationClient: FusedLocationProviderClient
) {
  val contentResolver = LocalContext.current.contentResolver
  var creatingPoi = viewModel.getCreatingPoi()
  fun action(path: String = CheFicoRoute.Back.path) {
    viewModel.removeCreatingPoi()
    onNavigate(path)
  }
  SimpleFrame(
    onBackPressed = ::action,
    bottomBar = {
      SimpleBottomBar(
        leftButtonData = ButtonData(
          icon = R.drawable.ic_close,
          contentDescription = "Cancel",
          colors = IconButtonDefaults.filledIconButtonColors(contentColor = Color.Red),
          onClick = ::action
        ),
        rightButtonData = ButtonData(
          icon = R.drawable.ic_check,
          contentDescription = "Confirm"
        ) {
          saveImage(creatingPoi.image, contentResolver) {
            if (it != null) {
              creatingPoi.image = it.toString()
            }
            if (creatingPoi.latitude == 0.0 && creatingPoi.longitude == 0.0) {
              fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                  creatingPoi.latitude = location.latitude
                  creatingPoi.longitude = location.longitude
                }
                viewModel.addPoi(creatingPoi)
                action()
              }
            } else {
              viewModel.addPoi(creatingPoi)
              action()
            }
          }
        }
      )
    }
  ) {
    PoiDetailContent(creatingPoi, { creatingPoi = it }, false)
  }
}
