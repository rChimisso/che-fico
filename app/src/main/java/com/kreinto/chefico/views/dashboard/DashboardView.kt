package com.kreinto.chefico.views.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.R
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.views.dashboard.components.DashboardContent
import com.kreinto.chefico.views.dashboard.components.DashboardNavigationContent

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun DashboardView(onNavigate: (String) -> Unit, viewModel: CheFicoViewModel) {
  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        navigationIcon = { Image(painterResource(R.drawable.che_fico_icon), null, Modifier.size(64.dp)) },
        title = { Text(stringResource(R.string.app_name)) }
      )
    },
    bottomBar = { DashboardNavigationContent(onNavigate) }
  ) { DashboardContent(onNavigate, viewModel) }
}
