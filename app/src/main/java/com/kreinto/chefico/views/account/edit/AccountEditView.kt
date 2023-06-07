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
import androidx.compose.ui.window.Dialog
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.inputs.TextInput
import com.kreinto.chefico.isValidEmail
import com.kreinto.chefico.isValidPassword
import com.kreinto.chefico.room.viewmodels.AuthViewModel
import com.kreinto.chefico.ui.theme.PaddingExtraLarge
import com.kreinto.chefico.ui.theme.PaddingLarge

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

    val changesFailureMessage = stringResource(R.string.changes_failure)

    if (openConfirmDialog) {
      Dialog({ openConfirmDialog = false }) {
        Column {
          TextInput(
            placeholder = R.string.confirm_with_password
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
                  Toast.makeText(context, changesFailureMessage, Toast.LENGTH_SHORT).show()
                }
              }
            },
            contentPadding = ButtonDefaults.TextButtonContentPadding
          ) { Text(stringResource(R.string.edit_confirm), color = MaterialTheme.colorScheme.error) }
        }
      }
    }
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(top = padding.calculateTopPadding(), start = PaddingExtraLarge, end = PaddingExtraLarge),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      TextInput(
        init = username,
        label = R.string.username,
        isError = username.isBlank()
      ) {
        username = it
      }
      Spacer(modifier = Modifier.height(PaddingLarge))
      TextInput(
        init = email,
        label = R.string.email,
        isError = !email.isValidEmail()
      ) {
        email = it
      }
      Spacer(modifier = Modifier.height(PaddingLarge))
      TextInput(
        label = R.string.new_password,
        isPassword = true,
        isError = !newPassword.isValidPassword()
      ) {
        newPassword = it
      }
      Spacer(modifier = Modifier.height(PaddingLarge))
      TextInput(
        label = R.string.new_password,
        isPassword = true,
        isError = !repeatedNewPassword.isValidPassword() && newPassword != repeatedNewPassword
      ) {
        repeatedNewPassword = it
      }
      Spacer(modifier = Modifier.height(PaddingLarge))
      if (
        email.isValidEmail() && email != authViewModel.currentUser?.email ||
        username.isNotBlank() && username != authViewModel.currentUser?.displayName ||
        newPassword.isValidPassword() && newPassword == repeatedNewPassword
      ) {
        Button(
          onClick = { openConfirmDialog = true },
          contentPadding = ButtonDefaults.TextButtonContentPadding
        ) { Text(stringResource(R.string.edit_confirm), color = MaterialTheme.colorScheme.error) }
      }
    }
  }
}
