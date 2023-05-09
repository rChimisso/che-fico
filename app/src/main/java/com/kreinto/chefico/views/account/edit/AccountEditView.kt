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
import androidx.compose.ui.res.painterResource
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
    var passwordOld by rememberSaveable { mutableStateOf("") }
    var passwordNew1 by rememberSaveable { mutableStateOf("") }
    var passwordNew2 by rememberSaveable { mutableStateOf("") }
    var oldPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var newPasswordVisible1 by rememberSaveable { mutableStateOf(false) }
    var newPasswordVisible2 by rememberSaveable { mutableStateOf(false) }
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
        Box(
          modifier =  Modifier.size(128.dp)
        ){
          Icon(
            painter = painterResource(id = R.drawable.ic_photo_camera),
            contentDescription = "Modifica Immagine",
            modifier = Modifier
              .size(48.dp)
              .align(Alignment.Center), //Da modificare a piacimento
          )
          AsyncImage(
            model = Firebase.auth.currentUser?.photoUrl ?: "",
            contentDescription = "",
            modifier = Modifier
              .fillMaxSize()
              .clip(CircleShape)
              .align(Alignment.Center),
          )
        }

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
            value = passwordOld,
            enabled = modify,
            onValueChange = {
              passwordOld = it
            },
            label = {
              Text("Password Vecchia")
            },
            singleLine = true,
            placeholder = { Text("La vecchia Password") },
            visualTransformation = if (oldPasswordVisible && modify) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
              val image = if (oldPasswordVisible && modify)
                Icons.Filled.Visibility
              else Icons.Filled.VisibilityOff
              val description = if (oldPasswordVisible) "Hide password" else "Show password"
              IconButton(onClick = {oldPasswordVisible = !oldPasswordVisible}){
                Icon(imageVector  = image, description)
              }
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
            value = passwordNew1,
            enabled = modify,
            onValueChange = {
              passwordNew1 = it
            },
            label = {
              Text("Password Nuova")
            },
            singleLine = true,
            placeholder = { Text("La nuova Password") },
            visualTransformation = if (newPasswordVisible1 && modify) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
              val image = if (newPasswordVisible1 && modify)
                Icons.Filled.Visibility
              else Icons.Filled.VisibilityOff
              val description = if (newPasswordVisible1) "Hide password" else "Show password"
              IconButton(onClick = {newPasswordVisible1 = !newPasswordVisible1}){
                Icon(imageVector  = image, description)
              }
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
            value = passwordNew2,
            enabled = modify,
            isError = passwordNew1 != passwordNew2,
            onValueChange = {
              passwordNew2 = it
            },
            label = {
              Text("Password Nuova")
            },
            singleLine = true,
            placeholder = { Text("La nuova Password") },
            visualTransformation = if (newPasswordVisible2 && modify) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
              val image = if (newPasswordVisible2 && modify)
                Icons.Filled.Visibility
              else Icons.Filled.VisibilityOff
              val description = if (newPasswordVisible2) "Hide password" else "Show password"
              IconButton(onClick = {newPasswordVisible2 = !newPasswordVisible2}){
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