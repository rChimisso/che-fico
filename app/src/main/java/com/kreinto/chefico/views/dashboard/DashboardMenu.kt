package com.kreinto.chefico.views.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.Route
import com.kreinto.chefico.components.items.BasicItem
import com.kreinto.chefico.ui.theme.level1
import com.kreinto.chefico.ui.theme.level2

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun DashboardMenu(onNavigate: (id: String) -> Unit) {
  Surface(
    tonalElevation = level1,
    color = MaterialTheme.colorScheme.surface,
    shape= MaterialTheme.shapes.extraLarge
  ) {
    Column(
      verticalArrangement = Arrangement.SpaceEvenly,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.padding(16.dp)
    ) {
      BasicItem(icon = Icons.Default.List, text = "POI List") { onNavigate(Route.PoiList.path) }
      Spacer(modifier = Modifier.size(16.dp))
      BasicItem(icon = Icons.Default.Place, text = "Maps") { onNavigate(Route.Maps.path) }
      Spacer(modifier = Modifier.size(16.dp))
      BasicItem(icon = Icons.Default.Star, text = "Camera") { onNavigate(Route.Camera.path) }
      Spacer(modifier = Modifier.size(16.dp))
      BasicItem(icon = Icons.Default.Build, text = "TODO") { onNavigate(Route.Dashboard.path) }
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