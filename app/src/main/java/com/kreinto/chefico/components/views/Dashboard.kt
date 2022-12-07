package com.kreinto.chefico.components.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.components.*

@Composable
fun DashboardButton() {
  IconButton(onClick = { /*TODO*/ }) {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Icon(
        contentDescription = "Settings",
        imageVector = Icons.Default.Settings,
        modifier = Modifier.size(24.dp)
      )
      Text("Settings", fontSize = 12.sp)
    }

  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardView() {
  AppScaffold(
    topBar = {
      AppTopBar(title = {
        Text("Che Fico!", fontSize = 24.sp)
      },
        navigationIcon = {
          Icon(
            contentDescription = "Settings",
            imageVector = Icons.Default.AccountCircle
          )
        },
        actionButton = {
          IconButton(onClick = {}) {
            Icon(
              contentDescription = "Settings",
              imageVector = Icons.Default.Settings
            )
          }
        })
    },
    content = {
      LazyColumn(
        modifier = Modifier
          .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
      ) {
        val notificationData = listOf(
          NotificationData(12, "message"),
          NotificationData(12, "message"),
          NotificationData(12, "message"),
          NotificationData(12, "message"),
          NotificationData(12, "message"),
        )
        items(notificationData.size) { index ->
          Notification(notificationData[index])
        }
      }
    },
    bottomBar = {
      AppBottomBar {
        DashboardButton()
        DashboardButton()
        DashboardButton()
        DashboardButton()
      }
    }
  )
}

/*
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
 */