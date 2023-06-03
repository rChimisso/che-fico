package com.kreinto.chefico.views.account.login

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.views.account.login.components.AccountLoginContent

@ExperimentalMaterial3Api
@Composable
fun AccountLoginView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  SimpleFrame(onNavigate) { AccountLoginContent(authViewModel, it, onNavigate) }
}

