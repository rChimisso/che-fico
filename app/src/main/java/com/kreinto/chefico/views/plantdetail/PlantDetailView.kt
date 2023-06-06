package com.kreinto.chefico.views.plantdetail

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.frames.topbars.SimpleTopBar
import com.kreinto.chefico.components.misc.Loader
import com.kreinto.chefico.room.entities.Poi
import com.kreinto.chefico.room.viewmodels.LocalViewModel
import com.kreinto.chefico.views.camera.PlantRecognition
import com.kreinto.chefico.views.plantdetail.components.PlantDetailBottomSheetContent
import com.kreinto.chefico.views.plantdetail.components.PlantDetailContent
import java.net.URLDecoder

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun PlantDetailView(onNavigate: (String) -> Unit, imageURI: String?, organ: String?, viewModel: LocalViewModel) {
  val inputStream = LocalContext.current.contentResolver.openInputStream(Uri.parse(URLDecoder.decode(imageURI, "utf-8")))
  val image = BitmapFactory.decodeStream(inputStream)
  val result = remember { mutableStateOf(PlantRecognition.InvalidData) }
  val description = remember { mutableStateOf("") }

  LaunchedEffect(Unit) {
    PlantRecognition.recognize(image, organ ?: PlantRecognition.PlantOrgan.leaf) {
      result.value = it
      PlantRecognition.fetchPlantDescription(
        result.value.results?.getOrNull(0)?.species?.commonNames?.getOrNull(0) ?: ""
      ) { data -> description.value = data.getOrNull(0)?.extract ?: "" }
    }
  }

  BottomSheetScaffold(
    sheetContent = { PlantDetailBottomSheetContent(result, description) },
//    sheetContainerColor = Color(0xFF262724),
//    sheetContentColor = Color(0xff32C896),
    sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
    sheetPeekHeight = 48.dp + 16.dp + 16.dp,
    sheetDragHandle = {
      Row(
        modifier = Modifier
          .padding(16.dp)
          .fillMaxWidth()
          .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        val name = result.value.results?.getOrNull(0)?.species?.commonNames?.getOrNull(0) ?: "Unrecognized plant"
        Text(name, fontSize = 24.sp)
        TransparentButton(R.drawable.ic_arrow_next, "Save plant as POI", iconColor = MaterialTheme.colorScheme.primary) {
          viewModel.setCreatingPoi(Poi(name = name, description = description.value, image = imageURI!!))
          onNavigate(CheFicoRoute.PoiCreation.path)
        }
      }
    }
  ) {
    PlantDetailContent(image)
    // Simple Top Bar drawn here above content because setting BottomSheetScaffold topBar parameter results in unwanted content padding.
    SimpleTopBar(onNavigate)
  }
  Loader(!result.value.isValid())
}
