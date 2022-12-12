package com.kreinto.chefico.views.poidetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalMaterial3Api
@Composable
fun PoiDetailDescription() {
  Surface(
    shadowElevation = 12.dp,
    modifier = Modifier.padding(16.dp),
    shape = RoundedCornerShape(10.dp)
  ) {
    Column {
      var value by remember { mutableStateOf("Nota dell'utente, magari con indicazioni o altro. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut...") }
      TextField(
        modifier = Modifier
          .fillMaxWidth()
          .requiredHeight(128.dp),
        textStyle = TextStyle(
          fontSize = 18.sp
        ),
        colors = TextFieldDefaults.textFieldColors(
          textColor = Color.Black,
          cursorColor = Color.Black,
          containerColor = Color.Transparent,
          focusedIndicatorColor = Color.Transparent,
          unfocusedIndicatorColor = Color.Transparent,
          disabledIndicatorColor = Color.Transparent
        ),
        value = value,
        onValueChange = { value = it }
      )

      Divider(modifier = Modifier.height(2.dp))

      Row(
        modifier = Modifier.height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Spacer(modifier = Modifier.width(16.dp))
        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "")
        Spacer(modifier = Modifier.width(32.dp))

        Text("Questa painta che fico")
      }
    }
  }
}
