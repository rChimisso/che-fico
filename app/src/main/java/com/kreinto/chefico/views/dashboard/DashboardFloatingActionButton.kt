package com.kreinto.chefico.views.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.R

@Composable
fun DashboardFloatingActionButton() {
  Image(
    painter = painterResource(R.drawable.che_fico_icon),
    contentDescription = "App logo",
    modifier = Modifier.size(64.dp)
  )
}