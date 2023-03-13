package com.kreinto.chefico.views.account

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.views.account.signin.AccountSigninTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSigninView(authViewModel: AuthViewModel, onNavigate: (String) -> Unit) {
  Scaffold(
    topBar = { AccountSigninTopAppBar(onNavigate) },
    content = { AccountSignInContent(authViewModel, it, onNavigate) },
    containerColor = Color.Black
  )
}


