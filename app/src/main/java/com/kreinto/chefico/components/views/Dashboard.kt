package com.kreinto.chefico.components.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.components.NotificationData
import com.kreinto.chefico.components.NotificationsList
import com.kreinto.chefico.components.TopBar
import com.kreinto.chefico.components.buttons.RoundButton


@Composable
fun DashboardView() {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    TopBar {
      Box(
        modifier = Modifier.padding(10.dp)
      ) {
        Icon(
          modifier = Modifier
            .size(32.dp)
            .align(Alignment.CenterStart),
          imageVector = Icons.Default.AccountCircle,
          contentDescription = ""
        )
        Row(
          modifier = Modifier.align(Alignment.Center),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Home,
            contentDescription = ""
          )
          Text("Che Fico", fontSize = 24.sp)
        }

        RoundButton(
          modifier = Modifier.align(Alignment.CenterEnd),
          icon = Icons.Default.Settings,
        ) {

        }
      }
    }
    NotificationsList(
      modifier = Modifier.height(500.dp),
      notificationData = listOf(
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),

        NotificationData(12, "message"),
        NotificationData(12, "message"),
        NotificationData(12, "message"),
      )
    )
    NavigationFloatingMenu()
  }
}
