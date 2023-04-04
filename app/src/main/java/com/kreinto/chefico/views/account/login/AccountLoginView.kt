package com.kreinto.chefico.views.account

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.views.account.login.AccountLoginTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountLoginView(authViewModel: AuthViewModel, onNavigate: (String) -> Unit) {
  Scaffold(
    topBar = { AccountLoginTopAppBar(onNavigate) },
    content = { AccountLoginContent(authViewModel, it, onNavigate) },
    containerColor = Color.Black
  )
}

