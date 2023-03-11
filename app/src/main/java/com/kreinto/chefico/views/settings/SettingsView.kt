package com.kreinto.chefico.views.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.kreinto.chefico.views.account.AccountSigninView

@ExperimentalMaterial3Api
@Composable
fun SettinsView(onNavigate: (String) -> Unit) {
  AccountSigninView(onNavigate)
}
