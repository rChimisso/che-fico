package com.kreinto.chefico.views.maps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar

@ExperimentalMaterial3Api
@Composable
fun MapsView(
  onNavigate: (id: String) -> Unit
) {
  SimpleFrame(
    onClick = { onNavigate("dashboard") },
    bottomBar = {
      SimpleBottomBar(
        leftIcon = Icons.Default.List,
        leftAction = {},
        centerIcon = Icons.Default.Search,
        centerAction = {},
        rightIcon = Icons.Default.Place,
        rightAction = {}
      )
    }
  ) {
    val mapProperties by remember {
      mutableStateOf(
        MapProperties(maxZoomPreference = 10f, minZoomPreference = 5f)
      )
    }
    val mapUiSettings by remember {
      mutableStateOf(
        MapUiSettings(mapToolbarEnabled = true)
      )
    }
    GoogleMap(
      modifier = Modifier.fillMaxSize(),
      properties = mapProperties,
      uiSettings = mapUiSettings,
    )
  }
}

@ExperimentalMaterial3Api
@Composable
@Preview
private fun MapsViewPreviw() {
  MapsView {}
}