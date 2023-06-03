package com.kreinto.chefico

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.location.LocationServices
import com.kreinto.chefico.managers.PoiNotificationManager
import com.kreinto.chefico.managers.SettingsManager
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.ui.theme.CheFicoTheme
import com.kreinto.chefico.views.account.AccountView
import com.kreinto.chefico.views.account.blacklist.BlackListView
import com.kreinto.chefico.views.account.edit.AccountEditView
import com.kreinto.chefico.views.account.login.AccountLoginView
import com.kreinto.chefico.views.account.signin.AccountSigninView
import com.kreinto.chefico.views.camera.CameraView
import com.kreinto.chefico.views.dashboard.DashboardView
import com.kreinto.chefico.views.maps.MapsView
import com.kreinto.chefico.views.plantdetail.PlantDetailView
import com.kreinto.chefico.views.poicreation.PoiCreationView
import com.kreinto.chefico.views.poidetail.PoiDetailView
import com.kreinto.chefico.views.poilist.PoiListView
import com.kreinto.chefico.views.settings.SettinsView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Che Fico! Main Activity.
 *
 */
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {
  /**
   * [NavHostController] for views navigation.
   */
  private lateinit var navController: NavHostController

  /**
   * If the app is lacking the specified permission, requests it.
   *
   * @param permission permission to request.
   * @param launcher [ActivityResultLauncher] to execute on the user decision.
   */
  private fun requestPerm(permission: String, launcher: ActivityResultLauncher<String>) {
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
        alertBuilder.create().show()
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
   * Returns an [ActivityResultLauncher] for permission requests that navigates to the given route if the user grants the permission.
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

    val viewModel by viewModels<CheFicoViewModel>()
    val authViewModel by viewModels<AuthViewModel>()

    SettingsManager(this).refreshTheme()
    PoiNotificationManager.createNotificationChannel(this)
    if (authViewModel.isUserSignedIn()) {
      authViewModel.isOnlineBackupActive { onlineBackup ->
        if (onlineBackup) {
          lifecycleScope.launch(Dispatchers.IO) {
            authViewModel.backup(viewModel.getPois().first()) {
              authViewModel.getPois { pois -> pois.forEach { poi -> viewModel.updatePoi(poi) } }
            }
          }
        }
      }
    }
    @SuppressLint("SourceLockedOrientationActivity")
    this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    val requestLocationPermLauncherMaps = getPermissionLauncher(CheFicoRoute.Maps.path)
    val requestLocationPermLauncherPoi = getPermissionLauncher(CheFicoRoute.PoiCreation.path)
    val requestLocationPermLauncher: (String) -> ActivityResultLauncher<String> = {
      when (it) {
        CheFicoRoute.Maps.path -> requestLocationPermLauncherMaps
        CheFicoRoute.PoiCreation.path -> requestLocationPermLauncherPoi
        else -> requestLocationPermLauncherMaps
      }
    }
    val requestCameraPermLauncher = getPermissionLauncher(CheFicoRoute.Camera.path)
    val requestGenericPermLauncher = getPermissionLauncher {}
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      requestPerm(POST_NOTIFICATIONS, requestGenericPermLauncher)
      requestPerm(READ_MEDIA_IMAGES, requestGenericPermLauncher)
      requestPerm(SCHEDULE_EXACT_ALARM, requestGenericPermLauncher)
    }

    setContent {
      CheFicoTheme {
        navController = rememberNavController()
        val onNavigate: (String) -> Unit = {
          when (it) {
            CheFicoRoute.Back.path -> {
              navController.popBackStack()
              if (navController.currentDestination?.route == CheFicoRoute.PlantDetail.path) {
                navController.popBackStack()
              }
            }
            CheFicoRoute.Maps.path, CheFicoRoute.PoiCreation.path -> requestPerm(ACCESS_FINE_LOCATION, requestLocationPermLauncher(it))
            CheFicoRoute.Camera.path -> requestPerm(CAMERA, requestCameraPermLauncher)
            else -> navController.navigate(it)
          }
        }

        NavHost(navController, CheFicoRoute.Dashboard.path) {
          composable(CheFicoRoute.Dashboard.path) { DashboardView(onNavigate, viewModel) }
          composable(CheFicoRoute.Maps.path) {
            MapsView(
              onNavigate,
              viewModel,
              locationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity),
              locationSettingsClient = LocationServices.getSettingsClient(this@MainActivity)
            )
          }
          composable(CheFicoRoute.Settings.path) { SettinsView(onNavigate, viewModel, authViewModel) }
          composable(CheFicoRoute.PoiList.path) { PoiListView(onNavigate, viewModel) }
          composable(CheFicoRoute.PoiDetail.path, listOf(navArgument("poiId") { type = NavType.StringType })) {
            PoiDetailView(onNavigate, it.arguments?.getString("poiId"), viewModel, authViewModel)
          }
          composable(CheFicoRoute.Camera.path) { CameraView(onNavigate) }
          composable(CheFicoRoute.PoiCreation.path) {
            PoiCreationView(
              onNavigate,
              viewModel,
              authViewModel,
              locationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity),
            )
          }
          composable(CheFicoRoute.PlantDetail.path, listOf(
            navArgument("imageName") { type = NavType.StringType },
            navArgument("organ") { type = NavType.StringType }
          )) { PlantDetailView(onNavigate, it.arguments?.getString("imageName"), it.arguments?.getString("organ"), viewModel) }
          composable(CheFicoRoute.Signin.path) { AccountSigninView(onNavigate, authViewModel) }
          composable(CheFicoRoute.Login.path) { AccountLoginView(onNavigate, authViewModel) }
          composable(CheFicoRoute.Account.path) { AccountView(onNavigate, viewModel, authViewModel) }
          composable(CheFicoRoute.AccountEdit.path) { AccountEditView(onNavigate, authViewModel) }
          composable(CheFicoRoute.Blacklist.path) { BlackListView(onNavigate, authViewModel) }
        }
      }
    }
  }
}
