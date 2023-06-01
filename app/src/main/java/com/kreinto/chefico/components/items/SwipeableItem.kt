package com.kreinto.chefico.components.items

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import kotlin.math.roundToInt

/**
 * Item that can be swiped, revealing a list of actions to perform.
 *
 * @param icon Icon to display.
 * @param text Text to display.
 * @param contentColor Optional content color, defaults to TODO.
 * @param containerColor Optional container color, defaults to TODO.
 * @param actions Optional array of actions to display when the item gets swiped.
 * @param onClick Function called when the item is tapped.
 */
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun SwipeableItem(
  @DrawableRes icon: Int,
  text: String,
  contentColor: Color = MaterialTheme.colorScheme.primaryContainer,
  containerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
  actions: Array<@Composable () -> Unit> = arrayOf(),
  onClick: () -> Unit
) {
  val state = rememberSwipeableState(0)
  val offsetPx = with(LocalDensity.current) { (actions.size * 40).dp.toPx() }
  val anchors = mapOf(0f to 0, -offsetPx to 1)
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(IntrinsicSize.Min)
      .swipeable(
        state = state,
        anchors = anchors,
        thresholds = { _, _ -> FractionalThreshold(1.5f) },
        orientation = Orientation.Horizontal,
        resistance = null
      )
  ) {
    Surface(
      modifier = Modifier.fillMaxWidth(),
      shadowElevation = 12.dp,
      shape = RoundedCornerShape(12.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
      ) {
        actions.forEach { it() }
      }
    }
    BasicItem(
      icon = icon,
      text = text,
      modifier = Modifier.offset { IntOffset(x = state.offset.value.roundToInt(), y = 0) },
      contentColor = contentColor,
      containerColor = containerColor,
      onClick = onClick
    )
  }
}

/**
 * [Preview] for [SwipeableItem].
 */
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
@Preview
private fun SwipeableItemPreview() {
  SwipeableItem(
    icon = R.drawable.ic_notification,
    text = "Swipeable item",
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
}
