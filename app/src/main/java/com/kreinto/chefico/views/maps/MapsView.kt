package com.kreinto.chefico.views.maps

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.location.Location
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.components.misc.Loader
import com.kreinto.chefico.room.entities.Poi
import com.kreinto.chefico.room.viewmodels.LocalViewModel
import com.kreinto.chefico.ui.theme.colorToHue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@SuppressLint("MissingPermission")
@ExperimentalCoroutinesApi
@ExperimentalMaterial3Api
@Composable
fun MapsView(
  onNavigate: (String) -> Unit,
  viewModel: LocalViewModel,
  locationClient: FusedLocationProviderClient,
  locationSettingsClient: SettingsClient
) {
  var isMapLoaded by remember { mutableStateOf(false) }
  var shouldFollow by rememberSaveable { mutableStateOf(true) }
  var cameraPosition: CameraPosition by rememberSaveable {
    mutableStateOf(
      CameraPosition
        .builder()
        .target(LatLng(0.0, 0.0))
        .zoom(19f)
        .build()
    )
  }
  val poisWithin = viewModel.poisWithin.collectAsStateWithLifecycle(emptyList())
  fun buildCameraPosition(location: Location) = CameraPosition
    .builder()
    .target(LatLng(location.latitude, location.longitude))
    .bearing(location.bearing)
    .zoom(18f)
    .build()

  val cameraPositionState = rememberCameraPositionState {
    position = cameraPosition
    locationClient.lastLocation.addOnSuccessListener {
      if (it != null) {
        position = buildCameraPosition(it)
        cameraPosition = position
      }
    }
    locationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null).addOnSuccessListener {
      if (it != null) {
        position = buildCameraPosition(it)
        cameraPosition = position
      }
    }
  }

  val locationListener = rememberLocationListener {
    if (shouldFollow) {
      cameraPosition = CameraPosition
        .builder()
        .target(LatLng(it.latitude, it.longitude))
        .bearing(it.bearing)
        .zoom(cameraPositionState.position.zoom)
        .tilt(cameraPositionState.position.tilt)
        .build()
    }
  }

  SimpleFrame(
    onNavigate,
    bottomBar = {
      SimpleBottomBar(
        leftButtonData = ButtonData(R.drawable.ic_list, "Go to POI list") { onNavigate(CheFicoRoute.PoiList.path) },
        centerButtonData = ButtonData(R.drawable.ic_photo_camera, "Open Plant Recognition") {
          onNavigate(CheFicoRoute.Camera.path)
        },
        rightButtonData = ButtonData(if (shouldFollow) R.drawable.my_location else R.drawable.location_searching, "Center camera") {
          shouldFollow = true
          locationClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            null
          ).addOnSuccessListener {
            if (it != null) {
              cameraPosition = CameraPosition
                .builder()
                .target(LatLng(it.latitude, it.longitude))
                .bearing(it.bearing)
                .zoom(if (cameraPositionState.position.zoom < 15f) 19f else cameraPositionState.position.zoom)
                .build()
            }
          }
        }
      )
    }
  ) {
    val properties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
    val uiSettings by remember {
      mutableStateOf(
        MapUiSettings(compassEnabled = false, myLocationButtonEnabled = false, mapToolbarEnabled = false, zoomControlsEnabled = false)
      )
    }
    val locationRequest = LocationRequest
      .Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
      .build()
    val settingResultRequest = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
      if (it.resultCode == Activity.RESULT_OK) {
        locationClient.requestLocationUpdates(
          locationRequest,
          locationListener,
          Looper.getMainLooper()
        )
      } else {
        println(R.string.message_label)
      }
    }
    val refreshMarkers = {
      val latLngBounds = cameraPositionState.projection?.visibleRegion?.latLngBounds
      if (latLngBounds != null) {
        viewModel.setLatLngBounds(latLngBounds)
      }
    }

    suspend fun moveCamera() {
      cameraPositionState.animate(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000)
    }

    LaunchedEffect(shouldFollow) {
      if (shouldFollow) {
        locationClient.removeLocationUpdates(locationListener)
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val task = locationSettingsClient.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
          locationClient.requestLocationUpdates(
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
        moveCamera()
      } else {
        locationClient.removeLocationUpdates(locationListener)
      }
    }
    LaunchedEffect(cameraPosition) {
      if (shouldFollow) {
        moveCamera()
      }
    }
    LaunchedEffect(cameraPositionState.isMoving) {
      if (cameraPositionState.cameraMoveStartedReason == CameraMoveStartedReason.GESTURE) {
        shouldFollow = false
      }
      if (!cameraPositionState.isMoving) {
        refreshMarkers()
      }
    }
    DisposableEffect(Unit) {
      onDispose {
        locationClient.removeLocationUpdates(locationListener)
      }
    }

    GoogleMap(
      modifier = Modifier
        .fillMaxSize(),
      properties = properties,
      uiSettings = uiSettings,
      cameraPositionState = cameraPositionState,
      onMapLoaded = {
        isMapLoaded = true
        refreshMarkers()
      },
      onMapLongClick = {
        viewModel.setCreatingPoi(Poi(name = "New POI", latitude = it.latitude, longitude = it.longitude))
        onNavigate(CheFicoRoute.PoiCreation.path)
      }
    ) {
      // For clustering: https://github.com/googlemaps/android-maps-compose/issues/44
      poisWithin.value.forEach { poi ->
        Marker(
          MarkerState(LatLng(poi.latitude, poi.longitude)),
          title = poi.name,
          // Color conversion from RGB to Hue: https://stackoverflow.com/questions/23090019/fastest-formula-to-get-hue-from-rgb
          icon = BitmapDescriptorFactory.defaultMarker(colorToHue(MaterialTheme.colorScheme.primary)),
          onClick = {
            onNavigate(CheFicoRoute.PoiDetail.path(poi.id.toString()))
            return@Marker true
          }
        )
      }
    }
    Loader(!isMapLoaded)
  }
}
