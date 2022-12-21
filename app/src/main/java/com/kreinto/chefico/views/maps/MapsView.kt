package com.kreinto.chefico.views.maps

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.IntentSender
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.*
import com.kreinto.chefico.AppRoute
import com.kreinto.chefico.components.data.ButtonData
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.room.entities.Poi
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
  var cameraPosition: CameraPosition? by rememberSaveable { mutableStateOf(null) }
  val poisWithin = viewModel.poisWithin.collectAsStateWithLifecycle(emptyList())
  val properties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
  val uiSettings by remember {
    mutableStateOf(
      MapUiSettings(
        compassEnabled = false,
        myLocationButtonEnabled = false,
        zoomControlsEnabled = false
      )
    )
  }
  val cameraPositionState = rememberCameraPositionState {
    position = CameraPosition.builder().target(LatLng(0.0, 0.0)).zoom(20f).build()
  }
  val locationRequest = LocationRequest
    .Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
    .build()
  val locationListener = LocationListener {
    if (
      cameraPosition == null ||
      it.latitude != cameraPosition!!.target.latitude ||
      it.longitude != cameraPosition!!.target.longitude
    ) {
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
  val settingResultRequest =
    rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
      if (it.resultCode == RESULT_OK) {
        fusedLocationClient.requestLocationUpdates(
          locationRequest,
          locationListener,
          Looper.getMainLooper()
        )
      } else {
        println("Settings denied")
      }
    }
  val refreshMarkers = {
    val target = cameraPositionState.position.target
    val farLeft = cameraPositionState.projection?.visibleRegion?.farLeft
    if (farLeft != null) {
      viewModel.setMapBoundaries(
        MapBoundaries.compute(
          target,
          SphericalUtil.computeDistanceBetween(farLeft, target)
        )
      )
    }
  }

  SimpleFrame(
    onClick = { onNavigate(AppRoute.Dashboard.route) },
    bottomBar = {
      SimpleBottomBar(
        leftButtonData = ButtonData(
          icon = Icons.Default.List,
          contentDescription = "Go to POI list",
        ) { onNavigate(AppRoute.PoiList.route) },
        centerButtonData = ButtonData(
          icon = Icons.Default.Search,
          contentDescription = "Open Plant Recognition",
        ) { onNavigate(AppRoute.Camera.route) },
        rightButtonData = ButtonData(
          icon = Icons.Default.Place,
          contentDescription = "Create new POI",
        ) {
          viewModel.addPoi(
            Poi(
              "",
              "",
              "",
              "",
              cameraPosition!!.target.latitude + 10,
              cameraPosition!!.target.longitude
            )
          )
        }
      )
    }
  ) {
    LaunchedEffect(Unit) {
      val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
      val task = locationSettingsClient.checkLocationSettings(builder.build())
      task.addOnSuccessListener {
        fusedLocationClient.requestLocationUpdates(
          locationRequest,
          locationListener,
          Looper.getMainLooper()
        )
      }
      task.addOnFailureListener {
        if (it is ResolvableApiException) {
          try {
            settingResultRequest.launch(IntentSenderRequest.Builder(it.resolution).build())
          } catch (_: IntentSender.SendIntentException) {
          }
        }
      }
    }
    LaunchedEffect(cameraPosition) {
      if (cameraPosition != null) {
        cameraPositionState.animate(
          CameraUpdateFactory.newCameraPosition(cameraPosition!!),
          1000
        )
      }
    }
    LaunchedEffect(cameraPositionState.isMoving) {
      if (!cameraPositionState.isMoving) {
        refreshMarkers()
      }
    }

    LaunchedEffect(poisWithin.value) {
      println(poisWithin.value.size)
    }

    GoogleMap(
      modifier = Modifier.fillMaxSize(),
      cameraPositionState = cameraPositionState,
      onMapLoaded = {
        isMapLoaded = true
        refreshMarkers()
      },
      properties = properties,
      uiSettings = uiSettings
    ) {
      poisWithin.value.forEach { Marker(MarkerState(LatLng(it.latitude, it.longitude))) }
    }
    if (!isMapLoaded || cameraPosition == null) {
      AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = !isMapLoaded || cameraPosition == null,
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
