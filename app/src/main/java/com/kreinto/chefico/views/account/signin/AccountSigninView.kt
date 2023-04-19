package com.kreinto.chefico.views.account.signin

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kreinto.chefico.components.frames.topbars.SimpleTopBar
import com.kreinto.chefico.room.AuthViewModel

@ExperimentalMaterial3Api
@Composable
fun AccountSigninView(authViewModel: AuthViewModel, onNavigate: (String) -> Unit) {
  Scaffold(
    topBar = { SimpleTopBar(onNavigate) },
    content = { AccountSignInContent(authViewModel, it, onNavigate) },
    containerColor = Color.Black
  )
}


