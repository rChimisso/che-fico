package com.kreinto.chefico.views.account.signin.components

import android.util.Patterns
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.room.viewmodels.AuthViewModel

fun String.isValidEmail(): Boolean {
  return isNotEmpty() && matches(Patterns.EMAIL_ADDRESS.toRegex())
}

@Composable
internal fun AccountSignInContent(authViewModel: AuthViewModel, paddingValues: PaddingValues, onNavigate: (String) -> Unit) {
  val context = LocalContext.current

  var email by rememberSaveable { mutableStateOf("") }
  var password by rememberSaveable { mutableStateOf("") }
  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  var repeatedPassword by rememberSaveable { mutableStateOf("") }
  var displayName by rememberSaveable { mutableStateOf("") }

  var isEmailFieldClicked by rememberSaveable { mutableStateOf(false) }
  var isPasswordFieldClicked by rememberSaveable { mutableStateOf(false) }
  var isRepeatedPasswordFieldClicked by rememberSaveable { mutableStateOf(false) }
  var isDisplaNameFieldClicked by rememberSaveable { mutableStateOf(false) }

  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxSize()
      .padding(paddingValues)
  ) {
    Image(painterResource(R.drawable.che_fico_icon), null, Modifier.size(128.dp))
    Spacer(modifier = Modifier.height(64.dp))
    GoogleSignInButton(
      onSuccess = {
        authViewModel.initGoogleAccount()
        onNavigate(CheFicoRoute.Account.path)
      },
      onFailure = {}
    )
    Spacer(modifier = Modifier.height(40.dp))
    Row(
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Divider(color = Color(0x6632C896), modifier = Modifier.width(128.dp))
      Spacer(Modifier.width(8.dp))
      Text(stringResource(R.string.signup_methods_divider_label), fontSize = 16.sp, color = Color(0xff32C896))
      Spacer(Modifier.width(8.dp))
      Divider(color = Color(0x6632C896), modifier = Modifier.width(128.dp))
    }
    Spacer(modifier = Modifier.height(32.dp))
    TextField(
      modifier = Modifier.onFocusChanged {
        if (it.hasFocus) {
          isDisplaNameFieldClicked = true
        }
      },
      label = { Text(stringResource(R.string.name_label)) },
      value = displayName,
      onValueChange = { displayName = it },
      isError = isDisplaNameFieldClicked && displayName.isEmpty(),
      singleLine = true,
      colors = TextFieldDefaults.colors(
        errorContainerColor = Color.Transparent,
        unfocusedTextColor = Color(0xff32C896),
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        cursorColor = Color(0xff32C896),
        focusedIndicatorColor = Color(0x6632C896),
        unfocusedIndicatorColor = Color(0x6632C896),
        disabledIndicatorColor = Color(0x6632C896),
        focusedLeadingIconColor = Color(0xff32C896),
        unfocusedLeadingIconColor = Color(0xff32C896),
        focusedLabelColor = Color.Transparent,
        unfocusedLabelColor = Color(0xff32C896),
        disabledLabelColor = Color(0xff32C896),
        unfocusedPlaceholderColor = Color(0xff32C896),
      ),
      leadingIcon = {
        Icon(
          painter = painterResource(id = R.drawable.ic_account),
          contentDescription = "displayName",
          modifier = Modifier.size(24.dp)
        )
      }
    )
    TextField(
      modifier = Modifier.onFocusChanged {
        if (it.hasFocus) {
          isEmailFieldClicked = true
        }
      },
      label = { Text(text = stringResource(R.string.email_label)) },
      value = email,
      onValueChange = { email = it },
      isError = isEmailFieldClicked && !email.isValidEmail(),
      singleLine = true,
      colors = TextFieldDefaults.colors(
        errorContainerColor = Color.Transparent,
        unfocusedTextColor = Color(0xff32C896),
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        cursorColor = Color(0xff32C896),
        focusedIndicatorColor = Color(0x6632C896),
        unfocusedIndicatorColor = Color(0x6632C896),
        disabledIndicatorColor = Color(0x6632C896),
        focusedLeadingIconColor = Color(0xff32C896),
        unfocusedLeadingIconColor = Color(0xff32C896),
        focusedLabelColor = Color.Transparent,
        unfocusedLabelColor = Color(0xff32C896),
        disabledLabelColor = Color(0xff32C896),
        unfocusedPlaceholderColor = Color(0xff32C896),
      ),
      leadingIcon = {
        Icon(
          painter = painterResource(id = R.drawable.ic_email),
          contentDescription = "email",
          modifier = Modifier.size(24.dp)
        )
      }
    )
    TextField(
      modifier = Modifier.onFocusChanged {
        if (it.hasFocus) {
          isPasswordFieldClicked = true
        }
      },
      label = { Text(text = stringResource(R.string.pwd_label)) },
      value = password,
      onValueChange = { password = it },
      supportingText = { Text("Lunghezza minima di 7 caratteri") },
      isError = isPasswordFieldClicked && (password.isEmpty() || password.length <= 6),
      singleLine = true,
      colors = TextFieldDefaults.colors(
        errorContainerColor = Color.Transparent,
        unfocusedTextColor = Color(0xff32C896),
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        cursorColor = Color(0xff32C896),
        focusedIndicatorColor = Color(0x6632C896),
        unfocusedIndicatorColor = Color(0x6632C896),
        disabledIndicatorColor = Color(0x6632C896),
        focusedLeadingIconColor = Color(0xff32C896),
        unfocusedLeadingIconColor = Color(0xff32C896),
        focusedTrailingIconColor = Color(0xff32C896),
        unfocusedTrailingIconColor = Color(0x6632C896),
        focusedLabelColor = Color.Transparent,
        unfocusedLabelColor = Color(0xff32C896),
        disabledLabelColor = Color(0xff32C896),
        unfocusedPlaceholderColor = Color(0xff32C896),
      ),
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
      visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
      trailingIcon = {
        IconButton(onClick = { passwordVisible = !passwordVisible }) {
          Crossfade(targetState = passwordVisible, animationSpec = tween(300)) { visible ->
            if (visible) {
              Icon(painter = painterResource(id = R.drawable.ic_visible), contentDescription = "", modifier = Modifier.size(24.dp))
            } else {
              Icon(painter = painterResource(id = R.drawable.ic_hidden), contentDescription = "", modifier = Modifier.size(24.dp))
            }
          }
        }
      },
      leadingIcon = {
        Icon(
          painter = painterResource(id = R.drawable.ic_lock),
          contentDescription = "email",
          modifier = Modifier.size(24.dp)
        )
      }
    )
    TextField(
      modifier = Modifier.onFocusChanged {
        if (it.hasFocus) {
          isRepeatedPasswordFieldClicked = true
        }
      },
      label = { Text(text = stringResource(R.string.pwd_repeat_label)) },
      value = repeatedPassword,
      supportingText = { Text("Le password devono coincidere") },
      onValueChange = { repeatedPassword = it },
      isError = isRepeatedPasswordFieldClicked && (password != repeatedPassword || repeatedPassword.isEmpty()),
      singleLine = true,
      colors = TextFieldDefaults.colors(
        errorContainerColor = Color.Transparent,
        unfocusedTextColor = Color(0xff32C896),
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        cursorColor = Color(0xff32C896),
        focusedIndicatorColor = Color(0x6632C896),
        unfocusedIndicatorColor = Color(0x6632C896),
        disabledIndicatorColor = Color(0x6632C896),
        focusedLeadingIconColor = Color(0xff32C896),
        unfocusedLeadingIconColor = Color(0xff32C896),
        focusedTrailingIconColor = Color(0xff32C896),
        unfocusedTrailingIconColor = Color(0x6632C896),
        focusedLabelColor = Color.Transparent,
        unfocusedLabelColor = Color(0xff32C896),
        disabledLabelColor = Color(0xff32C896),
        unfocusedPlaceholderColor = Color(0xff32C896),
      ),
      trailingIcon = {
        IconButton(onClick = { passwordVisible = !passwordVisible }) {
          Crossfade(targetState = passwordVisible, animationSpec = tween(300)) { visible ->
            if (visible) {
              Icon(painter = painterResource(id = R.drawable.ic_visible), contentDescription = "", modifier = Modifier.size(24.dp))
            } else {
              Icon(painter = painterResource(id = R.drawable.ic_hidden), contentDescription = "", modifier = Modifier.size(24.dp))
            }
          }
        }
      },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
      visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
      leadingIcon = {
        Icon(
          painter = painterResource(id = R.drawable.ic_lock),
          contentDescription = "email",
          modifier = Modifier.size(24.dp)
        )
      }
    )
    Spacer(modifier = Modifier.height(32.dp))
    Row(
      horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
    ) {
      TextButton(
        enabled = displayName.isNotEmpty() && password == repeatedPassword && password.length >= 6 && email.isValidEmail(),
        colors = ButtonDefaults.buttonColors(
          containerColor = Color.Transparent,
          contentColor = Color.Black
        ),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .width(208.dp)
          .height(40.dp),
        onClick = {
          authViewModel.createUser(email, password, displayName) {
            if (it) {
              onNavigate(CheFicoRoute.Account.path)
            } else {
              Toast.makeText(context, "Registrazione non andata a buon fine", Toast.LENGTH_SHORT).show()
            }
          }
        }
      ) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.verticalGradient(listOf(Color(0xff32C896), Color(0x6632C896))))
        ) {
          Text(text = stringResource(R.string.signup_label), fontSize = 16.sp, modifier = Modifier.align(Alignment.Center))
        }
      }
    }
  }
}
