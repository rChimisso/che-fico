package com.kreinto.chefico.components.items

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.R

/**
 * Item that can be selected, handling the state change.
 *
 * @param icon Icon to display.
 * @param text Text to display.
 * @param contentColor Optional content color, defaults to TODO.
 * @param containerColor Optional container color, defaults to TODO.
 * @param selectable Whether this item can be selected.
 * @param onClick Function called when the item is tapped.
 * @param onLongClick Optional function called when the item is selected. Won't be called if the item is already [selectable].
 * @param onCheckedChange Function called when the item goes from selected to unselected and vice versa.
 */
@ExperimentalFoundationApi
@Composable
fun SelectableItem(
  @DrawableRes icon: Int,
  text: String,
  contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
  containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
  selectable: Boolean,
  onClick: () -> Unit,
  onLongClick: () -> Unit,
  onCheckedChange: (Boolean) -> Unit
) {
  var selected by remember { mutableStateOf(false) }
  val onClickAction = {
    if (selected || selectable) {
      selected = !selected
      onCheckedChange(selected)
    } else {
      onClick()
    }
  }
  val onLongClickAction = {
    if (!selected && !selectable) {
      selected = true
      onLongClick()
    }
  }
  if (selectable) {
    BasicItem(
      icon = { Checkbox(checked = selected, onCheckedChange = null, modifier = it) },
      text = text,
      contentColor = contentColor,
      containerColor = if (selected) Color(0xFFA8E2AB) else Color.White,
      border = if (selected) BorderStroke(width = 2.dp, color = Color(0xff4caf50)) else null,
      onClick = onClickAction,
      onLongClick = onLongClickAction
    )
  } else {
    selected = false
    BasicItem(
      icon = icon,
      text = text,
      contentColor = contentColor,
      containerColor = containerColor,
      onClick = onClickAction,
      onLongClick = onLongClickAction
    )
  }
}

/**
 * [Preview] for [SelectableItem].
 */
@ExperimentalFoundationApi
@Composable
@Preview
private fun SelectableItemPreview() {
  var selectable by remember { mutableStateOf(false) }
  SelectableItem(
    icon = R.drawable.ic_poi,
    text = "Selectable item",
    selectable = selectable,
    onClick = {},
    onLongClick = { selectable = true },
    onCheckedChange = { /*selectable = it*/ }
  )
}