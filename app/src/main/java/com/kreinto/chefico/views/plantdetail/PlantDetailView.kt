package com.kreinto.chefico.views.plantdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.views.camera.PlantRecognition
import java.io.File

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)
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

  BottomSheetScaffold(
    topBar = { PlantDetailTopBar(onNavigate) },
    content = { PlantDetailContent(image) },
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = { PlantDetailFloatingActionButton() },
    sheetContent = { PlantDeatailBottomSheetContent(result, description) },
    sheetBackgroundColor = Color(0xFF262724),
    sheetPeekHeight = 48.dp,
    sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
  )
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

