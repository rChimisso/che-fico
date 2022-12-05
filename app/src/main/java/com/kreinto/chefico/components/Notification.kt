package com.kreinto.chefico.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Notification(
  message: String
) {
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .clickable {
        //TODO
      },
    elevation = 10.dp,
    shape = RoundedCornerShape(10.dp),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(10.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Home,
        contentDescription = "",
        modifier = Modifier.size(32.dp),
        tint = Color.DarkGray
      )
      Spacer(modifier = Modifier.width(10.dp))
      Divider(
        color = Color.DarkGray,
        modifier = Modifier
          .height(20.dp)
          .width(1.dp),
      )
      Spacer(Modifier.width(15.dp))
      Text(text = message, fontSize = 18.sp)
    }
  }
}