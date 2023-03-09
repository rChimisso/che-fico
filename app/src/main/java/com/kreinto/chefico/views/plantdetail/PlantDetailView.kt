package com.kreinto.chefico.views.plantdetail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.Route
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.util.ModalBottomSheet
import com.kreinto.chefico.views.camera.PlantRecognition
import java.io.File

@ExperimentalMaterial3Api
@Composable
fun PlantDetailView(
  onNavigate: (String) -> Unit,
  imageName: String?
) {
  val image = File("${LocalContext.current.cacheDir}/${imageName ?: "/"}")
  val result = remember { mutableStateOf(PlantRecognition.InvalidData) }
  val description = remember { mutableStateOf("") }

  LaunchedEffect(Unit) {
    PlantRecognition.recognize(image) {
      result.value = it
      PlantRecognition.fetchPlantDescription(
        result.value.results?.getOrNull(0)?.species?.commonNames?.getOrNull(
          0
        ) ?: ""
      ) { data ->
        description.value = data.getOrNull(0)?.extract.toString()
      }
    }
  }

  SimpleFrame(
    onBackPressed = onNavigate,

    ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
    ) {
      val imgBitmap = BitmapFactory.decodeFile(image.absolutePath)
      Image(
        bitmap = rotateImage(imgBitmap, 90f)!!.asImageBitmap(),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .fillMaxSize()
      )
      ModalBottomSheet(onDismissRequest = { /*TODO*/ }) {
        Text(text = "Ciao")
      }
    }

//    Column(
//      modifier = Modifier
//        .padding(it)
//        .fillMaxWidth()
//        .padding(16.dp),
//      horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//      val imgBitmap = BitmapFactory.decodeFile(image.absolutePath)
//
//      Image(
//        bitmap = imgBitmap.asImageBitmap(),
//        contentDescription = "",
//        contentScale = ContentScale.FillBounds,
//        modifier = Modifier
//          .fillMaxWidth()
//          .fillMaxHeight()
//          .rotate(90f)
//      )
//      Spacer(modifier = Modifier.height(16.dp))
//      Surface(
//        shadowElevation = 12.dp,
//        shape = RoundedCornerShape(8.dp),
//        modifier = Modifier
//          .fillMaxWidth()
//          .padding(16.dp)
//      ) {
//
//        Column(modifier = Modifier.fillMaxWidth()) {
//
//          Row(
//            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
//            verticalAlignment = Alignment.CenterVertically
//          ) {
//            Text(
//              text = result.value.results?.getOrNull(0)?.species?.commonNames?.getOrNull(0) ?: "",
//              fontSize = 21.sp,
//              color = Color(0xff4caf50)
//            )
//            Spacer(modifier = Modifier.weight(1f))
//            IconButton(
//              colors = IconButtonDefaults.iconButtonColors(
//                contentColor = Color.Green
//              ),
//              onClick = {
//                onNavigate(Route.PoiCreation.path)
//              }) {
//              Icon(
//                imageVector = Icons.Rounded.Add,
//                contentDescription = ""
//              )
//            }
//          }
//        }
//      }
//      Surface {
//        Column {
//          Row {
//            Text("Conosciuta come: ")
//            Spacer(Modifier.width(16.dp))
//            // var len = result.value.results?.size ?: 0
//            result.value.results?.getOrNull(0)?.species?.commonNames?.forEach { name ->
//              Text(name)
//            }
//          }
//          Text(description.value)
//        }
//      }
//    }
    AnimatedVisibility(
      modifier = Modifier.fillMaxSize(),
      visible = !result.value.isValid(),
      enter = EnterTransition.None,
      exit = fadeOut()
    ) {
      CircularProgressIndicator(
        modifier = Modifier
          .background(MaterialTheme.colorScheme.background)
          .wrapContentSize()
      )
    }
  }
}

fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
  val matrix = Matrix()
  matrix.postRotate(angle)
  return Bitmap.createBitmap(
    source, 0, 0, source.width, source.height,
    matrix, true
  )
}