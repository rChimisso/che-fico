package com.kreinto.chefico.views.account.edit

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.inputs.TextInput
import com.kreinto.chefico.room.viewmodels.AuthViewModel
import com.kreinto.chefico.views.account.signin.components.isValidEmail

@ExperimentalMaterial3Api
@Composable
fun AccountEditView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  SimpleFrame(onNavigate) { padding ->
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf(authViewModel.currentUser?.email ?: "") }
    var username by rememberSaveable { mutableStateOf(authViewModel.currentUser?.displayName ?: "") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var repeatedNewPassword by rememberSaveable { mutableStateOf("") }
    var openConfirmDialog by rememberSaveable { mutableStateOf(false) }
    var currPassword by rememberSaveable { mutableStateOf("") }

    if (openConfirmDialog) {
      Dialog({ openConfirmDialog = false }) {
        Column {
          TextInput(
            placeholder = { Text("Conferma inserendo la password corrente") }
          ) {
            currPassword = it
          }
          Button(
            onClick = {
              openConfirmDialog = false
              authViewModel.updateUserInfo(currPassword, email, newPassword, username) {
                if (it) {
                  onNavigate(CheFicoRoute.Back.path)
                } else {
                  Toast.makeText(context, "Modifiche non riuscite", Toast.LENGTH_SHORT).show()

                }
              }
            },
            contentPadding = ButtonDefaults.TextButtonContentPadding
          ) { Text(stringResource(R.string.confirm_changes), color = MaterialTheme.colorScheme.error) }
        }
      }
    }
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(top = padding.calculateTopPadding(), start = 24.dp, end = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      TextInput(
        init = username,
        label = stringResource(R.string.user_name_area_edit_label),
        isError = username.isBlank()
      ) {
        username = it
      }
      Spacer(modifier = Modifier.height(16.dp))
      TextInput(
        init = email,
        label = stringResource(R.string.email_label),
        isError = !email.isValidEmail()
      ) {
        email = it
      }
      Spacer(modifier = Modifier.height(16.dp))
      TextInput(
        label = stringResource(R.string.new_pwd_area_edit_label),
        isPassword = true,
        isError = newPassword.length < 6
      ) {
        newPassword = it
      }
      Spacer(modifier = Modifier.height(16.dp))
      TextInput(
        label = stringResource(R.string.new_pwd_area_edit_label),
        isPassword = true,
        isError = newPassword != repeatedNewPassword
      ) {
        repeatedNewPassword = it
      }
      Spacer(modifier = Modifier.height(16.dp))
      if (
        email.isValidEmail() && email != authViewModel.currentUser?.email ||
        username.isNotBlank() && username != authViewModel.currentUser?.displayName ||
        newPassword.isNotBlank() && newPassword.length >= 6 && newPassword == repeatedNewPassword
      ) {
        Button(
          onClick = { openConfirmDialog = true },
          contentPadding = ButtonDefaults.TextButtonContentPadding
        ) { Text(stringResource(R.string.confirm_changes), color = MaterialTheme.colorScheme.error) }
      }
    }
  }
}
