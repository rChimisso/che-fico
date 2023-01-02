package com.kreinto.chefico.components.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Item that can be selected, handling the state change.
 *
 * @param icon Icon to display.
 * @param text Text to display.
 * @param tint Optional icon color, defaults to green.
 * @param selectable Whether this item can be selected.
 * @param onClick Function called when the item is tapped.
 * @param onLongClick Optional function called when the item is selected. Won't be called if the item is already [selectable].
 * @param onCheckedChange Function called when the item goes from selected to unselected and vice versa.
 */
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun SelectableItem(
  icon: ImageVector,
  text: String,
  tint: Color = Color(0xff4caf50),
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
      icon = {
        Checkbox(
          modifier = it,
          checked = selected,
          onCheckedChange = null,
          colors = CheckboxDefaults.colors(
            checkedColor = Color(0xff4caf50),
            uncheckedColor = Color(0xff4caf50),
            checkmarkColor = Color.White
          )
        )
      },
      text = text,
      color = if (selected) Color(0xFFA8E2AB) else Color.White,
      border = if (selected) BorderStroke(width = 2.dp, color = Color(0xff4caf50)) else null,
      onClick = onClickAction,
      onLongClick = onLongClickAction
    )
  } else {
    selected = false
    BasicItem(
      icon = icon,
      text = text,
      tint = tint,
      onClick = onClickAction,
      onLongClick = onLongClickAction
    )
  }
}

/**
 * [Preview] for [SelectableItem].
 */
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
@Preview
private fun SelectableItemPreview() {
  var selectable by remember { mutableStateOf(false) }
  SelectableItem(
    icon = Icons.Default.Star,
    text = "Selectable item",
    selectable = selectable,
    onClick = {},
    onLongClick = { selectable = true },
    onCheckedChange = { /*selectable = it*/ }
  )
}