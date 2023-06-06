package com.kreinto.chefico.views.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.items.SwipeableItem
import com.kreinto.chefico.room.viewmodels.LocalViewModel
import com.kreinto.chefico.views.dashboard.components.DashboardNavigationContent

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun DashboardView(onNavigate: (String) -> Unit, viewModel: LocalViewModel) {
  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        navigationIcon = { Image(painterResource(R.drawable.che_fico_icon), null, Modifier.size(64.dp)) },
        title = { Text(stringResource(R.string.app_name)) }
      )
    },
    bottomBar = { DashboardNavigationContent(onNavigate) }
  ) {
    val notifications = viewModel.getNotifications().collectAsStateWithLifecycle(emptyList())
    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .padding(it)
        .fillMaxSize()
        .padding(16.dp)
    ) {
      items(notifications.value.size) { index ->
        SwipeableItem(
          R.drawable.ic_alert,
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
}
