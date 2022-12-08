package com.kreinto.chefico.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

data class NotificationData(
  val id: Int,
  val text: String,
)

@ExperimentalMaterialApi
@Composable
fun Notification(notificationData: NotificationData) {
  val swipeableState = rememberSwipeableState(initialValue = 0)
  Swipeable(
    swipeableState = swipeableState,
    modifier = Modifier
      .fillMaxWidth()
      .height(56.dp),
    elevation = 12.dp,
    swipeOffset = 96.dp,
    shape = RoundedCornerShape(10.dp),
    actions = {
      val scale by animateFloatAsState(
        targetValue = if (swipeableState.offset.value == 0f) swipeableState.offset.value else 1f
      )

      Spacer(Modifier.width(16.dp))

      Icon(
        imageVector = Icons.Default.Warning,
        contentDescription = "Delete",
        tint = Color(0xff4caf50),
        modifier = Modifier
          .scale(scale)
          .size(24.dp)

      )
      Spacer(Modifier.width(16.dp))

      Icon(
        imageVector = Icons.Default.Delete,
        contentDescription = "Delete",
        tint = Color(0xfffa2e25),
        modifier = Modifier
          .scale(scale)
          .size(24.dp)
      )
      Spacer(Modifier.width(16.dp))

    },
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxSize()
        .offset {
          IntOffset(
            x = -swipeableState.offset.value.roundToInt(),
            y = 0
          )
        }

    ) {
      Spacer(Modifier.width(16.dp))
      Icon(
        imageVector = Icons.Default.Home,
        contentDescription = "",
        modifier = Modifier
          .size(24.dp),
        tint = Color(0xff4caf50)
      )
      Spacer(Modifier.width(32.dp))
      Text(
        text = notificationData.text,
        fontSize = 22.sp,
        modifier = Modifier
      )
    }
  }
}
