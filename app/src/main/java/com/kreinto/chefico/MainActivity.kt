package com.kreinto.chefico

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavHostController
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
import com.kreinto.chefico.views.camera.CameraView
import com.kreinto.chefico.views.dashboard.DashboardView
import com.kreinto.chefico.views.maps.MapsView
import com.kreinto.chefico.views.plantdetail.PlantDetailView
import com.kreinto.chefico.views.poicreation.PoiCreationView
import com.kreinto.chefico.views.poidetail.PoiDetailView
import com.kreinto.chefico.views.poilist.PoiListView
import com.kreinto.chefico.views.settings.SettinsView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*


sealed class AppRoute(val route: String, val arg: String = "") {
  object Back : AppRoute("back")
  object Dashboard : AppRoute("dashboard")
  object Settings : AppRoute("settings")
  object Maps : AppRoute("maps")
  object Camera : AppRoute("camera")
  object PoiList : AppRoute("poilist")
  object PoiDetail : AppRoute("poidetail/{poiId}", "poiId")
  object PoiCreation : AppRoute("poicreation")
  object PlantDetail : AppRoute("plantdetail/{imageName}", "imageName")

  fun route(vararg args: Pair<String, String>): String {
    var routes = route
    args.forEach { pair ->
      routes = routes.replace(
        "{${pair.first}}",
        pair.second
      )
    }
    println(routes)
    return routes
  }
}


@SuppressLint("MissingPermission")
fun showSimpleNotification(
  context: Context,
  channelId: String,
  notificationId: Int,
  textTitle: String,
  textContent: String,
  priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {
  val notificationBuilder = NotificationCompat.Builder(context, channelId)
    .setSmallIcon(R.drawable.ic_launcher_foreground)
    .setContentTitle(textTitle)
    .setContentText(textContent)
    .setPriority(priority)

  with(NotificationManagerCompat.from(context)) {
    notify(notificationId, notificationBuilder.build())
  }
}

class PoiNotificationManager : BroadcastReceiver() {
  companion object {
    private const val channelId: String = "CheFicoChannel"
    private const val notificationId: Int = 1
    private const val channelName: String = "Che Fico!"
    private const val channelDescription: String = "Default channel"
    private const val titleExtra = "titleExtra"
    private const val messageExtra = "messageExtra"

    fun createNotificationChannel(context: Context) {
      val importance = NotificationManager.IMPORTANCE_DEFAULT

      val channel = NotificationChannel(channelId, channelName, importance)
      channel.description = channelDescription

      with(context.getSystemService<NotificationManager>()) {
        this?.createNotificationChannel(channel)
      }
    }

    fun scheduleNotification(context: Context, time: Long, title: String, message: String) {
      val intent = Intent(context, PoiNotificationManager::class.java)
      intent.putExtra(titleExtra, title)
      intent.putExtra(messageExtra, message)

      val pendingIntent = PendingIntent.getBroadcast(
        context,
        notificationId,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
      )

      with(context.getSystemService<AlarmManager>()) {
        this?.set(
          AlarmManager.RTC,
          time,
          pendingIntent
        )
      }
    }

    fun showNotification(context: Context, title: String, message: String) {
      val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

      with(context.getSystemService<NotificationManager>()) {
        this?.notify(1, notification)
      }
    }
  }


  override fun onReceive(context: Context, intent: Intent) {
    val notification: Notification = NotificationCompat.Builder(context, channelId)
      .setSmallIcon(R.drawable.ic_launcher_foreground)
      .setContentTitle(intent.getStringExtra(titleExtra))
      .setContentText(intent.getStringExtra(messageExtra))
      .build()

    with(context.getSystemService<NotificationManager>()) {
      this?.notify(notificationId, notification)
    }
  }
}


@ExperimentalLifecycleComposeApi
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalGetImage
class MainActivity : ComponentActivity() {
  private lateinit var fusedLocationClient: FusedLocationProviderClient
  private lateinit var locationSettingsClient: SettingsClient
  private lateinit var navController: NavHostController

  fun checkPermission(permission: String) = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    locationSettingsClient = LocationServices.getSettingsClient(this)
    val requestLocationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
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
    val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
      if (it) {
        navController.navigate(AppRoute.Camera.route)
      } else {
        // Explain to the user that the feature is unavailable because the
        // feature requires a permission that the user has denied. At the
        // same time, respect the user's decision. Don't link to system
        // settings in an effort to convince the user to change their
        // decision.
      }
    }
    val requestNotificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
      if (!it) {
        // Explain to the user that the feature is unavailable because the
        // feature requires a permission that the user has denied. At the
        // same time, respect the user's decision. Don't link to system
        // settings in an effort to convince the user to change their
        // decision.
      }
    }
    if (checkPermission(POST_NOTIFICATIONS)) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, POST_NOTIFICATIONS)) {
        // In an educational UI, explain to the user why your app requires this
        // permission for a specific feature to behave as expected, and what
        // features are disabled if it's declined. In this UI, include a
        // "cancel" or "no thanks" button that lets the user continue
        // using your app without granting the permission.
        // showInContextUI(...)
      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        requestNotificationPermissionLauncher.launch(POST_NOTIFICATIONS)
      }
    }

    setContent {
      val context = LocalContext.current
      CheFicoTheme {
        LaunchedEffect(Unit) {
          PoiNotificationManager.createNotificationChannel(context)
          PoiNotificationManager.scheduleNotification(context, Calendar.getInstance().timeInMillis + 10000, "hello", "world")
          PoiNotificationManager.showNotification(context, "ciao", "modno")
        }

        navController = rememberNavController()
        val onNavigate: (route: String) -> Unit = {
          if (it == AppRoute.Back.route) {
            navController.popBackStack()
            if (navController.currentDestination?.route == AppRoute.PlantDetail.route) {
              navController.popBackStack()
            }
          } else if (it == AppRoute.Maps.route && checkPermission(ACCESS_FINE_LOCATION)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)) {
              // In an educational UI, explain to the user why your app requires this
              // permission for a specific feature to behave as expected, and what
              // features are disabled if it's declined. In this UI, include a
              // "cancel" or "no thanks" button that lets the user continue
              // using your app without granting the permission.
              // showInContextUI(...)
            } else {
              requestLocationPermissionLauncher.launch(ACCESS_FINE_LOCATION)
            }
          } else if (it == AppRoute.Camera.route && checkPermission(CAMERA)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, CAMERA)) {
              // In an educational UI, explain to the user why your app requires this
              // permission for a specific feature to behave as expected, and what
              // features are disabled if it's declined. In this UI, include a
              // "cancel" or "no thanks" button that lets the user continue
              // using your app without granting the permission.
              // showInContextUI(...)
            } else {
              requestCameraPermissionLauncher.launch(CAMERA)
            }
          } else {
            navController.navigate(it)
          }
        }
        val viewModel by viewModels<CheFicoViewModel>()
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
              viewModel = viewModel,
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
          composable(AppRoute.Camera.route) {
            CameraView(onNavigate = onNavigate)
          }
          composable(AppRoute.PoiCreation.route) {
            PoiCreationView(onNavigate = onNavigate)
          }
          composable(
            AppRoute.PlantDetail.route,
            arguments = listOf(
              navArgument("imageName") {
                type = NavType.StringType
              }
            )
          ) { backStackEntry ->
            PlantDetailView(
              onNavigate = onNavigate,
              imageName = backStackEntry.arguments?.getString("imageName")
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

