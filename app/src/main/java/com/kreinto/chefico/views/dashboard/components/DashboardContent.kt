package com.kreinto.chefico.views.dashboard.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.items.SwipeableItem

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DashboardContent() {
  Column(
    modifier = Modifier
      .fillMaxHeight()
      .padding(16.dp)
  ) {
    SwipeableItem(
      icon = R.drawable.ic_poi,
      text = stringResource(R.string.notification_label),
      containerColor = Color(0xff20211e),
      contentColor = Color(0xff32C896),
      actions = arrayOf(
        {
          TransparentButton(
            icon = R.drawable.ic_snooze,
            contentDescription = "Snooze",
            colors = IconButtonDefaults.iconButtonColors(
              contentColor = Color(0xFFFFC107)
            )
          ) {}
        },
        {
          TransparentButton(
            icon = R.drawable.ic_trash,
            contentDescription = "Delete",
            colors = IconButtonDefaults.iconButtonColors(
              contentColor = Color(0xffc5312a)
            )
          ) {}
        }
      )
    ) {}
  }
}

