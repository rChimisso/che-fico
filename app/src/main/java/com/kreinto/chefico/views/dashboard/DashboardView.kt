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
import com.kreinto.chefico.views.dashboard.components.DashboardBottomSheetContent
import com.kreinto.chefico.views.dashboard.components.DashboardContent

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
    sheetDragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xff32C896)) },
    sheetSwipeEnabled = true,
    containerColor = Color(0xff20211e),
    contentColor = Color(0xff20211e)
  ) { DashboardContent() }
}
