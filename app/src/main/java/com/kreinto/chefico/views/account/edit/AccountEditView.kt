package com.kreinto.chefico.views.account.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.R
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.inputs.TextInput
import com.kreinto.chefico.room.viewmodels.AuthViewModel

@ExperimentalMaterial3Api
@Composable
fun AccountEditView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  SimpleFrame(onNavigate) { padding ->
    var email by rememberSaveable { mutableStateOf(authViewModel.currentUser?.email ?: "") }
    var userName by rememberSaveable { mutableStateOf(authViewModel.currentUser?.displayName ?: "") }
    var passwordNew1 by rememberSaveable { mutableStateOf("") }
    var passwordNew2 by rememberSaveable { mutableStateOf("") }

    var newPasswordVisible1 by rememberSaveable { mutableStateOf(false) }
    var newPasswordVisible2 by rememberSaveable { mutableStateOf(false) }

    var openConfirmDialog by rememberSaveable { mutableStateOf(false) }

    if (openConfirmDialog) {
      var password by rememberSaveable { mutableStateOf("") }
      Dialog({ openConfirmDialog = false }) {
        Column {
          TextInput { password = it }
          Button(
            onClick = {
              openConfirmDialog = false
              authViewModel.updateUserInfo(password, email, passwordNew1, userName)
            },
            contentPadding = ButtonDefaults.TextButtonContentPadding
          ) { Text(stringResource(R.string.confirm_changes), color = MaterialTheme.colorScheme.error) }
        }
      }
    }

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
            onValueChange = { userName = it },
            label = { Text(stringResource(R.string.user_name_area_edit_label)) },
            readOnly = authViewModel.getUserProviderIds().contains("google.com"),
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
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.email_label)) },
            readOnly = authViewModel.getUserProviderIds().contains("google.com"),
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
            onValueChange = { passwordNew1 = it },
            label = { Text(stringResource(R.string.new_pwd_area_edit_label)) },
            singleLine = true,
            placeholder = { Text(stringResource(R.string.new_pwd_placeholder_area_edit_label)) },
            visualTransformation = if (newPasswordVisible1) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
              IconButton({ newPasswordVisible1 = !newPasswordVisible1 }) {
                Icon(
                  if (newPasswordVisible1) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                  if (newPasswordVisible1) "Hide password" else "Show password"
                )
              }
            },
            readOnly = authViewModel.getUserProviderIds().contains("google.com"),
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
            isError = passwordNew1 != passwordNew2,
            onValueChange = { passwordNew2 = it },
            label = { Text(stringResource(R.string.new_pwd_area_edit_label)) },
            singleLine = true,
            placeholder = { Text(stringResource(R.string.new_pwd_placeholder_area_edit_label)) },
            visualTransformation = if (newPasswordVisible2) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
              IconButton({ newPasswordVisible2 = !newPasswordVisible2 }) {
                Icon(
                  if (newPasswordVisible2) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                  if (newPasswordVisible2) "Hide password" else "Show password"
                )
              }
            },
            readOnly = authViewModel.getUserProviderIds().contains("google.com"),
            shape = RoundedCornerShape(0.dp)
          )
        }
      }
      if (email != authViewModel.currentUser?.email || userName != authViewModel.currentUser?.displayName || passwordNew1.isNotBlank() && passwordNew1.length >= 6 && passwordNew1 == passwordNew2) {
        Button(
          onClick = { openConfirmDialog = true },
          contentPadding = ButtonDefaults.TextButtonContentPadding
        ) { Text(stringResource(R.string.confirm_changes), color = MaterialTheme.colorScheme.error) }
      }
    }
  }
}
