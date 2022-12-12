package com.kreinto.chefico.views.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.AppRoute
import com.kreinto.chefico.components.buttons.RoundButton
import com.kreinto.chefico.components.data.ButtonData

@Composable
fun DashboardMenu(onNavigate: (id: String) -> Unit) {
  Surface(
    elevation = 12.dp,
    modifier = Modifier
      .fillMaxWidth()
      .height(56.dp)
  ) {
    Row(
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      RoundButton(
        ButtonData(
          icon = Icons.Default.Share,
          contentDescription = "",
          onClick = { onNavigate(AppRoute.PoiList.route) }
        )
      )
      RoundButton(
        ButtonData(
          icon = Icons.Default.Add,
          contentDescription = "",
          onClick = { onNavigate(AppRoute.Maps.route) }
        )
      )
      RoundButton(
        ButtonData(
          icon = Icons.Default.CheckCircle,
          contentDescription = "",
          onClick = { onNavigate(AppRoute.Camera.route) }
        )
      )
    }
  }
}