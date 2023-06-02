package com.kreinto.chefico.views.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.R
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.views.dashboard.components.DashboardBottomSheetContent
import com.kreinto.chefico.views.dashboard.components.DashboardContent

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun DashboardView(onNavigate: (String) -> Unit, viewModel: CheFicoViewModel) {
  BottomSheetScaffold(
    sheetContent = { DashboardBottomSheetContent(onNavigate) },
    sheetPeekHeight = 48.dp,
    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    sheetContainerColor = MaterialTheme.colorScheme.primaryContainer,
    sheetContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    sheetDragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xff32C896)) },
    sheetSwipeEnabled = true,
    topBar = {
      CenterAlignedTopAppBar(
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        navigationIcon = { Image(painterResource(R.drawable.che_fico_icon), null, Modifier.size(64.dp)) },
        title = { Text("Che Fico!") }
      )
    },
    containerColor = MaterialTheme.colorScheme.background,
    contentColor = MaterialTheme.colorScheme.onBackground
  ) { DashboardContent(onNavigate, viewModel) }
}
