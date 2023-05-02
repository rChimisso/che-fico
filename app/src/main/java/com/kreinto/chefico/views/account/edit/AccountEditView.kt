package com.kreinto.chefico.views.account.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.room.AuthViewModel

@ExperimentalMaterial3Api
@Composable
fun AccountEditView (onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  SimpleFrame(
    onBackPressed = onNavigate,
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier
        .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp, bottom = 16.dp)
        .fillMaxSize()
    ) {
      Spacer(modifier = Modifier.height(16.dp))
      Surface(
        tonalElevation = 12.dp,
        shape = CircleShape
      ) {
        AsyncImage(
          model = Firebase.auth.currentUser?.photoUrl ?: "",
          contentDescription = "",
          modifier = Modifier
            .size(128.dp)
            .clip(CircleShape)
        )
      }
      Spacer(modifier = Modifier.height(16.dp))

      var email by rememberSaveable { mutableStateOf(Firebase.auth.currentUser?.email ?: "") }
      var userName by rememberSaveable { mutableStateOf(Firebase.auth.currentUser?.displayName ?: "") }
      var modify by remember { mutableStateOf(false) }

      TextField(
        value = userName,
        onValueChange = {
          userName = it
        },
        label = {
          Text("User Name")
        },
        readOnly = authViewModel.getUserProviderIds().contains("google.com") && !modify
      )

      TextField(
        value = email,
        onValueChange = {
          email = it
        },
        label = {
          Text("Email")
        },
        readOnly = authViewModel.getUserProviderIds().contains("google.com") && !modify
      )
      Button(
        onClick = {
          modify = !modify
        },
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
      ) {
        Text("Modifica Dati", color = Color.Red)
      }
    }
  }
}