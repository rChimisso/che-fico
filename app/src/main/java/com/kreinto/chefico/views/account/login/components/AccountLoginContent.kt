package com.kreinto.chefico.views.account.login.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.misc.Loader
import com.kreinto.chefico.room.AuthViewModel

@Composable
internal fun AccountLoginContent(authViewModel: AuthViewModel, paddingValues: PaddingValues, onNavigate: (String) -> Unit) {
  val loading = remember { mutableStateOf(false) }

  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxSize()
      .padding(paddingValues)
  ) {
    Image(painterResource(R.drawable.che_fico_icon), null, Modifier.size(156.dp))
    Spacer(Modifier.height(64.dp))
    GoogleLogInButton({ onNavigate(CheFicoRoute.Account.path) })
    Spacer(Modifier.height(40.dp))
    Row(
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Divider(color = Color(0x6632C896), modifier = Modifier.width(128.dp))
      Spacer(Modifier.width(8.dp))
      Text(stringResource(R.string.login_methods_divider_label), fontSize = 16.sp, color = Color(0xff32C896))
      Spacer(Modifier.width(8.dp))
      Divider(color = Color(0x6632C896), modifier = Modifier.width(128.dp))
    }
    Spacer(Modifier.height(32.dp))
    var email: String by rememberSaveable { mutableStateOf("") }
    var password: String by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    TextField(
      label = { Text(stringResource(R.string.email_label)) },
      value = email,
      onValueChange = { email = it },
      singleLine = true,
//      colors = TextFieldDefaults.colors(
//        unfocusedTextColor = Color(0xff32C896),
//        focusedContainerColor = Color.Transparent,
//        unfocusedContainerColor = Color.Transparent,
//        disabledContainerColor = Color.Transparent,
//        cursorColor = Color(0xff32C896),
//        focusedIndicatorColor = Color(0x6632C896),
//        unfocusedIndicatorColor = Color(0x6632C896),
//        disabledIndicatorColor = Color(0x6632C896),
//        focusedLeadingIconColor = Color(0xff32C896),
//        unfocusedLeadingIconColor = Color(0xff32C896),
//        focusedLabelColor = Color.Transparent,
//        unfocusedLabelColor = Color(0xff32C896),
//        disabledLabelColor = Color(0xff32C896),
//        unfocusedPlaceholderColor = Color(0xff32C896),
//      ),
      leadingIcon = {
        Icon(painterResource(R.drawable.ic_email), "email", Modifier.size(24.dp))
      }
    )
    Spacer(Modifier.height(16.dp))
    TextField(
      label = { Text("Password") },
      value = password,
      onValueChange = { password = it },
      singleLine = true,
//      colors = TextFieldDefaults.colors(
//        unfocusedTextColor = Color(0xff32C896),
//        focusedContainerColor = Color.Transparent,
//        unfocusedContainerColor = Color.Transparent,
//        disabledContainerColor = Color.Transparent,
//        cursorColor = Color(0xff32C896),
//        focusedIndicatorColor = Color(0x6632C896),
//        unfocusedIndicatorColor = Color(0x6632C896),
//        disabledIndicatorColor = Color(0x6632C896),
//        focusedLeadingIconColor = Color(0xff32C896),
//        unfocusedLeadingIconColor = Color(0xff32C896),
//        focusedTrailingIconColor = Color(0xff32C896),
//        unfocusedTrailingIconColor = Color(0x6632C896),
//        focusedLabelColor = Color.Transparent,
//        unfocusedLabelColor = Color(0xff32C896),
//        disabledLabelColor = Color(0xff32C896),
//        unfocusedPlaceholderColor = Color(0xff32C896),
//      ),
      trailingIcon = {
        IconButton(onClick = { passwordVisible = !passwordVisible }) {
          Crossfade(targetState = passwordVisible, animationSpec = tween(300)) { visible ->
            if (visible) {
              Icon(
                painter = painterResource(id = R.drawable.ic_visible),
                contentDescription = "Hide password",
                modifier = Modifier.size(24.dp)
              )
            } else {
              Icon(
                painter = painterResource(id = R.drawable.ic_hidden),
                contentDescription = "Show password",
                modifier = Modifier.size(24.dp)
              )
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
    Spacer(Modifier.height(32.dp))
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
      Text(stringResource(R.string.pwd_forgot_label))
      Spacer(Modifier.width(8.dp))
      Text(stringResource(R.string.pwd_recover_label), Modifier.clickable {})
    }
    Spacer(Modifier.height(32.dp))
    TextButton(
//      colors = ButtonDefaults.buttonColors(
//        containerColor = Color.Transparent,
//        contentColor = Color.Black
//      ),
      contentPadding = PaddingValues(0.dp),
      shape = RoundedCornerShape(12.dp),
      modifier = Modifier
        .width(208.dp)
        .height(40.dp),
      onClick = {
        loading.value = true
        authViewModel.signIn(
          email,
          password,
          {
            loading.value = false
            if (it == null) {
              email = ""
              password = ""
              onNavigate(CheFicoRoute.Account.path)
            }
          },
          {}
        )
      },
      enabled = email.isNotBlank() && password.isNotBlank()
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .clip(RoundedCornerShape(12.dp))
          .background(Brush.verticalGradient(listOf(Color(0xff32C896), Color(0x6632C896))))
      ) { Text(stringResource(R.string.login_label), fontSize = 16.sp, modifier = Modifier.align(Alignment.Center)) }
    }
    Spacer(Modifier.height(16.dp))
    TextButton(
//      colors = ButtonDefaults.buttonColors(
//        containerColor = Color.Transparent,
//        contentColor = Color(0xff32C896)
//      ),
      contentPadding = PaddingValues(0.dp),
      shape = RoundedCornerShape(12.dp),
      modifier = Modifier
        .width(208.dp)
        .height(40.dp),
      onClick = { onNavigate(CheFicoRoute.Signin.path) },
    ) { Text(stringResource(R.string.signup_label), fontSize = 16.sp) }
    Spacer(Modifier.height(8.dp))
  }
  Loader(loading.value)
}

