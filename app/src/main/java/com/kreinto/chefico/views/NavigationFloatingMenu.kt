package com.kreinto.chefico.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Test() {
  Surface(
    elevation = 12.dp,
    shape = RoundedCornerShape(12.dp),
    modifier = Modifier
      .fillMaxWidth()
      .height(50.dp)
  ) {
    Row {
      Spacer(Modifier.width(15.dp))
      Icon(
        imageVector = Icons.Default.Home,
        contentDescription = "",
        modifier = Modifier
          .size(32.dp),
        tint = Color(0xff4caf50)
      )
      Spacer(Modifier.width(15.dp))
      Divider(
        color = Color.DarkGray,
        modifier = Modifier
          .height(30.dp)  //fill the max height
          .width(1.dp)
      )
      Spacer(Modifier.width(15.dp))
      Text(
        text = "ciao",
        fontSize = 18.sp,
        modifier = Modifier
      )
    }
  }
}


@Composable
fun NavigationFloatingMenu() {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.padding(12.dp)
  ) {
    Surface(
      elevation = 12.dp,
      modifier = Modifier
        .width(100.dp)
        .height(30.dp),
      shape = RoundedCornerShape(12.dp)
    ) {
      Icon(
        imageVector = Icons.Default.KeyboardArrowUp,
        contentDescription = "",
        tint = Color.DarkGray
      )
    }
    Spacer(modifier = Modifier.height(12.dp))
    Surface(
      elevation = 12.dp,
      modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
      shape = RoundedCornerShape(12.dp)
    ) {
      Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(12.dp)
      ) {
        Test()
        Test()
        Test()
      }
    }
  }
}