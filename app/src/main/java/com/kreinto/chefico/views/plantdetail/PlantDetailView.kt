package com.kreinto.chefico.views.plantdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.FilledButton
import com.kreinto.chefico.components.frames.topbars.SimpleTopBar
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.room.entities.Poi
import com.kreinto.chefico.views.camera.PlantRecognition
import com.kreinto.chefico.views.plantdetail.components.PlantDetailBottomSheetContent
import com.kreinto.chefico.views.plantdetail.components.PlantDetailContent
import java.io.File

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun PlantDetailView(
  onNavigate: (String) -> Unit,
  viewModel: CheFicoViewModel,
  imageName: String?,
  organ: String?,
) {
  val cacheDir = LocalContext.current.cacheDir.absolutePath
  val image = File("$cacheDir/$imageName")
  val result = remember { mutableStateOf(PlantRecognition.InvalidData) }
  val description = remember { mutableStateOf("") }

  LaunchedEffect(Unit) {
    PlantRecognition.recognize(image, organ ?: PlantRecognition.PlantOrgan.leaf) {
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

  BottomSheetScaffold(
    sheetContent = { PlantDetailBottomSheetContent(result, description) },
    sheetContainerColor = Color(0xFF262724),
    sheetContentColor = Color(0xff32C896),
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
        Text(
          text = name,
          fontSize = 24.sp,
          color = Color(0xFF32C896)
        )
        FilledButton(
          icon = R.drawable.ic_close,
          contentDescription = "Save plant as POI"
        ) {
          viewModel.setCreatingPoi(Poi(name, description.value, "$cacheDir/$imageName"))
          onNavigate(CheFicoRoute.PoiCreation.path)
        }
      }
    }
  ) {
    PlantDetailContent(image)
    // Simple Top Bar drawn here above content because setting BottomSheetScaffold topBar parameter results in unwanted content padding.
    SimpleTopBar(onNavigate)
  }
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
