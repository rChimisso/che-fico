package com.kreinto.chefico

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.ui.theme.CheFicoTheme
import com.kreinto.chefico.views.dashboard.DashboardView
import com.kreinto.chefico.views.maps.MapsView
import com.kreinto.chefico.views.poidetail.PoiDetailView
import com.kreinto.chefico.views.poilist.PoiListView
import com.kreinto.chefico.views.settings.SettinsView

sealed class AppRoute(val route: String, val arg: String = "") {
  object Dashboard : AppRoute("dashboard")
  object Settings : AppRoute("settings")
  object Maps : AppRoute("maps")
  object Camera : AppRoute("camera")
  object PoiList : AppRoute("poilist")
  object PoiDetail : AppRoute("poidetail/{poiId}", "poiId")
}

@ExperimentalLifecycleComposeApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@ExperimentalPagerApi
class MainActivity : ComponentActivity() {
  private lateinit var fusedLocationClient: FusedLocationProviderClient
  private lateinit var locationSettingsClient: SettingsClient
  private lateinit var navController: NavHostController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    locationSettingsClient = LocationServices.getSettingsClient(this)
    val requestPermissionLauncher =
      registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
          navController.navigate(AppRoute.Maps.route)
        } else {
          // Explain to the user that the feature is unavailable because the
          // feature requires a permission that the user has denied. At the
          // same time, respect the user's decision. Don't link to system
          // settings in an effort to convince the user to change their
          // decision.
        }
      }

    setContent {
      CheFicoTheme {
        navController = rememberNavController()
        val onNavigate: (route: String) -> Unit = {
          if (it == AppRoute.Maps.route && ContextCompat.checkSelfPermission(
              this,
              ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
          ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)) {
              // In an educational UI, explain to the user why your app requires this
              // permission for a specific feature to behave as expected, and what
              // features are disabled if it's declined. In this UI, include a
              // "cancel" or "no thanks" button that lets the user continue
              // using your app without granting the permission.
              // showInContextUI(...)
            } else {
              requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
            }
          } else {
//            fusedLocationClient.removeLocationUpdates {}
            navController.navigate(it)
          }
        }
        val viewModel by viewModels<CheFicoViewModel>()
        val navController = rememberNavController()
        NavHost(
          navController = navController,
          startDestination = AppRoute.Dashboard.route,
        ) {
          composable(AppRoute.Dashboard.route) {
            DashboardView(onNavigate = onNavigate)
          }
          composable(AppRoute.Maps.route) {
            MapsView(
              fusedLocationClient = fusedLocationClient,
              locationSettingsClient = locationSettingsClient,
              onNavigate = onNavigate
            )
          }
          composable(AppRoute.Settings.route) {
            SettinsView(onNavigate = onNavigate)
          }
          composable(AppRoute.PoiList.route) {
            PoiListView(viewModel = viewModel, onNavigate = onNavigate)
          }
          composable(
            AppRoute.PoiDetail.route,
            arguments = listOf(
              navArgument(AppRoute.PoiDetail.arg) {
                type = NavType.StringType
              }
            )
          ) { backStackEntry ->
            PoiDetailView(
              onNavigate = onNavigate,
              poiId = backStackEntry.arguments?.getString(AppRoute.PoiDetail.arg),
              viewModel = viewModel
            )
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

