package com.kreinto.chefico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kreinto.chefico.ui.theme.CheFicoTheme
import com.kreinto.chefico.views.dashboard.DashboardView
import com.kreinto.chefico.views.maps.MapsView
import com.kreinto.chefico.views.poidetail.PoiDetailView
import com.kreinto.chefico.views.poilist.PoiListView
import com.kreinto.chefico.views.settings.SettinsView

sealed class AppRoute(val route: String) {
  object Dashboard : AppRoute("dashboard")
  object Settings : AppRoute("settings")
  object Maps : AppRoute("maps")
  object Camera : AppRoute("camera")
  object PoiList : AppRoute("poilist")
  object PoiDetail : AppRoute("poidetail")
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
            PoiListView(onNavigate = { navController.navigate(it) })
          }
          composable(AppRoute.PoiDetail.route) {
            PoiDetailView(onNavigate = { navController.navigate(it) })
          }
        }
      }
    }
  }
}


