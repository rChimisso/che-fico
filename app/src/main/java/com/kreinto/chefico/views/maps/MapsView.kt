package com.kreinto.chefico.views.maps

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.kreinto.chefico.AppRoute
import com.kreinto.chefico.components.data.ButtonData
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.room.CheFicoViewModel
import kotlinx.coroutines.flow.*

@ExperimentalLifecycleComposeApi
@SuppressLint("MissingPermission")
@ExperimentalMaterial3Api
@Composable
fun MapsView(
  fusedLocationClient: FusedLocationProviderClient,
  locationSettingsClient: SettingsClient,
  viewModel: CheFicoViewModel,
  onNavigate: (id: String) -> Unit
) {
  var isMapLoaded by remember { mutableStateOf(false) }
  var shouldFollow by rememberSaveable { mutableStateOf(true) }
  // TODO: Replace base coordinates with saved last location and saved zoom
  var cameraPosition: CameraPosition by rememberSaveable {
    mutableStateOf(
      CameraPosition
        .builder()
        .target(LatLng(0.0, 0.0))
        .zoom(18f)
        .build()
    )
  }
  val poisWithin = viewModel.poisWithin.collectAsStateWithLifecycle(emptyList())
  val cameraPositionState = rememberCameraPositionState {
    position = cameraPosition
    fusedLocationClient.lastLocation.addOnSuccessListener {
      if (it != null) {
        position = CameraPosition
          .builder()
          .target(LatLng(it.latitude, it.longitude))
          .bearing(it.bearing)
          .zoom(18f)
          .build()
        cameraPosition = position
      }
    }
    fusedLocationClient.getCurrentLocation(
      Priority.PRIORITY_BALANCED_POWER_ACCURACY,
      null
    ).addOnSuccessListener {
      if (it != null) {
        position = CameraPosition
          .builder()
          .target(LatLng(it.latitude, it.longitude))
          .bearing(it.bearing)
          .zoom(18f)
          .build()
        cameraPosition = position
      }
    }
  }

  val LocationListenerSaver: Saver<LocationListener, Int> = Saver(
    save = { 0 },
    restore = {
      LocationListener {
        if (shouldFollow) {
          cameraPosition = CameraPosition
            .builder()
            .target(LatLng(it.latitude, it.longitude))
            .bearing(it.bearing)
            .zoom(cameraPositionState.position.zoom)
            .tilt(cameraPositionState.position.tilt)
            .build()
        }
        println("Updating location...")
      }
    }
  )

  @Composable
  fun rememberLocationListener(): LocationListener =
    rememberSaveable(saver = LocationListenerSaver) {
      LocationListener {
        if (shouldFollow) {
          cameraPosition = CameraPosition
            .builder()
            .target(LatLng(it.latitude, it.longitude))
            .bearing(it.bearing)
            .zoom(cameraPositionState.position.zoom)
            .tilt(cameraPositionState.position.tilt)
            .build()
        }
        println("Updating location...")
      }
    }

  val locationListener = rememberLocationListener()

  fun navigation(id: String) {
    fusedLocationClient.removeLocationUpdates(locationListener)
    onNavigate(id)
  }

  SimpleFrame(
    onClick = { navigation(AppRoute.Dashboard.route) },
    bottomBar = {
      SimpleBottomBar(
        leftButtonData = ButtonData(
          icon = Icons.Default.List,
          contentDescription = "Go to POI list",
        ) { navigation(AppRoute.PoiList.route) },
        centerButtonData = ButtonData(
          icon = Icons.Default.Search,
          contentDescription = "Open Plant Recognition",
        ) { navigation(AppRoute.Camera.route) },
        rightButtonData = ButtonData(
          icon = if (shouldFollow) Icons.Default.AccountCircle else Icons.Default.AccountBox,
          contentDescription = "Center camera",
        ) {
          shouldFollow = true
          fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            null
          ).addOnSuccessListener {
            if (it != null) {
              cameraPosition = CameraPosition
                .builder()
                .target(LatLng(it.latitude, it.longitude))
                .bearing(it.bearing)
                .zoom(if (cameraPositionState.position.zoom < 15f) 18f else cameraPositionState.position.zoom)
                .build()
            }
          }
        }
      )
    }
  ) {
    Map(
      cameraPosition = cameraPosition,
      cameraPositionState = cameraPositionState,
      poisWithin = poisWithin,
      fusedLocationClient = fusedLocationClient,
      locationSettingsClient = locationSettingsClient,
      onMapLoaded = { isMapLoaded = true },
      locationListener = locationListener,
      onBoundariesChange = { viewModel.setMapBoundaries(it) },
      shouldFollow = shouldFollow,
      onMapMoved = {
        if (it == CameraMoveStartedReason.GESTURE) {
          shouldFollow = false
        }
      }
    )
    if (!isMapLoaded) {
      AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = !isMapLoaded,
        enter = EnterTransition.None,
        exit = fadeOut()
      ) {
        CircularProgressIndicator(
          modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .wrapContentSize()
        )
      }
    }
  }
}

@ExperimentalMaterial3Api
@Composable
@Preview
private fun MapsViewPreviw() {
  // MapsView() {}
}
