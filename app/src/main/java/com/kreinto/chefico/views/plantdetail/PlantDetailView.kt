package com.kreinto.chefico.views.plantdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
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
import com.kreinto.chefico.views.camera.PlantRecognition
import java.io.File

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun PlantDetailView(
  onNavigate: (String) -> Unit,
  imageName: String?,
  organ: String?,
) {
  val image = File(LocalContext.current.cacheDir, imageName!!)
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
    topBar = { PlantDetailTopBar(onNavigate) },
    content = { PlantDetailContent(image) },
    sheetContent = { PlantDeatailBottomSheetContent(result, description) },
    sheetContainerColor = Color(0xFF262724),
    sheetContentColor = Color(0xff32C896),
    sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
    sheetPeekHeight = 48.dp + 16.dp + 16.dp,
    sheetDragHandle = {
      Box(
        modifier = Modifier
          .padding(16.dp)
          .fillMaxWidth()
          .height(48.dp)
      ) {
        Text(
          text = result.value.results?.getOrNull(0)?.species?.commonNames?.getOrNull(0) ?: "Nome non trovato",
          fontSize = 24.sp,
          color = Color(0xFF32C896),
          modifier = Modifier.align(Alignment.Center)
        )
        FilledIconButton(
          onClick = { /*TODO*/ },
          colors = IconButtonDefaults.filledIconButtonColors(
            contentColor = Color(0xff20211e),
            containerColor = Color(0xff32C896),
          ),
          modifier = Modifier.align(Alignment.CenterEnd)
        ) {
          Icon(
            Icons.Rounded.Add,
            ""
          )
        }

      }
    }
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

