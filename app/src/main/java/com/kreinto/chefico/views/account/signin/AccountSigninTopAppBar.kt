package com.kreinto.chefico.views.account.signin

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.kreinto.chefico.R
import com.kreinto.chefico.Route


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountSigninTopAppBar(onNavigate: (String) -> Unit) {
  TopAppBar(
    colors = TopAppBarDefaults.smallTopAppBarColors(Color.Transparent),
    title = { Text("Accedi") },
    navigationIcon = {
      IconButton(onClick = { onNavigate(Route.Login.path) }) {
        Icon(painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = "Back")
      }
    }
  )
}