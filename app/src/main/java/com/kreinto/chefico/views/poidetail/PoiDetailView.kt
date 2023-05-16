package com.kreinto.chefico.views.poidetail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.misc.PoiDetailContent
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.room.entities.Poi

@ExperimentalMaterial3Api
@Composable
fun PoiDetailView(
  onNavigate: (String) -> Unit,
  viewModel: CheFicoViewModel,
  poiId: String?
) {
  SimpleFrame(onBackPressed = onNavigate) {
    if (poiId != null) {
      val poi = viewModel.getPoi(poiId.toInt()).collectAsStateWithLifecycle(Poi.NullPoi)
      PoiDetailContent(poi.value, viewModel::updatePoi, true)
    }
  }
}
