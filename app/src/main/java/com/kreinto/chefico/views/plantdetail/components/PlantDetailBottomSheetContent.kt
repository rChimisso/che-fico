package com.kreinto.chefico.views.plantdetail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.R
import com.kreinto.chefico.views.camera.PlantRecognition

@Composable
fun PlantDetailBottomSheetContent(result: MutableState<PlantRecognition.PlantRecognitionData>, description: MutableState<String>) {
  val commonNames = result.value.results?.getOrNull(0)?.species?.commonNames
  if (commonNames != null && commonNames.isNotEmpty()) {
    Spacer(Modifier.height(16.dp))
    Row(Modifier.padding(16.dp)) {
      Text(stringResource(R.string.known_as_label), fontSize = 16.sp)
      Spacer(Modifier.width(8.dp))
      Column {
        commonNames.forEach { name ->
          Text(name, fontSize = 16.sp)
          Spacer(Modifier.height(8.dp))
        }
      }
    }
  }
  if (description.value.isNotBlank()) {
    Spacer(Modifier.height(16.dp))
    Text(description.value, Modifier.padding(16.dp))
  }
}
