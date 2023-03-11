package com.kreinto.chefico.views.account

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountLoginView(onNavigate: (String) -> Unit) {
  Scaffold(
    topBar = { AccountLoginTopAppBar(onNavigate) },
    content = { AccountLoginContent(it, onNavigate) },
    containerColor = Color.Black
  )
}

