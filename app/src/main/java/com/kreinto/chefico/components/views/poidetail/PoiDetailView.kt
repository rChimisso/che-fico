package com.kreinto.chefico.components.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kreinto.chefico.components.views.poidetail.*


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun PoiDetailView() {
  Scaffold(
    topBar = { PoiDetailTopAppBar() },
    bottomBar = {},
    content = {
      Column {
        PoiDetailHeader()
        PoiDetailDescription()
        Column(
          modifier = Modifier
            .weight(1f)
            .padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          for (index in 0..4) {
            PoiDetailEvent()
          }
        }
        Spacer(modifier = Modifier.height(16.dp))
      }
    },
  )
}

@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
@Preview
fun PoiDetailPreviw() {
  PoiDetailView()
}