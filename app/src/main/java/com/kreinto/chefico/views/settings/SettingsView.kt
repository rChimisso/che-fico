package com.kreinto.chefico.views.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.kreinto.chefico.Route
import com.kreinto.chefico.room.AuthViewModel

@ExperimentalMaterial3Api
@Composable
fun SettinsView(authViewModel: AuthViewModel, onNavigate: (String) -> Unit) {
  Column {
    TextButton(onClick = {
      if (authViewModel.isUserLoggedIn()) {
        onNavigate(Route.Account.path)
      } else {
        onNavigate(Route.Login.path)
      }
    }) {
      Text("Account")
    }
  }
}
