package com.kreinto.chefico.views.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.AppRoute
import com.kreinto.chefico.components.items.BasicItem

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun DashboardMenu(onNavigate: (id: String) -> Unit) {
  Surface(
    shadowElevation = 12.dp,
    color = Color.White
  ) {
    /*Surface(
      shape = RoundedCornerShape(40.dp),
      shadowElevation = 12.dp,
      modifier = Modifier.offset(y = 0.dp),
      color = Color.White
    ) {
      IconButton(
        modifier = Modifier
          .width(80.dp)
          .height(28.dp),
        onClick = {}
      ) {
        Icon(
          imageVector = Icons.Default.ArrowDropDown,
          contentDescription = "Hide menu",
          tint = Color.Black
        )
      }
    }*/
    Column(
      verticalArrangement = Arrangement.SpaceEvenly,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.padding(16.dp)
    ) {
      BasicItem(icon = Icons.Default.List, text = "POI List") { onNavigate(AppRoute.PoiList.route) }
      Spacer(modifier = Modifier.size(16.dp))
      BasicItem(icon = Icons.Default.Place, text = "Maps") { onNavigate(AppRoute.Maps.route) }
      Spacer(modifier = Modifier.size(16.dp))
      BasicItem(icon = Icons.Default.Star, text = "Camera") { onNavigate(AppRoute.Camera.route) }
      Spacer(modifier = Modifier.size(16.dp))
      BasicItem(icon = Icons.Default.Build, text = "TODO") { onNavigate(AppRoute.Dashboard.route) }
    }
  }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
@Preview
private fun DashboardMenuPreview() {
  DashboardMenu {}
}