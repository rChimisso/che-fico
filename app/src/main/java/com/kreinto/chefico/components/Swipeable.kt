package com.kreinto.chefico.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun Swipeable(
  swipeableState: SwipeableState<Int>,
  modifier: Modifier = Modifier,
  swipeOffset: Dp,
  elevation: Dp = 12.dp,
  threshold: Float = 1.5f,
  shape: RoundedCornerShape = RoundedCornerShape(12.dp),
  actions: @Composable () -> Unit,
  content: @Composable () -> Unit,
) {
  val offsetPx = with(LocalDensity.current) { swipeOffset.toPx() }
  val anchors = mapOf(
    0f to 0,
    -offsetPx to 1
  )
  Surface(
    elevation = elevation,
    shape = shape,
    modifier = modifier
      .swipeable(
        state = swipeableState,
        anchors = anchors,
        thresholds = { _, _ -> FractionalThreshold(threshold) },
        orientation = Orientation.Horizontal
      )
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.End,
    ) {
      actions()
    }
    Surface(
      shape = shape,
      modifier = Modifier
        .fillMaxSize()
        .offset { IntOffset(x = swipeableState.offset.value.roundToInt(), y = 0) }
    ) {
      content()
    }
  }
}