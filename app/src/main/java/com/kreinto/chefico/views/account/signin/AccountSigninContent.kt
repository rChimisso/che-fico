package com.kreinto.chefico.views.account

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.R
import com.kreinto.chefico.Route
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.views.account.signin.GoogleSignInButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountSignInContent(authViewModel: AuthViewModel, paddingValues: PaddingValues, onNavigate: (String) -> Unit) {
  var email by rememberSaveable { mutableStateOf("") }
  var password by rememberSaveable { mutableStateOf("") }
  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  var repeatedPassword by rememberSaveable { mutableStateOf("") }
  var displayName by rememberSaveable { mutableStateOf("") }

  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxSize()
      .padding(paddingValues)
  ) {
    Image(painter = painterResource(id = R.drawable.che_fico_icon), contentDescription = "", Modifier.size(156.dp))
    Spacer(modifier = Modifier.height(64.dp))
    GoogleSignInButton(
      onSuccess = {
        onNavigate(Route.Account.path)
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
    TextField(
      label = { Text("Nome") },
      value = displayName,
      onValueChange = {
        displayName = it
      },
      singleLine = true,
      colors = TextFieldDefaults.textFieldColors(
        textColor = Color(0xff32C896),
        containerColor = Color.Transparent,
        cursorColor = Color(0xff32C896),
        placeholderColor = Color(0xff32C896),
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
          painter = painterResource(id = R.drawable.ic_account),
          contentDescription = "displayName",
          modifier = Modifier.size(24.dp)
        )
      }
    )
    TextField(
      label = { Text("Email") },
      value = email,
      onValueChange = {
        email = it
      },
      singleLine = true,
      colors = TextFieldDefaults.textFieldColors(
        textColor = Color(0xff32C896),
        containerColor = Color.Transparent,
        cursorColor = Color(0xff32C896),
        placeholderColor = Color(0xff32C896),
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
    TextField(
      label = { Text("Password") },
      value = password,
      onValueChange = {
        password = it
      },
      singleLine = true,
      colors = TextFieldDefaults.textFieldColors(
        textColor = Color(0xff32C896),
        containerColor = Color.Transparent,
        cursorColor = Color(0xff32C896),
        placeholderColor = Color(0xff32C896),
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
      leadingIcon = {
        Icon(
          painter = painterResource(id = R.drawable.ic_lock),
          contentDescription = "email",
          modifier = Modifier.size(24.dp)
        )
      }
    )
    TextField(
      label = { Text("Ripeti Password") },
      value = password,
      onValueChange = {
        password = it
      },
      singleLine = true,
      colors = TextFieldDefaults.textFieldColors(
        textColor = Color(0xff32C896),
        containerColor = Color.Transparent,
        cursorColor = Color(0xff32C896),
        placeholderColor = Color(0xff32C896),
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

        }
      ) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.verticalGradient(listOf(Color(0xff32C896), Color(0x6632C896))))
        ) {
          Text("Registrati", fontSize = 16.sp, modifier = Modifier.align(Alignment.Center))
        }
      }
    }
  }
}

