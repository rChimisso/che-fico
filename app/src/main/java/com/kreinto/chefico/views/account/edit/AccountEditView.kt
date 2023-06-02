package com.kreinto.chefico.views.account.edit

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.R
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.room.AuthViewModel

@ExperimentalMaterial3Api
@Composable
fun AccountEditView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  SimpleFrame(onNavigate) { padding ->
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
        .padding(top = padding.calculateTopPadding(), start = 16.dp, end = 16.dp, bottom = 16.dp)
        .fillMaxSize()
    ) {
      Spacer(Modifier.height(64.dp))
      Surface(
        tonalElevation = 12.dp,
        shape = CircleShape
      ) {
        Box(Modifier.size(128.dp)) {
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
      Spacer(Modifier.height(16.dp))
      Column(
        Modifier
          .fillMaxSize()
          .weight(1f)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextField(
            value = userName,
            enabled = modify,
            onValueChange = { userName = it },
            label = { Text(stringResource(R.string.user_name_area_edit_label)) },
            readOnly = authViewModel.getUserProviderIds().contains("google.com") && !modify,
            shape = RoundedCornerShape(0.dp)
          )
        }
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextField(
            value = email,
            enabled = modify,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.email_label)) },
            readOnly = authViewModel.getUserProviderIds().contains("google.com") && !modify,
            shape = RoundedCornerShape(0.dp)
          )
        }
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextField(
            value = passwordOld,
            enabled = modify,
            onValueChange = { passwordOld = it },
            label = { Text(text = stringResource(R.string.old_pwd_area_edit_label)) },
            singleLine = true,
            placeholder = { Text(text = stringResource(R.string.old_pwd_placeholder_area_edit_label)) },
            visualTransformation = if (oldPasswordVisible && modify) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
              val image = if (oldPasswordVisible && modify) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
              val description = if (oldPasswordVisible) "Hide password" else "Show password"
              IconButton({ oldPasswordVisible = !oldPasswordVisible }) { Icon(image, description) }
            },
            readOnly = authViewModel.getUserProviderIds().contains("google.com") && !modify,
            shape = RoundedCornerShape(0.dp)
          )
        }
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextField(
            value = passwordNew1,
            enabled = modify,
            onValueChange = { passwordNew1 = it },
            label = { Text(stringResource(R.string.new_pwd_area_edit_label)) },
            singleLine = true,
            placeholder = { Text(stringResource(R.string.new_pwd_placeholder_area_edit_label)) },
            visualTransformation = if (newPasswordVisible1 && modify) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
              val image = if (newPasswordVisible1 && modify) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
              val description = if (newPasswordVisible1) "Hide password" else "Show password"
              IconButton({ newPasswordVisible1 = !newPasswordVisible1 }) { Icon(image, description) }
            },
            readOnly = authViewModel.getUserProviderIds().contains("google.com") && !modify,
            shape = RoundedCornerShape(0.dp)
          )
        }
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextField(
            value = passwordNew2,
            enabled = modify,
            isError = passwordNew1 != passwordNew2,
            onValueChange = { passwordNew2 = it },
            label = { Text(stringResource(R.string.new_pwd_area_edit_label)) },
            singleLine = true,
            placeholder = { Text(stringResource(R.string.new_pwd_placeholder_area_edit_label)) },
            visualTransformation = if (newPasswordVisible2 && modify) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
              val image = if (newPasswordVisible2 && modify) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
              val description = if (newPasswordVisible2) "Hide password" else "Show password"
              IconButton({ newPasswordVisible2 = !newPasswordVisible2 }) { Icon(image, description) }
            },
            readOnly = authViewModel.getUserProviderIds().contains("google.com") && !modify,
            shape = RoundedCornerShape(0.dp)
          )
        }
      }
      Button(
        onClick = { modify = !modify },
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
      ) { Text(stringResource(if (!modify) R.string.btn_edit_label else R.string.btn_submit_label), color = Color.Red) }
    }
  }
}