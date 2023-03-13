package com.kreinto.chefico.views.account.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.R

@Composable
fun KreintoSignInButton() {
  Button(
    onClick = {

    },
    modifier = Modifier
      .height(40.dp)
      .width(208.dp),
    shape = RoundedCornerShape(12.dp),
    colors = ButtonDefaults.buttonColors(
      containerColor = Color(0xffffffff),
      contentColor = Color(0xff000000)
    ),
    contentPadding = PaddingValues(
      start = 0.dp,
      top = 0.dp,
      bottom = 0.dp,
      end = 8.dp
    )
  ) {
    Image(
      painter = painterResource(id = R.drawable.che_fico_icon),
      contentDescription = "accedi con Kreinto",
      modifier = Modifier.size(28.dp)
    )
    Spacer(Modifier.width(8.dp))
    Text("Registrati con Kreinto", fontSize = 14.sp)
  }
}