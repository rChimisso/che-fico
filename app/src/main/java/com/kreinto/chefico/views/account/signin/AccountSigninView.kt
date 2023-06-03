package com.kreinto.chefico.views.account.signin

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.views.account.signin.components.AccountSignInContent

@ExperimentalMaterial3Api
@Composable
fun AccountSigninView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  SimpleFrame(onNavigate) { AccountSignInContent(authViewModel, it, onNavigate) }
}


