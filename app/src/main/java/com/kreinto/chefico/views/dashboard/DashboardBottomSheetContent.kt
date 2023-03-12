package com.kreinto.chefico.views.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.R
import com.kreinto.chefico.Route

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
    ) { onNavigate(Route.Camera.path) }
  }
}

@Composable
private fun DashboardBottomSheetItem(
  icon: Painter,
  text: String,
  onClick: () -> Unit
) {
  val colorStops = arrayOf(
    0.0f to Color(0xFF32C896),
    1f to Color(0xff2B9584)
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
        tint = Color(0xff20211e),
        modifier = Modifier
          .size(32.dp)
          .align(Alignment.Center)
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(text, fontSize = 12.sp, color = Color(0xFF32C896))
  }
}