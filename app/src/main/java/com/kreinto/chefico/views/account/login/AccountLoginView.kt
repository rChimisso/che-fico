package com.kreinto.chefico.views.account.login

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kreinto.chefico.components.frames.topbars.SimpleTopBar
import com.kreinto.chefico.room.AuthViewModel

@ExperimentalMaterial3Api
@Composable
fun AccountLoginView(authViewModel: AuthViewModel, onNavigate: (String) -> Unit) {
  Scaffold(
    topBar = { SimpleTopBar(onNavigate) },
    content = { AccountLoginContent(authViewModel, it, onNavigate) },
    containerColor = Color.Black
  )
}

