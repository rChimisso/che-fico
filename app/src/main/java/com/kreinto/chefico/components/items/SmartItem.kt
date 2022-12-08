package com.kreinto.chefico.components.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SmartItem() {
  /*val offsetPx = with(LocalDensity.current) { swipeOffset.toPx() }
  val anchors = mapOf(
    0f to 0,
    -offsetPx to 1
  )
  Surface(
    elevation = 12.dp,
    shape = RoundedCornerShape(10.dp),
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
    }*/
}

@Composable
@Preview
private fun SmartItemPreview() {
  SmartItem()
}