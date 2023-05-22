package com.kreinto.chefico.views.plantdetail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.views.camera.PlantRecognition
import com.kreinto.chefico.R

@Composable
fun PlantDetailBottomSheetContent(result: MutableState<PlantRecognition.PlantRecognitionData>, description: MutableState<String>) {

  Spacer(modifier = Modifier.height(16.dp))
  Row(modifier = Modifier.padding(16.dp)) {
    Text(text = stringResource(R.string.known_as_label), fontSize = 16.sp, color = Color(0xFF32C896))
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