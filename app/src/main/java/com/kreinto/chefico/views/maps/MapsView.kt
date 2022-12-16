package com.kreinto.chefico.views.maps

import android.annotation.SuppressLint
import android.content.IntentSender
import android.os.Looper
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.kreinto.chefico.AppRoute
import com.kreinto.chefico.components.data.ButtonData
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar

@SuppressLint("MissingPermission")
@ExperimentalMaterial3Api
@Composable
fun MapsView(
  fusedLocationClient: FusedLocationProviderClient,
  locationSettingsClient: SettingsClient,
  /*activity: ComponentActivity,*/
  onNavigate: (id: String) -> Unit
) {
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
        ) {}
      )
    }
  ) {
    /*
    task.addOnCompleteListener {
      try {
        val response = it.getResult(ApiException::class.java)
        // All location settings are satisfied. The client can initialize location requests here.
      } catch (exception: ApiException) {
        // if (exception is ResolvableApiException) {}
        when (exception.statusCode) {
          LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
            // Location settings are not satisfied. But could be fixed by showing the user a dialog.
            try {
              val resolvable = exception as ResolvableApiException
              val intentSenderRequest = IntentSenderRequest.Builder(resolvable.resolution).build()
              settingResultRequest.launch(intentSenderRequest)
            } catch (_: IntentSender.SendIntentException) {

            } catch (_: ClassCastException) {

            }
          }
        }
      }
    }*/

    var isMapLoaded by remember { mutableStateOf(false) }
    var cameraPosition: CameraPosition? by remember { mutableStateOf(null) }
    val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
    val mapUiSettings by remember {
      mutableStateOf(
        MapUiSettings(
          compassEnabled = false,
          myLocationButtonEnabled = false,
          zoomControlsEnabled = false
        )
      )
    }
    val cameraPositionState = rememberCameraPositionState {
      fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
        .addOnSuccessListener {
          position = CameraPosition
            .builder()
            .target(LatLng(it.latitude, it.longitude))
            .bearing(it.bearing)
            .zoom(16f)
            .build()
        }
    }

    val locationRequest = LocationRequest
      .Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
      .build()
    val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
    val task = locationSettingsClient.checkLocationSettings(builder.build())
    task.addOnSuccessListener {
      fusedLocationClient.requestLocationUpdates(
        locationRequest,
        {
          cameraPosition = CameraPosition
            .builder()
            .target(LatLng(it.latitude, it.longitude))
            .bearing(it.bearing)
            .zoom(cameraPositionState.position.zoom)
            .build()
          println("lrcp: $cameraPosition")
        },
        Looper.getMainLooper()
      )
    }
    task.addOnFailureListener {
      if (it is ResolvableApiException) {
        // Location settings are not satisfied, but this can be fixed by showing the user a dialog.
        try {
          // Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
          /*it.startResolutionForResult(
            this@MainActivity,
            REQUEST_CHECK_SETTINGS
          )*/
        } catch (sendEx: IntentSender.SendIntentException) {
          // Ignore the error.
        }
      }
    }

    LaunchedEffect(cameraPosition) {
      println("cp: $cameraPosition")
      if (cameraPosition != null) {
        cameraPositionState.animate(
          CameraUpdateFactory.newCameraPosition(cameraPosition!!),
          1000
        )
      }
    }

    GoogleMap(
      modifier = Modifier.fillMaxSize(),
      cameraPositionState = cameraPositionState,
      onMapLoaded = { isMapLoaded = true },
      properties = mapProperties,
      uiSettings = mapUiSettings
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
