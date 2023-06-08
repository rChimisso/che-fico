package com.kreinto.chefico.views.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.items.SwipeableItem
import com.kreinto.chefico.room.viewmodels.LocalViewModel
import com.kreinto.chefico.ui.theme.*

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun DashboardView(onNavigate: (String) -> Unit, viewModel: LocalViewModel) {
  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        navigationIcon = {
          Image(
            painterResource(R.drawable.che_fico_icon),
            null,
            Modifier
              .padding(start = PaddingMedium)
              .size(IconSizeExtraLarge)
          )
        },
        title = { Text(stringResource(R.string.app_name)) }
      )
    },
    bottomBar = {
      Row(
        modifier = Modifier
          .background(MaterialTheme.colorScheme.surface)
          .padding(PaddingLarge)
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        DashboardItem(R.drawable.ic_list, stringResource(R.string.green_spots)) { onNavigate(CheFicoRoute.PoiList.path) }
        DashboardItem(R.drawable.ic_map, stringResource(R.string.map)) { onNavigate(CheFicoRoute.Maps.path) }
        DashboardItem(R.drawable.ic_photo_camera, stringResource(R.string.camera)) { onNavigate(CheFicoRoute.Camera.path) }
        DashboardItem(R.drawable.ic_settings, stringResource(R.string.settings)) { onNavigate(CheFicoRoute.Settings.path) }
      }
    }
  ) {
    val notifications = viewModel.getNotifications().collectAsStateWithLifecycle(emptyList())
    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(PaddingLarge),
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .padding(it)
        .fillMaxSize()
        .padding(PaddingLarge)
    ) {
      items(notifications.value.size) { index ->
        SwipeableItem(
          R.drawable.ic_alert,
          notifications.value[index].text,
          arrayOf({
            TransparentButton(R.drawable.ic_close, R.string.del, MaterialTheme.colorScheme.error) {
              viewModel.deleteNotification(notifications.value[index].id)
            }
          })
        ) { onNavigate(CheFicoRoute.PoiDetail.path(notifications.value[index].poiId.toString())) }
      }
    }
  }
}

@Composable
private fun DashboardItem(@DrawableRes icon: Int, text: String, onClick: () -> Unit) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(PaddingMedium)) {
    FilledIconButton(onClick, Modifier.size(DashboardItemSize), shape = MaterialTheme.shapes.small) {
      Icon(painterResource(icon), text, Modifier.size(IconSizeLarge))
    }
    Text(text, color = MaterialTheme.colorScheme.primary)
  }
}