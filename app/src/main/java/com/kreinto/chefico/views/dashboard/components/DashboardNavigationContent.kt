package com.kreinto.chefico.views.dashboard.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R

@Composable
fun DashboardNavigationContent(onNavigate: (String) -> Unit) {
  Row(
    modifier = Modifier
      .background(MaterialTheme.colorScheme.surface)
      .padding(16.dp)
      .fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly
  ) {
    DashboardNavigationItem(R.drawable.ic_list, stringResource(R.string.spots_label)) { onNavigate(CheFicoRoute.PoiList.path) }
    Spacer(Modifier.height(16.dp))
    DashboardNavigationItem(R.drawable.ic_map, stringResource(R.string.map_label)) { onNavigate(CheFicoRoute.Maps.path) }
    Spacer(Modifier.height(16.dp))
    DashboardNavigationItem(R.drawable.ic_photo_camera, stringResource(R.string.camera_label)) { onNavigate(CheFicoRoute.Camera.path) }
    Spacer(Modifier.height(16.dp))
    DashboardNavigationItem(R.drawable.ic_settings, stringResource(R.string.settings_label)) { onNavigate(CheFicoRoute.Settings.path) }
  }
}

@Composable
private fun DashboardNavigationItem(@DrawableRes icon: Int, text: String, onClick: () -> Unit) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
    FilledIconButton(onClick, Modifier.size(64.dp), shape = RoundedCornerShape(16.dp)) {
      Icon(painterResource(icon), text, Modifier.size(32.dp))
    }
    Spacer(Modifier.height(8.dp))
    Text(text, color = MaterialTheme.colorScheme.primary)
  }
}
