package com.kreinto.chefico.views.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun DashboardView(onNavigate: (String) -> Unit) {
  val scaffoldState = rememberBottomSheetScaffoldState()
  BottomSheetScaffold(
    sheetContent = { DashboardBottomSheetContent(onNavigate) },
    scaffoldState = scaffoldState,
    sheetPeekHeight = 48.dp,
    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    sheetContainerColor = Color(0xFF262724),
    sheetContentColor = Color(0xff32C896),
    // TODO:
    //  Trovare soluzione per la mancanza del FAB, https://issuetracker.google.com/issues/273066268
    //  floatingActionButtonPosition = FabPosition.End,
    //  floatingActionButton = { DashboardFloatingActionButton() },
    sheetDragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xff32C896)) },
    sheetSwipeEnabled = true,
    containerColor = Color(0xff20211e)
  ) { DashboardContent() }
}
