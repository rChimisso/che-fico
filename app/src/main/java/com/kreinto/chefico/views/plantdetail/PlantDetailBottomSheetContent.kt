package com.kreinto.chefico.views.plantdetail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.views.camera.PlantRecognition

@Composable
fun PlantDeatailBottomSheetContent(result: MutableState<PlantRecognition.PlantRecognitionData>, description: MutableState<String>) {
  Row(
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .fillMaxWidth()
      .height(48.dp)
  ) {
    Text(
      text = result.value.results?.getOrNull(0)?.species?.commonNames?.getOrNull(0) ?: "Nome non trovato",
      fontSize = 24.sp,
      color = Color(0xFF32C896)
    )
  }
  Spacer(modifier = Modifier.height(16.dp))
  Row(modifier = Modifier.padding(16.dp)) {
    Text(text = "Conosciuta come:", fontSize = 16.sp, color = Color(0xFF32C896))
    Spacer(Modifier.width(8.dp))
    Column {
      result.value.results?.getOrNull(0)?.species?.commonNames?.forEach { name ->
        Text(text = name, fontSize = 16.sp, color = Color(0xFF32C896))
        Spacer(Modifier.height(8.dp))
      }
    }
  }
  Spacer(modifier = Modifier.height(16.dp))
  Text(description.value, Modifier.padding(16.dp), color = Color(0xFF32C896))
}