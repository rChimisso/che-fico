package com.kreinto.chefico.views.poidetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.misc.PoiDetailContent
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.room.entities.Poi

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun PoiDetailView(
  onNavigate: (String) -> Unit,
  viewModel: CheFicoViewModel,
  poiId: String?,
  authViewModel: AuthViewModel
) {
  var poi by remember {
    mutableStateOf(Poi.NullPoi)
  }
  LaunchedEffect(poiId) {
    if (poiId != null) {
      viewModel.getPoi(poiId.toInt()).collect {
        poi = it
      }
    }
  }
  SimpleFrame(onBackPressed = onNavigate) {
    PoiDetailContent(poi, viewModel::updatePoi, true, viewModel, authViewModel)
  }
}
