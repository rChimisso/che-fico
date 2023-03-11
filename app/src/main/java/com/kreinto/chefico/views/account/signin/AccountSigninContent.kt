package com.kreinto.chefico.views.account.signin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
internal fun AccountSigninContent(it: PaddingValues) {
  Column(
    modifier = Modifier.padding(paddingValues = it),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    AccountSigninField("Nome") {}
    AccountSigninField("Cognome") {}
    AccountSigninField("Email") {}
    AccountSigninField("Password", "La password deve contenere almeno un numero e un carattere speciale") {}
    AccountSigninField("Ripeti Password") {}

  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountSigninField(fieldName: String, hint: String = "", onValueChange: (String) -> Unit) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(fieldName, color = Color(0xff32C896))
    Spacer(modifier = Modifier.width(16.dp))
    TextField(
      value = TextFieldValue(""),
      onValueChange = { onValueChange(it.text) },
      singleLine = true,
      colors = TextFieldDefaults.textFieldColors(
        textColor = Color(0xff32C896),
        containerColor = Color.Transparent,
        cursorColor = Color(0xff32C896),
        placeholderColor = Color(0xff32C896),
        focusedIndicatorColor = Color(0xff32C896),
        focusedLeadingIconColor = Color(0xff32C896),
        unfocusedLeadingIconColor = Color(0xff32C896)
      ),
      supportingText = { Text(text = hint) }
    )
  }
}