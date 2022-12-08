package com.kreinto.chefico.components.views.poidetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.components.Swipeable
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun PoiDetailEvent() {

  val swipeableState = rememberSwipeableState(initialValue = 0)
  val swipeoffset = 128.dp
  val elevation = 12.dp
  val shape = RoundedCornerShape(10.dp)
  val modifier = Modifier
    .height(56.dp)
    .fillMaxWidth()

  Swipeable(
    modifier = modifier,
    shape = shape,
    elevation = elevation,
    swipeableState = swipeableState,
    swipeOffset = swipeoffset,
    actions = {
      TextButton(
        onClick = {},
        colors = ButtonDefaults.textButtonColors(
          backgroundColor = Color(0xfffa2e25),
        )
      ) {
        Text(text = "Elimina", color = Color.White, fontWeight = FontWeight.Bold)
      }
      Spacer(Modifier.width(16.dp))
    }
  ) {
    Row(
      modifier = Modifier
        .offset {
          IntOffset(
            x = -swipeableState.offset.value.roundToInt(),
            y = 0
          )
        }
        .fillMaxSize(),
      verticalAlignment = Alignment.CenterVertically

    ) {

      Spacer(Modifier.width(16.dp))

      Icon(
        imageVector = Icons.Default.CheckCircle,
        tint = Color(0xff4caf50),
        contentDescription = ""
      )
      Spacer(Modifier.width(32.dp))

      Text("Evento 1")
    }
  }
}
