package com.kreinto.chefico.views.account.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Visibility
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.room.AuthViewModel

@ExperimentalMaterial3Api
@Composable
fun AccountEditView (onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  SimpleFrame(
    onBackPressed = onNavigate,
  ) { it ->
    var email by rememberSaveable { mutableStateOf(Firebase.auth.currentUser?.email ?: "") }
    var userName by rememberSaveable { mutableStateOf(Firebase.auth.currentUser?.displayName ?: "") }
    var modify by remember { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier
        .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp, bottom = 16.dp)
        .fillMaxSize()
    ) {
      Spacer(modifier = Modifier.height(64.dp))
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
      Column(
        modifier = Modifier
          .fillMaxSize()
          .weight(1f)
      ) {

        Row(
          modifier = Modifier
            .fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextField(
            value = userName,
            enabled = modify,
            onValueChange = {
              userName = it
            },
            label = {
              Text("User Name")
            },
            readOnly = authViewModel.getUserProviderIds().contains("google.com") && !modify,
            shape = RoundedCornerShape(0.dp)
          )
        }

        Row(
          modifier = Modifier
            .fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextField(
            value = email,
            enabled = modify,
            onValueChange = {
              email = it
            },
            label = {
              Text("Email")
            },
            readOnly = authViewModel.getUserProviderIds().contains("google.com") && !modify,
            shape = RoundedCornerShape(0.dp)
          )
        }

        Row(
          modifier = Modifier
            .fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextField(
            value = password,
            enabled = modify,
            onValueChange = {
              password = it
            },
            label = {
              Text("Password")
            },
            singleLine = true,
            placeholder = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
              val image = if (passwordVisible)
                Icons.Filled.Visibility
              else Icons.Filled.VisibilityOff
              val description = if (passwordVisible) "Hide password" else "Show password"
              IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(imageVector  = image, description)
              }
            },
            readOnly = authViewModel.getUserProviderIds().contains("google.com") && !modify,
            shape = RoundedCornerShape(0.dp)
          )
        }

      }
      Button(
        onClick = {
          modify = !modify
        },
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
      ) {
        if (!modify)
          Text("Modifica Dati", color = Color.Red)
        else
          Text("Conferma Modifiche", color = Color.Red)
      }
    }
  }
}