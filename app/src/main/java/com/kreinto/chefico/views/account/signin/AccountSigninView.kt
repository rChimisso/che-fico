package com.kreinto.chefico.views.account.signin

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.GoogleAccessButton
import com.kreinto.chefico.components.buttons.SubmitButton
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.inputs.TextInput
import com.kreinto.chefico.isValidEmail
import com.kreinto.chefico.isValidPassword
import com.kreinto.chefico.room.viewmodels.AuthViewModel
import com.kreinto.chefico.ui.theme.*

@ExperimentalMaterial3Api
@Composable
fun AccountSigninView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  val context = LocalContext.current
  var email by rememberSaveable { mutableStateOf("") }
  var password by rememberSaveable { mutableStateOf("") }
  var repeatedPassword by rememberSaveable { mutableStateOf("") }
  var displayName by rememberSaveable { mutableStateOf("") }

  var isEmailFieldClicked by rememberSaveable { mutableStateOf(false) }
  var isPasswordFieldClicked by rememberSaveable { mutableStateOf(false) }
  var isRepeatedPasswordFieldClicked by rememberSaveable { mutableStateOf(false) }
  var isDisplaNameFieldClicked by rememberSaveable { mutableStateOf(false) }

  SimpleFrame(onNavigate) {
    Column(
      verticalArrangement = Arrangement.Bottom,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .padding(bottom = PaddingExtraLarge)
        .fillMaxSize()
    ) {
      Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
          painterResource(R.drawable.che_fico_icon),
          null,
          Modifier
            .padding(CheFicoIconPadding)
            .size(CheFicoIconSize)
        )
      }
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
      ) {
        GoogleAccessButton {
          authViewModel.initGoogleAccount(it)
          onNavigate(CheFicoRoute.Account.path)
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
          Divider(color = MaterialTheme.colorScheme.primary, modifier = Modifier.width(CheFicoIconSize))
          Spacer(Modifier.width(PaddingMedium))
          Text(stringResource(R.string.or), fontSize = LabelLarge, color = MaterialTheme.colorScheme.primary)
          Spacer(Modifier.width(PaddingMedium))
          Divider(color = MaterialTheme.colorScheme.primary, modifier = Modifier.width(CheFicoIconSize))
        }
        Column(Modifier.padding(PaddingExtraLarge)) {
          TextInput(
            label = R.string.username,
            onFocusChanged = {
              if (it.hasFocus) {
                isDisplaNameFieldClicked = true
              }
            },
            onValueChange = { displayName = it },
            isError = isDisplaNameFieldClicked && displayName.isBlank(),
            leadingIcon = {
              Icon(
                painter = painterResource(id = R.drawable.ic_account),
                null,
                modifier = Modifier.size(IconSizeMedium)
              )
            }
          )
          TextInput(
            label = R.string.email,
            onFocusChanged = {
              if (it.hasFocus) {
                isEmailFieldClicked = true
              }
            },
            onValueChange = { email = it },
            isError = isEmailFieldClicked && !email.isValidEmail(),
            leadingIcon = {
              Icon(
                painter = painterResource(id = R.drawable.ic_email),
                null,
                modifier = Modifier.size(IconSizeMedium)
              )
            }
          )
          TextInput(
            label = R.string.password,
            onFocusChanged = {
              if (it.hasFocus) {
                isPasswordFieldClicked = true
              }
            },
            onValueChange = { password = it },
            isError = isPasswordFieldClicked && !password.isValidPassword(),
            supportingText = R.string.min_password_len,
            leadingIcon = {
              Icon(
                painter = painterResource(id = R.drawable.ic_lock),
                null,
                modifier = Modifier.size(IconSizeMedium)
              )
            }
          )
          TextInput(
            label = R.string.password,
            onFocusChanged = {
              if (it.hasFocus) {
                isRepeatedPasswordFieldClicked = true
              }
            },
            onValueChange = { repeatedPassword = it },
            isError = isRepeatedPasswordFieldClicked && !repeatedPassword.isValidPassword() && repeatedPassword != password,
            supportingText = R.string.passwords_equal,
            leadingIcon = {
              Icon(
                painter = painterResource(id = R.drawable.ic_lock),
                null,
                modifier = Modifier.size(IconSizeMedium)
              )
            }
          )
        }
        SubmitButton(
          text = R.string.signup,
          enabled = displayName.isNotBlank() && password == repeatedPassword && password.isValidPassword()
        ) {
          authViewModel.createUser(email, password, displayName) {
            if (it) {
              onNavigate(CheFicoRoute.Account.path)
            } else {
              Toast.makeText(context, R.string.access_error, Toast.LENGTH_SHORT).show()
            }
          }
        }
      }
    }
  }
}


