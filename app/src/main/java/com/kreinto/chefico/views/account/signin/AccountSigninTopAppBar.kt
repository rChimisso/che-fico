package com.kreinto.chefico.views.account.signin

import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountSigninTopAppBar(onNavigate: (String) -> Unit) {
  TopAppBar(
    colors = topAppBarColors(Color.Transparent),
    title = { Text("Accedi") },
    navigationIcon = {
      IconButton(onClick = { onNavigate(CheFicoRoute.Login.path) }) {
        Icon(painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = "Back")
      }
    }
  )
}