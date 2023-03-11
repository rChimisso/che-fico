package com.kreinto.chefico.views.account

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kreinto.chefico.views.account.signin.AccountSigninContent
import com.kreinto.chefico.views.account.signin.AccountSigninTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSigninView(onNavigate: (String) -> Unit) {
  Scaffold(
    topBar = { AccountSigninTopAppBar(onNavigate) },
    content = { AccountSigninContent(it) },
    containerColor = Color.Black
  )
}


