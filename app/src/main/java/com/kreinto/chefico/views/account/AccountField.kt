package com.kreinto.chefico.views.account

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountField(@DrawableRes leadingIcon: Int, fieldName: String, onValueChange: (String) -> Unit) {
  var value: String by rememberSaveable { mutableStateOf("") }

  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  TextField(
    label = { Text(fieldName) },
    value = value,
    onValueChange = {
      value = it
      onValueChange(value)
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
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
    leadingIcon = {
      Icon(
        painter = painterResource(id = leadingIcon),
        contentDescription = fieldName,
        modifier = Modifier.size(24.dp)
      )
    }
  )
}