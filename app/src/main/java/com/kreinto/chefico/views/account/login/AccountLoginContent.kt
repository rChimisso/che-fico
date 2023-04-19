package com.kreinto.chefico.views.account.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.views.account.signin.GoogleLogInButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountLoginContent(authViewModel: AuthViewModel, paddingValues: PaddingValues, onNavigate: (String) -> Unit) {

  var coroutine = rememberCoroutineScope()
  var loading = remember {
    mutableStateOf(false)
  }
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxSize()
      .padding(paddingValues)
  ) {
    Image(painter = painterResource(id = R.drawable.che_fico_icon), contentDescription = "", Modifier.size(156.dp))
    Spacer(modifier = Modifier.height(64.dp))
    GoogleLogInButton(
      onSuccess = {
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
      Spacer(modifier = Modifier.width(8.dp))
      Text("oppure", fontSize = 16.sp, color = Color(0xff32C896))
      Spacer(modifier = Modifier.width(8.dp))
      Divider(color = Color(0x6632C896), modifier = Modifier.width(128.dp))
    }
    Spacer(modifier = Modifier.height(32.dp))
    var email: String by rememberSaveable { mutableStateOf("") }
    var password: String by rememberSaveable { mutableStateOf("") }

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    TextField(
      label = { Text("Email") },
      value = email,
      onValueChange = {
        email = it
      },
      singleLine = true,
      colors = TextFieldDefaults.textFieldColors(
        unfocusedTextColor = Color(0xff32C896),
        containerColor = Color.Transparent,
        cursorColor = Color(0xff32C896),
        unfocusedPlaceholderColor = Color(0xff32C896),
        focusedIndicatorColor = Color(0x6632C896),
        disabledIndicatorColor = Color(0x6632C896),
        unfocusedIndicatorColor = Color(0x6632C896),
        focusedLeadingIconColor = Color(0xff32C896),
        disabledLabelColor = Color(0xff32C896),
        unfocusedLeadingIconColor = Color(0xff32C896),
        focusedLabelColor = Color.Transparent,
        unfocusedLabelColor = Color(0xff32C896),

        ),
      leadingIcon = {
        Icon(
          painter = painterResource(id = R.drawable.ic_email),
          contentDescription = "email",
          modifier = Modifier.size(24.dp)
        )
      }
    )
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
      label = { Text("Email") },
      value = password,
      onValueChange = {
        password = it
      },
      singleLine = true,
      colors = TextFieldDefaults.textFieldColors(
        unfocusedTextColor = Color(0xff32C896),
        containerColor = Color.Transparent,
        cursorColor = Color(0xff32C896),
        unfocusedPlaceholderColor = Color(0xff32C896),
        focusedIndicatorColor = Color(0x6632C896),
        disabledIndicatorColor = Color(0x6632C896),
        unfocusedIndicatorColor = Color(0x6632C896),
        focusedLeadingIconColor = Color(0xff32C896),
        disabledLabelColor = Color(0xff32C896),
        unfocusedLeadingIconColor = Color(0xff32C896),
        focusedLabelColor = Color.Transparent,
        unfocusedLabelColor = Color(0xff32C896),
        focusedTrailingIconColor = Color(0xff32C896),
        unfocusedTrailingIconColor = Color(0x6632C896)

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
      Text("Password dimenticata?", fontSize = 16.sp, color = Color(0xff32C896))
      Spacer(modifier = Modifier.width(8.dp))
      ClickableText(
        AnnotatedString("Recupera password"),
        style = TextStyle(
          color = Color(0xff32C896),
          fontSize = 16.sp,


          )
      ) {}

    }
    Spacer(modifier = Modifier.height(32.dp))
    TextButton(
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
        loading.value = true
        authViewModel.logIn(email, password) {
          loading.value = false
          if (it == null) {
            email = ""
            password = ""
            onNavigate(CheFicoRoute.Account.path)
          } else {

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
        Text("Accedi", fontSize = 16.sp, modifier = Modifier.align(Alignment.Center))
      }
    }
    Spacer(modifier = Modifier.height(16.dp))
    TextButton(
      colors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = Color(0xff32C896)
      ),
      contentPadding = PaddingValues(0.dp),
      shape = RoundedCornerShape(12.dp),
      modifier = Modifier
        .width(208.dp)
        .height(40.dp),
      onClick = { onNavigate(CheFicoRoute.Signin.path) },
    ) {
      Text("Registrati", fontSize = 16.sp)
    }
    Spacer(modifier = Modifier.height(8.dp))
  }
  AnimatedVisibility(
    modifier = Modifier.fillMaxSize(),
    visible = loading.value,
    enter = EnterTransition.None,
    exit = fadeOut()
  ) {
    CircularProgressIndicator(
      modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .wrapContentSize()
    )
  }
}

