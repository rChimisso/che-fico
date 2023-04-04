package com.kreinto.chefico.views.account.login

import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountLoginTopAppBar(onNavigate: (String) -> Unit) {
  TopAppBar(
    colors = topAppBarColors(Color.Transparent),
    title = { Text("Impostazioni") },
    navigationIcon = {
      IconButton(onClick = { onNavigate(CheFicoRoute.Settings.path) }) {
        Icon(painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = "Back")
      }
    }
  )
}