package com.kreinto.chefico.views.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R

@Composable
fun DashboardBottomSheetContent(onNavigate: (String) -> Unit) {
  Row(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly
  ) {
    DashboardBottomSheetItem(
      icon = painterResource(R.drawable.ic_poi),
      text = stringResource(R.string.spots_label)
    ) { onNavigate(CheFicoRoute.PoiList.path) }
    Spacer(modifier = Modifier.height(16.dp))
    DashboardBottomSheetItem(
      icon = painterResource(R.drawable.ic_map),
      text = stringResource(R.string.map_label)
    )
    { onNavigate(CheFicoRoute.Maps.path) }
    Spacer(modifier = Modifier.height(16.dp))
    DashboardBottomSheetItem(
      icon = painterResource(R.drawable.ic_photo_camera),
      text = stringResource(R.string.camera_label)
    ) { onNavigate(CheFicoRoute.Camera.path) }
    Spacer(modifier = Modifier.height(16.dp))
    DashboardBottomSheetItem(
      icon = painterResource(R.drawable.ic_settings),
      text = stringResource(R.string.settings_label)
    ) { onNavigate(CheFicoRoute.Settings.path) }
  }
}

@Composable
private fun DashboardBottomSheetItem(
  icon: Painter,
  text: String,
  onClick: () -> Unit
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = Modifier.clickable {
      onClick()
    }
  ) {
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .size(64.dp)
        .background(MaterialTheme.colorScheme.primary)
    ) {
      Icon(
        painter = icon,
        contentDescription = text,
        tint = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
          .size(32.dp)
          .align(Alignment.Center)
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(text, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
  }
}