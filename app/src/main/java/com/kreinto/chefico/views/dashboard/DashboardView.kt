package com.kreinto.chefico.views.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.views.dashboard.components.DashboardBottomSheetContent
import com.kreinto.chefico.views.dashboard.components.DashboardContent

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun DashboardView(onNavigate: (String) -> Unit, viewModel: CheFicoViewModel) {
  val scaffoldState = rememberBottomSheetScaffoldState()
  BottomSheetScaffold(
    sheetContent = { DashboardBottomSheetContent(onNavigate) },
    scaffoldState = scaffoldState,
    sheetPeekHeight = 48.dp,
    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    sheetContainerColor = MaterialTheme.colorScheme.primaryContainer,
    sheetContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    sheetDragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xff32C896)) },
    sheetSwipeEnabled = true,
    containerColor = MaterialTheme.colorScheme.background,
    contentColor = MaterialTheme.colorScheme.onBackground
  ) { DashboardContent(onNavigate, viewModel) }
}
