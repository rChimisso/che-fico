package com.kreinto.chefico.components.misc

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.FilledButton
import com.kreinto.chefico.components.inputs.TextInput
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.room.entities.Poi
import java.net.URLDecoder

private fun fixOrientaton(source: Bitmap): ImageBitmap {
  val matrix = Matrix()
  matrix.postRotate(90f)
  return Bitmap.createBitmap(
    source, 0, 0, source.width, source.height,
    matrix, true
  ).asImageBitmap()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoiDetailContent(poi: Poi, updatePoi: (Poi) -> Unit, showActions: Boolean, authViewModel: AuthViewModel) {
  //var openBottomSheet by remember { mutableStateOf(false) }
  //val bottomSheetState = rememberModalBottomSheetState()
  val context = LocalContext.current
  Column {
    if (poi != Poi.NullPoi) {
      val stream = context.contentResolver.openInputStream(Uri.parse(URLDecoder.decode(poi.image, "utf-8")))
      val image = BitmapFactory.decodeStream(stream)
      var name by rememberSaveable { mutableStateOf(poi.name) }
      var description by rememberSaveable { mutableStateOf(poi.description) }
      Surface(
        shadowElevation = 12.dp,
        modifier = Modifier.fillMaxWidth(),
      ) {
        Column {
          Box {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .align(Alignment.TopCenter),
            ) {
              if (image != null) {
                Image(
                  bitmap = fixOrientaton(image),
                  null,
                  contentScale = ContentScale.Crop
                )
              } else {
                Image(
                  painterResource(R.drawable.no_image),
                  null,
                  contentScale = ContentScale.Crop
                )
              }
            }
            Row(
              horizontalArrangement = Arrangement.End,
              modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth()
                .offset(y = 20.dp)
            ) {
              if (showActions) {
                FilledButton(icon = R.drawable.ic_share, contentDescription = "Share") {
                  //openBottomSheet = true
                }
                Spacer(modifier = Modifier.width(8.dp))
              }
              FilledButton(icon = R.drawable.ic_close, contentDescription = "Change image") {
                // TODO: Change image (pick from gallery and overwrite)
              }
              Spacer(modifier = Modifier.width(8.dp))
            }
          }
          TextInput(
            init = name,
            textColor = Color(0xff4caf50),
            textStyle = TextStyle(
              fontSize = 24.sp,
              fontWeight = FontWeight.Bold
            ),
            onFocusChanged = {
              poi.name = name
              //updatePoi(poi)
            },
            onValueChange = { name = it }
          )
        }
      }
      Surface(
        shadowElevation = 12.dp,
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(10.dp)
      ) {
        Column {
          TextInput(
            modifier = Modifier.requiredHeight(128.dp),
            init = description,
            textStyle = TextStyle(fontSize = 18.sp),
            singleLine = false,
            onFocusChanged = {
              poi.description = description
              //updatePoi(poi)
            },
            onValueChange = { description = it }
          )
          Divider(modifier = Modifier.height(2.dp))
        }
      }
    }
  }
}