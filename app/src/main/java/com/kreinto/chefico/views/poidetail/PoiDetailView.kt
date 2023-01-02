package com.kreinto.chefico.views.poidetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.inputs.TextInput
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.room.entities.Poi

@ExperimentalLifecycleComposeApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun PoiDetailView(
  poiId: String?,
  viewModel: CheFicoViewModel,
  onNavigate: (String) -> Unit
) {
  SimpleFrame(
    onBackPressed = onNavigate,
  ) {
    if (poiId != null) {
      val poi = viewModel.getPoi(poiId.toInt()).collectAsStateWithLifecycle(Poi.NullPoi)
      Column {
        if (poi.value != Poi.NullPoi) {
          var name by rememberSaveable { mutableStateOf(poi.value.name) }
          var description by rememberSaveable { mutableStateOf(poi.value.description) }
          var type by rememberSaveable { mutableStateOf(poi.value.type) }
          Surface(
            shadowElevation = 12.dp,
            modifier = Modifier.fillMaxWidth(),
          ) {
            Column {
              PoiDetailSlideShow()
              TextInput(
                init = name,
                textColor = Color(0xff4caf50),
                textStyle = TextStyle(
                  fontSize = 24.sp,
                  fontWeight = FontWeight.Bold
                ),
                onFocusChanged = {
                  poi.value.name = name
                  viewModel.updatePoi(poi.value)
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
                onFocusChanged = {
                  poi.value.description = description
                  viewModel.updatePoi(poi.value)
                },
                onValueChange = { description = it }
              )
              Divider(modifier = Modifier.height(2.dp))
              Row(
                modifier = Modifier.height(56.dp),
                verticalAlignment = Alignment.CenterVertically,
              ) {
                Spacer(modifier = Modifier.width(16.dp))
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "")
                Spacer(modifier = Modifier.width(16.dp))
                TextInput(
                  init = type,
                  textStyle = TextStyle(fontSize = 18.sp),
                  onFocusChanged = {
                    poi.value.type = type
                    viewModel.updatePoi(poi.value)
                  },
                  onValueChange = { type = it }
                )
              }
            }
          }
        }
      }
    }
  }
}
