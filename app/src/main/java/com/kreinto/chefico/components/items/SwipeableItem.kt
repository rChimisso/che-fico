package com.kreinto.chefico.components.items

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.times
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.ui.theme.InteractSizeMedium
import kotlin.math.roundToInt

/**
 * Item that can be swiped, revealing a list of actions to perform.
 *
 * @param icon Icon to display.
 * @param text Text to display.
 * @param actions Optional array of actions to display when the item gets swiped.
 * @param onClick Function called when the item is tapped.
 */
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun SwipeableItem(@DrawableRes icon: Int, text: String, actions: Array<@Composable () -> Unit> = arrayOf(), onClick: (() -> Unit)? = null) {
  val state = rememberSwipeableState(0)
  val offsetPx = with(LocalDensity.current) { (actions.size * InteractSizeMedium).toPx() }
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
    Surface(Modifier.fillMaxWidth(), MaterialTheme.shapes.small) {
      Row(Modifier.fillMaxSize(), Arrangement.End, Alignment.CenterVertically) { actions.forEach { it() } }
    }
    BasicItem(icon, text, Modifier.offset { IntOffset(state.offset.value.roundToInt(), 0) }, onClick = onClick)
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
    R.drawable.ic_notification,
    "Swipeable item",
    arrayOf(
      { TransparentButton(R.drawable.ic_snooze, R.string.preview) {} },
      { TransparentButton(R.drawable.ic_trash, R.string.preview) {} }
    )
  ) {}
}
