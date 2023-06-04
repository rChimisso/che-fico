package com.kreinto.chefico.views.dashboard.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.items.SwipeableItem
import com.kreinto.chefico.room.CheFicoViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DashboardContent(onNavigate: (String) -> Unit, viewModel: CheFicoViewModel) {
  val notifications = viewModel.getNotifications().collectAsStateWithLifecycle(emptyList())
  LazyColumn(
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .padding(16.dp)
      .fillMaxSize()
  ) {
    items(notifications.value.size) { index ->
      SwipeableItem(
        R.drawable.ic_poi,
        notifications.value[index].text,
        arrayOf({
          TransparentButton(R.drawable.ic_close, "Elimina", MaterialTheme.colorScheme.error) {
            viewModel.deleteNotification(notifications.value[index].id)
          }
        })
      ) { onNavigate(CheFicoRoute.PoiDetail.path(notifications.value[index].poiId.toString())) }
    }
  }
}

