package com.kreinto.chefico

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
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

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@ExperimentalPagerApi
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CheFicoTheme {
        val viewModel by viewModels<CheFicoViewModel>()
        val navController = rememberNavController()

        NavHost(
          navController = navController,
          startDestination = AppRoute.Dashboard.route,
        ) {
          composable(AppRoute.Dashboard.route) {
            DashboardView(onNavigate = { navController.navigate(it) })
          }
          composable(AppRoute.Maps.route) {
            MapsView(onNavigate = { navController.navigate(it) })
          }
          composable(AppRoute.Settings.route) {
            SettinsView(onNavigate = { navController.navigate(it) })
          }
          composable(AppRoute.PoiList.route) {
            PoiListView(viewModel = viewModel, onNavigate = { navController.navigate(it) })
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
              onNavigate = { navController.navigate(it) },
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

