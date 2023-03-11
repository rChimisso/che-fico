package com.kreinto.chefico.views.account

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.R

@Composable
internal fun AccountLoginContent(paddingValues: PaddingValues) {
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxSize()
      .padding(paddingValues)
  ) {
    Image(painter = painterResource(id = R.drawable.che_fico_icon), contentDescription = "", Modifier.size(64.dp))
    AccountLoginField(R.drawable.ic_account, "Password") {}
    Spacer(modifier = Modifier.height(16.dp))
    AccountLoginField(R.drawable.ic_lock, "Password") {}
    Spacer(modifier = Modifier.height(16.dp))
    TextButton(
      colors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = Color.Black
      ),
      contentPadding = PaddingValues(0.dp),
      shape = RoundedCornerShape(12.dp),
      modifier = Modifier
        .width(128.dp)
        .height(48.dp),
      onClick = { /*TODO*/ }
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
    Spacer(modifier = Modifier.height(8.dp))
    TextButton(
      colors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = Color(0xff32C896)
      ),
      contentPadding = PaddingValues(0.dp),
      shape = RoundedCornerShape(12.dp),
      onClick = { /*TODO*/ }
    ) {
      Text("Registrati", fontSize = 16.sp)
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "Accedendo si accettano i termini e condizioni d'uso dell'applicazione.")
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountLoginField(@DrawableRes leadingIcon: Int, fieldName: String, onValueChange: (String) -> Unit) {
  TextField(
    label = { Text(fieldName) },
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
      focusedLabelColor = Color(0xff32C896),
      disabledLabelColor = Color(0xff32C896)
    ),
    leadingIcon = {
      Icon(
        painter = painterResource(id = leadingIcon),
        contentDescription = fieldName,
        modifier = Modifier.size(24.dp)
      )
    }
  )
}