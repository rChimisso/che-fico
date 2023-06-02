package com.kreinto.chefico.views.dashboard.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.buttons.data.ButtonData
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
      .background(MaterialTheme.colorScheme.background)
  ) {
    items(notifications.value.size) { index ->
      SwipeableItem(
        icon = R.drawable.ic_poi,
        text = notifications.value[index].text,
        actions = arrayOf({
          TransparentButton(
            ButtonData(
              icon = R.drawable.ic_close,
              contentDescription = "Elimina",
              colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.error
              ),
              onClick = {
                viewModel.deleteNotification(notifications.value[index].id)
              }
            )
          )
        }),
        onClick = { onNavigate(CheFicoRoute.PoiDetail.path(notifications.value[index].poiId.toString())) }
      )
    }
  }
}

