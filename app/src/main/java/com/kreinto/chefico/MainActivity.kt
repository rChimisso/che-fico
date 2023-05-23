package com.kreinto.chefico

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.ActivityInfo
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
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.location.LocationServices
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.room.entities.User
import com.kreinto.chefico.ui.theme.CheFicoTheme
import com.kreinto.chefico.views.account.AccountView
import com.kreinto.chefico.views.account.blacklist.BlackListView
import com.kreinto.chefico.views.account.edit.AccountEditView
import com.kreinto.chefico.views.account.login.AccountLoginView
import com.kreinto.chefico.views.account.sharings.SharingsView
import com.kreinto.chefico.views.account.signin.AccountSigninView
import com.kreinto.chefico.views.camera.CameraView
import com.kreinto.chefico.views.dashboard.DashboardView
import com.kreinto.chefico.views.maps.MapsView
import com.kreinto.chefico.views.plantdetail.PlantDetailView
import com.kreinto.chefico.views.poicreation.PoiCreationView
import com.kreinto.chefico.views.poidetail.PoiDetailView
import com.kreinto.chefico.views.poilist.PoiListView
import com.kreinto.chefico.views.settings.SettinsView
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalGetImage
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
  private lateinit var navController: NavHostController

  /**
   * If the app is lacking the specified permission, requests it.
   *
   * @param permission permission to request.
   * @param launcher [ActivityResultLauncher] to execute on the user decision.
   */
  private fun requestPermission(permission: String, launcher: ActivityResultLauncher<String>) {
    if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
        val alertBuilder = AlertDialog.Builder(this@MainActivity)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle("Permesso necessario")
        alertBuilder.setMessage("Il permesso è necessario ai fini dell'utilizzo.")
        alertBuilder.setPositiveButton(
          "Grazie"
        ) { _, _ ->
          launcher.launch(permission)
        }
        val alert = alertBuilder.create()
        alert.show()
      } else {
        launcher.launch(permission)
      }
    } else {
      launcher.launch(permission)
    }
  }

  /**
   * Returns an [ActivityResultLauncher] for permission requests.
   *
   * @param callback [ActivityResultCallback] to launch when the user choose whether to grant the permission.
   * @return [ActivityResultLauncher] for permission requests.
   */
  private fun getPermissionLauncher(callback: ActivityResultCallback<Boolean>): ActivityResultLauncher<String> {
    return registerForActivityResult(ActivityResultContracts.RequestPermission(), callback)
  }

  /**
   * Returns an [ActivityResultLauncher] for permission requests that navigates to the given [Route] if the user grants the permission.
   *
   * @param route path to navigate to if the user grants the permission.
   * @return [ActivityResultLauncher] for permission requests.
   */
  private fun getPermissionLauncher(route: String): ActivityResultLauncher<String> {
    return getPermissionLauncher {
      if (it) {
        navController.navigate(route)
      } else {
        val alertBuilder = AlertDialog.Builder(this@MainActivity)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle("Attenzione!")
        alertBuilder.setMessage("Per proseguire è necessario garantire i permessi")
        alertBuilder.setPositiveButton(
          "Grazie"
        ) { _, _ -> }
        val alert = alertBuilder.create()
        alert.show()
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val requestLocationPermissionLauncherMaps = getPermissionLauncher(CheFicoRoute.Maps.path)
    val requestLocationPermissionLauncherPoi = getPermissionLauncher(CheFicoRoute.PoiCreation.path)
    val requestLocationPermissionLauncher: (String) -> ActivityResultLauncher<String> = {
      when (it) {
        CheFicoRoute.Maps.path -> requestLocationPermissionLauncherMaps
        CheFicoRoute.PoiCreation.path -> requestLocationPermissionLauncherPoi
        else -> requestLocationPermissionLauncherMaps
      }
    }
    val requestCameraPermissionLauncher = getPermissionLauncher(CheFicoRoute.Camera.path)
    val requestGenericPermissionLauncher = getPermissionLauncher {
      if (!it) {
        // Explain to the user that the feature is unavailable because the
        // feature requires a permission that the user has denied. At the
        // same time, respect the user's decision. Don't link to system
        // settings in an effort to convince the user to change their
        // decision.
      }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      requestPermission(POST_NOTIFICATIONS, requestGenericPermissionLauncher)
      requestPermission(READ_MEDIA_IMAGES, requestGenericPermissionLauncher)
    }


    setContent {
      CheFicoTheme {
        val context = LocalContext.current
        navController = rememberNavController()
        val viewModel by viewModels<CheFicoViewModel>()
        val authViewModel by viewModels<AuthViewModel>()
        val onNavigate: (String) -> Unit = {
          when (it) {
            CheFicoRoute.Back.path -> {
              navController.popBackStack()
              if (navController.currentDestination?.route == CheFicoRoute.PlantDetail.path) {
                navController.popBackStack()
              }
            }
            CheFicoRoute.Maps.path, CheFicoRoute.PoiCreation.path -> {
              requestPermission(ACCESS_FINE_LOCATION, requestLocationPermissionLauncher(it))
            }
            CheFicoRoute.Camera.path -> {
              requestPermission(CAMERA, requestCameraPermissionLauncher)
            }
            else -> navController.navigate(it)
          }
        }
        LaunchedEffect(Unit) {
          //viewModel.getUser().collectAsStateWithLifecycle(initialValue = User.NullUser) continuare da qui Alberto
          @SuppressLint("SourceLockedOrientationActivity")
          this@MainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
          PoiNotificationManager.createNotificationChannel(context)
        }

        NavHost(navController, startDestination = CheFicoRoute.Dashboard.path) {
          composable(CheFicoRoute.Dashboard.path) { DashboardView(onNavigate) }
          composable(CheFicoRoute.Maps.path) {
            MapsView(
              onNavigate,
              viewModel,
              fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity),
              locationSettingsClient = LocationServices.getSettingsClient(this@MainActivity)
            )
          }
          composable(CheFicoRoute.Settings.path) { SettinsView(onNavigate, authViewModel) }
          composable(CheFicoRoute.PoiList.path) { PoiListView(onNavigate, viewModel) }
          composable(CheFicoRoute.PoiDetail.path, listOf(navArgument("poiId") { type = NavType.StringType })) {
            PoiDetailView(onNavigate, viewModel, poiId = it.arguments?.getString("poiId"))
          }
          composable(CheFicoRoute.Camera.path) { CameraView(onNavigate) }
          composable(CheFicoRoute.PoiCreation.path) {
            PoiCreationView(
              onNavigate,
              viewModel,
              fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)
            )
          }
          composable(CheFicoRoute.PlantDetail.path, listOf(
            navArgument("imageName") { type = NavType.StringType },
            navArgument("organ") { type = NavType.StringType }
          )) {
            PlantDetailView(
              onNavigate,
              viewModel,
              imageURI = it.arguments?.getString("imageName"),
              organ = it.arguments?.getString("organ")
            )
          }
          composable(CheFicoRoute.Signin.path) { AccountSigninView(authViewModel, onNavigate) }
          composable(CheFicoRoute.Login.path) { AccountLoginView(authViewModel, onNavigate) }
          composable(CheFicoRoute.Account.path) { AccountView(authViewModel, onNavigate) }
          composable(CheFicoRoute.AccountEdit.path) { AccountEditView(onNavigate, authViewModel) }
          composable(CheFicoRoute.BlackList.path) { BlackListView(onNavigate, authViewModel) }
          composable(CheFicoRoute.Sharings.path) { SharingsView(onNavigate, authViewModel) }
        }
      }
    }
  }
}

//fun Context.getActivity(): Activity = when (this) {
//  is Activity -> this
//  is ContextWrapper -> baseContext.getActivity()
//  else -> throw IllegalStateException("Permissions should be called in the context of an Activity")
//}
