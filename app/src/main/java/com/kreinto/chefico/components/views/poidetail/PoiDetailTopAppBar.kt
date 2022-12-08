package com.kreinto.chefico.components.views.poidetail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kreinto.chefico.components.buttons.RoundButton

@ExperimentalMaterial3Api
@Composable
fun PoiDetailTopAppBar() {
  TopAppBar(
    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    navigationIcon = {
      RoundButton(icon = Icons.Default.ArrowBack, onClick = { /*TODO*/ })

    },
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
      containerColor = Color.Transparent
    ),
    actions = {

    },
    title = {}
  )
}