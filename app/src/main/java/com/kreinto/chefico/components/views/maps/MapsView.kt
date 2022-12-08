package com.kreinto.chefico.components.views.maps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

@Composable
fun MapsView() {
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

@Preview
@Composable
fun MapsViewPreviw() {
  MapsView()
}