package com.kreinto.chefico.views.maps

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.*
import com.kreinto.chefico.room.entities.Poi

@SuppressLint("MissingPermission")
@Composable
fun Map(
  cameraPosition: CameraPosition,
  cameraPositionState: CameraPositionState,
  poisWithin: State<List<Poi>>,
  fusedLocationClient: FusedLocationProviderClient,
  locationSettingsClient: SettingsClient,
  onMapLoaded: () -> Unit,
  locationListener: LocationListener,
  onBoundariesChange: (MapBoundaries) -> Unit,
  shouldFollow: Boolean,
  onMapMoved: (CameraMoveStartedReason) -> Unit
) {
  val properties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
  val uiSettings by remember {
    mutableStateOf(
      MapUiSettings(
        compassEnabled = false,
        myLocationButtonEnabled = false,
        mapToolbarEnabled = false,
        zoomControlsEnabled = false
      )
    )
  }
  val locationRequest = LocationRequest
    .Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
    .build()
  val settingResultRequest =
    rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
      if (it.resultCode == Activity.RESULT_OK) {
        fusedLocationClient.requestLocationUpdates(
          locationRequest,
          locationListener,
          Looper.getMainLooper()
        )
      } else {
        println("Settings denied")
      }
    }
  val computeBoundaries = {
    val target = cameraPositionState.position.target
    val farLeft = cameraPositionState.projection?.visibleRegion?.farLeft
    if (farLeft != null) {
      onBoundariesChange(
        MapBoundaries.compute(
          target,
          SphericalUtil.computeDistanceBetween(target, farLeft)
        )
      )
    }
  }

  LaunchedEffect(shouldFollow) {
    if (shouldFollow) {
      fusedLocationClient.removeLocationUpdates(locationListener)
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
      cameraPositionState.animate(
        CameraUpdateFactory.newCameraPosition(cameraPosition),
        1000
      )
    } else {
      fusedLocationClient.removeLocationUpdates(locationListener)
    }
  }
  LaunchedEffect(cameraPosition) {
    if (shouldFollow) {
      cameraPositionState.animate(
        CameraUpdateFactory.newCameraPosition(cameraPosition),
        1000
      )
    }
  }
  LaunchedEffect(cameraPositionState.isMoving) {
    onMapMoved(cameraPositionState.cameraMoveStartedReason)
    if (!cameraPositionState.isMoving) {
      computeBoundaries()
    }
  }

  GoogleMap(
    modifier = Modifier.fillMaxSize(),
    cameraPositionState = cameraPositionState,
    onMapLoaded = {
      onMapLoaded()
      computeBoundaries()
    },
    properties = properties,
    uiSettings = uiSettings
  ) {
    // For clustering: https://github.com/googlemaps/android-maps-compose/issues/44
    poisWithin.value.forEach { Marker(MarkerState(LatLng(it.latitude, it.longitude))) }
  }
}