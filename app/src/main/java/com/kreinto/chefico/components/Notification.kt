package com.kreinto.chefico.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.components.buttons.SimpleButton

data class NotificationData(
  val id: Int,
  val text: String,
)

@ExperimentalMaterialApi
@Composable
fun Notification(notificationData: NotificationData) {
  com.kreinto.chefico.components.items.Swipeable(
    icon = Icons.Default.Home,
    text = notificationData.text,
    actions = arrayOf(
      { SimpleButton(icon = Icons.Default.Warning, contentDescriptor = "Snooze") {} },
      { SimpleButton(icon = Icons.Default.Delete, contentDescriptor = "Delete") {} }
    )
  )
}

@ExperimentalMaterialApi
@Composable
@Preview
private fun NotificationPreview() {
  Notification(notificationData = NotificationData(12, "Notification message"))
}
