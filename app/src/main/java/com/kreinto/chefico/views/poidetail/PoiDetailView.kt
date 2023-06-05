package com.kreinto.chefico.views.poidetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.misc.PoiDetailContent
import com.kreinto.chefico.room.entities.Poi
import com.kreinto.chefico.room.viewmodels.AuthViewModel
import com.kreinto.chefico.room.viewmodels.LocalViewModel

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun PoiDetailView(
  onNavigate: (String) -> Unit,
  poiId: String?,
  viewModel: LocalViewModel,
  authViewModel: AuthViewModel
) {
  var poi by remember { mutableStateOf(Poi.NullPoi) }
  LaunchedEffect(poiId) {
    if (poiId != null) {
      viewModel.getPoi(poiId.toLong()).collect { poi = it }
    }
  }
  SimpleFrame(onNavigate) { PoiDetailContent(poi, viewModel::updatePoi, true, viewModel, authViewModel) }
}
