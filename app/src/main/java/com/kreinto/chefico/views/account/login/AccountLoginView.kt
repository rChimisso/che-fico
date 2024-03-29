package com.kreinto.chefico.views.account.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.kreinto.chefico.components.misc.Loader
import com.kreinto.chefico.isValidEmail
import com.kreinto.chefico.isValidPassword
import com.kreinto.chefico.room.viewmodels.AuthViewModel
import com.kreinto.chefico.ui.theme.*

@ExperimentalMaterial3Api
@Composable
fun AccountLoginView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  SimpleFrame(onNavigate) {
    val loading = remember { mutableStateOf(false) }
    val context = LocalContext.current
    var email: String by rememberSaveable { mutableStateOf("") }
    var password: String by rememberSaveable { mutableStateOf("") }

    Column(
      verticalArrangement = Arrangement.Bottom,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .padding(bottom = PaddingExtraLarge)
        .fillMaxSize()
    ) {
      Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.weight(1f)
      ) {
        Image(
          painterResource(R.drawable.che_fico_icon),
          null,
          Modifier
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
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(PaddingExtraLarge),
          modifier = Modifier
            .fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(PaddingExtraLarge)
          ) {
            TextInput(
              label = R.string.email,
              leadingIcon = {
                Icon(painterResource(R.drawable.ic_email), null, Modifier.size(IconSizeMedium))
              }
            ) {
              email = it
            }
            TextInput(
              isPassword = true,
              label = R.string.password,
              leadingIcon = {
                Icon(painterResource(R.drawable.ic_lock), null, Modifier.size(IconSizeMedium))
              }
            ) {
              password = it
            }
          }
          Column(
            verticalArrangement = Arrangement.spacedBy(PaddingExtraLarge)
          ) {
            SubmitButton(
              text = R.string.login,
              enabled = email.isValidEmail() && password.isValidPassword()
            ) {
              loading.value = true
              authViewModel.signIn(
                email,
                password
              ) {
                loading.value = false
                if (it) {
                  onNavigate(CheFicoRoute.Back.path)
                } else {
                  Toast.makeText(context, R.string.access_error, Toast.LENGTH_SHORT).show()
                }
              }
            }
            SubmitButton(
              text = R.string.signup,
              textOnly = true
            ) {
              onNavigate(CheFicoRoute.Signin.path)
            }
          }
        }
      }
    }
    Loader(loading.value)
  }
}
