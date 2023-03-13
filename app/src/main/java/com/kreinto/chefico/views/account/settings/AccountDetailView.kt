package com.kreinto.chefico.views.account.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.room.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailView(authViewModel: AuthViewModel) {
  val user by authViewModel.user.collectAsState()

  if (user != null) {
    var email by rememberSaveable { mutableStateOf(user!!.email ?: "") }
    var onChange by remember { mutableStateOf(false) }

    Column(
      Modifier
        .padding(32.dp)
        .fillMaxSize()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(40.dp)
      ) {
        Text("Credenziali")
      }
      TextField(
        value = email,
        onValueChange = {
          email = it
          onChange = true
        },
        label = {
          Text("Email")
        },
        readOnly = authViewModel.getUserProviderIds().contains("google.com")
      )
      if (!authViewModel.getUserProviderIds().contains("google.com")) {
        Row {
          Text("Cambia password")
        }
      }
      Spacer(modifier = Modifier.weight(1f))
      AnimatedVisibility(visible = onChange, enter = fadeIn(), exit = fadeOut()) {
        Button(onClick = {
          user!!.updateEmail(email)
          onChange = false
        }) {
          Text("Conferma modifiche")
        }
      }
    }
  }
}