package com.kreinto.chefico

import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.location.LocationServices
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.ui.theme.CheFicoTheme
import com.kreinto.chefico.views.camera.CameraView
import com.kreinto.chefico.views.dashboard.DashboardView
import com.kreinto.chefico.views.maps.MapsView
import com.kreinto.chefico.views.plantdetail.PlantDetailView
import com.kreinto.chefico.views.poicreation.PoiCreationView
import com.kreinto.chefico.views.poidetail.PoiDetailView
import com.kreinto.chefico.views.poilist.PoiListView
import com.kreinto.chefico.views.settings.SettinsView
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalLifecycleComposeApi
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalGetImage
class MainActivity : ComponentActivity() {
  private lateinit var navController: NavHostController

  private fun requestPermission(permission: String, launcher: ActivityResultLauncher<String>) {
    if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
        // In an educational UI, explain to the user why your app requires this
        // permission for a specific feature to behave as expected, and what
        // features are disabled if it's declined. In this UI, include a
        // "cancel" or "no thanks" button that lets the user continue
        // using your app without granting the permission.
        // showInContextUI(...)
      } else {
        launcher.launch(permission)
      }
    }
  }

  private fun getPermissionLauncher(callback: ActivityResultCallback<Boolean>): ActivityResultLauncher<String> {
    return registerForActivityResult(ActivityResultContracts.RequestPermission(), callback)
  }

  private fun getPermissionLauncher(route: Route): ActivityResultLauncher<String> {
    return getPermissionLauncher {
      if (it) {
        navController.navigate(route.path)
      } else {
        // Explain to the user that the feature is unavailable because the
        // feature requires a permission that the user has denied. At the
        // same time, respect the user's decision. Don't link to system
        // settings in an effort to convince the user to change their
        // decision.
      }
    }
  }

  private fun getNavArgs(route: Route) = listOf(navArgument(route.arg) { type = NavType.StringType })

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val requestLocationPermissionLauncher = getPermissionLauncher(Route.Maps)
    val requestCameraPermissionLauncher = getPermissionLauncher(Route.Camera)
    val requestNotificationPermissionLauncher = getPermissionLauncher {
      if (!it) {
        // Explain to the user that the feature is unavailable because the
        // feature requires a permission that the user has denied. At the
        // same time, respect the user's decision. Don't link to system
        // settings in an effort to convince the user to change their
        // decision.
      }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      requestPermission(POST_NOTIFICATIONS, requestNotificationPermissionLauncher)
    }

    setContent {
      CheFicoTheme {
        navController = rememberNavController()
        val viewModel by viewModels<CheFicoViewModel>()
        val onNavigate: (String) -> Unit = {
          when (it) {
            Route.Back.path -> {
              navController.popBackStack()
              if (navController.currentDestination?.route == Route.PlantDetail.path) {
                navController.popBackStack()
              }
            }
            Route.Maps.path -> {
              requestPermission(ACCESS_FINE_LOCATION, requestLocationPermissionLauncher)
            }
            Route.Camera.path -> {
              requestPermission(CAMERA, requestCameraPermissionLauncher)
            }
            else -> navController.navigate(it)
          }
        }

        LaunchedEffect(Unit) {
          PoiNotificationManager.createNotificationChannel(this@MainActivity)
        }

        NavHost(navController, startDestination = Route.Dashboard.path) {
          composable(Route.Dashboard.path) { DashboardView(onNavigate) }
          composable(Route.Maps.path) {
            MapsView(
              onNavigate,
              viewModel,
              fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity),
              locationSettingsClient = LocationServices.getSettingsClient(this@MainActivity)
            )
          }
          composable(Route.Settings.path) { SettinsView(onNavigate) }
          composable(Route.PoiList.path) { PoiListView(onNavigate, viewModel) }
          composable(Route.PoiDetail.path, getNavArgs(Route.PoiDetail)) {
            PoiDetailView(onNavigate, viewModel, poiId = it.arguments?.getString(Route.PoiDetail.arg))
          }
          composable(Route.Camera.path) { CameraView(onNavigate) }
          composable(Route.PoiCreation.path) { PoiCreationView(onNavigate) }
          composable(Route.PlantDetail.path, getNavArgs(Route.PlantDetail)) {
            PlantDetailView(onNavigate, imageName = it.arguments?.getString(Route.PlantDetail.arg))
          }
        }
      }
    }
  }
}

fun Context.getActivity(): Activity = when (this) {
  is Activity -> this
  is ContextWrapper -> baseContext.getActivity()
  else -> throw IllegalStateException("Permissions should be called in the context of an Activity")
}
