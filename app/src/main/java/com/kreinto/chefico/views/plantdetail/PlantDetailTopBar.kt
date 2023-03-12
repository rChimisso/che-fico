package com.kreinto.chefico.views.plantdetail

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.kreinto.chefico.R

@ExperimentalMaterial3Api
@Composable
fun PlantDetailTopBar(onNavigate: (String) -> Unit) {
  TopAppBar(
    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    title = {},
    navigationIcon = {
      IconButton(onClick = { onNavigate }) {
        Icon(
          painter = painterResource(id = R.drawable.ic_arrow_back),
          contentDescription = "",
          tint = Color(0xFF32C896)
        )
      }
    }
  )
}