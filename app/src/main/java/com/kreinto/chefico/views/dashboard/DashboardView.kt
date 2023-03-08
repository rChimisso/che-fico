package com.kreinto.chefico.views.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.components.items.SwipeableItem

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun DashboardView(onNavigate: (String) -> Unit) {
  StandardFrame(
    title = { Text(
      text = "Che fico!",
      fontSize = 24.sp,
      color = MaterialTheme.colorScheme.onSurface
    ) },
    isDashboard = true,
    onNavPressed = onNavigate,
    bottomBar = { DashboardMenu(onNavigate = onNavigate) }
  ) {
    Spacer(modifier = Modifier.height(16.dp))
    for (i in 0..20) {
      SwipeableItem(
        icon = Icons.Default.Star,
        text = "Notification Name $i",
        actions = arrayOf(
          {
            TransparentButton(
              icon = R.drawable.ic_snooze,
              contentDescription = "Snooze"
            ) {}
          },
          {
            TransparentButton(
              icon = R.drawable.ic_trash,
              contentDescription = "Delete"
            ) {}
          }
        )
      ) {}
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
@Preview
private fun DashboardViewPreview() {
  DashboardView {}
}
