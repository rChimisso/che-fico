package com.kreinto.chefico.views.dashboard

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.R
import com.kreinto.chefico.Route

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardBottomSheetContent(bottomSheetState: BottomSheetState, onNavigate: (String) -> Unit) {
  val bottomSheetOffset by remember { mutableStateOf(bottomSheetState.offset) }
  val bottomSheetPeekHeightPx = with(LocalDensity.current) { 48.dp.roundToPx() }
  val screenHeightPx = with(LocalDensity.current) {
    (LocalConfiguration.current.screenHeightDp.dp).roundToPx() - bottomSheetPeekHeightPx
  }
  Crossfade(targetState = bottomSheetOffset.value, animationSpec = tween(200)) { offset ->
    Row(
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
    ) {
      if (offset >= screenHeightPx) {
        Icon(
          painter = painterResource(R.drawable.ic_arrow_up),
          contentDescription = "swipe",
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      } else {
        Icon(
          painter = painterResource(R.drawable.ic_arrow_down),
          contentDescription = "swipe",
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    }
  }
  Row(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly
  ) {
    DashboardBottomSheetItem(
      icon = painterResource(R.drawable.ic_poi),
      text = "Green Spots"
    ) { onNavigate(Route.PoiList.path) }
    Spacer(modifier = Modifier.height(16.dp))

    DashboardBottomSheetItem(
      icon = painterResource(R.drawable.ic_map),
      text = "Mappa"
    )
    { onNavigate(Route.Maps.path) }
    Spacer(modifier = Modifier.height(16.dp))

    DashboardBottomSheetItem(
      icon = painterResource(R.drawable.ic_photo_camera),
      text = "Camera"
    ) { onNavigate(Route.Camera.path) }
    Spacer(modifier = Modifier.height(16.dp))

    DashboardBottomSheetItem(
      icon = painterResource(R.drawable.ic_settings),
      text = "Impostazioni"
    ) { onNavigate(Route.Settings.path) }
  }
}

@Composable
private fun DashboardBottomSheetItem(
  icon: Painter,
  text: String,
  onClick: () -> Unit
) {
  val colorStops = arrayOf(
    0.0f to MaterialTheme.colorScheme.primaryContainer,
    1f to MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
  )
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
        .background(Brush.verticalGradient(colorStops = colorStops))
    ) {
      Icon(
        painter = icon,
        contentDescription = text,
        tint = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier
          .size(32.dp)
          .align(Alignment.Center)
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(text, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
  }
}