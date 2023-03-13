package com.kreinto.chefico.views.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun DashboardView(onNavigate: (String) -> Unit) {
  var scaffoldState = rememberBottomSheetScaffoldState()
  BottomSheetScaffold(
    drawerGesturesEnabled = false,
    scaffoldState = scaffoldState,
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = { DashboardFloatingActionButton() },
    backgroundColor = MaterialTheme.colorScheme.background,
    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    sheetGesturesEnabled = true,
    sheetBackgroundColor = MaterialTheme.colorScheme.surface,
    sheetElevation = 12.dp,
    sheetPeekHeight = 48.dp,
    sheetContent = { DashboardBottomSheetContent(scaffoldState.bottomSheetState, onNavigate) },
    content = { DashboardContent() }
  )
}

