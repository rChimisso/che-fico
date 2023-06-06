package com.kreinto.chefico.components.items

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.R

/**
 * Basic item with an icon and text.
 *
 * @param icon Icon to display.
 * @param text Text to display.
 * @param modifier Optional [Modifier].
 * @param onLongClick Optional function called when the item is pressed for long.
 * @param onClick Function called when the item is tapped.
 */
@ExperimentalFoundationApi
@Composable
fun BasicItem(
  @DrawableRes icon: Int,
  text: String,
  modifier: Modifier = Modifier,
  onLongClick: (() -> Unit)? = null,
  onClick: (() -> Unit)? = null
) {
  BasicItem(
    icon = { Icon(painterResource(icon), null, it) },
    text = text,
    modifier = modifier,
    onLongClick = onLongClick,
    onClick = onClick
  )
}

/**
 * Basic item with an icon and text.
 *
 * @param icon Icon to display.
 * @param text Text to display.
 * @param modifier Optional [Modifier].
 * @param border Optional border color, defaults to null.
 * @param onLongClick Optional function called when the item is pressed for long.
 * @param onClick Function called when the item is tapped.
 */
@ExperimentalFoundationApi
@Composable
internal fun BasicItem(
  icon: @Composable (Modifier) -> Unit,
  text: String,
  modifier: Modifier = Modifier,
  border: BorderStroke? = null,
  onLongClick: (() -> Unit)? = null,
  onClick: (() -> Unit)? = null
) {
  var internalModifier = modifier.fillMaxWidth()
  if (onClick != null || onLongClick != null) {
    internalModifier = internalModifier.combinedClickable(onClick = onClick ?: {}, onLongClick = onLongClick)
  }
  Surface(
    modifier = internalModifier,
    shape = RoundedCornerShape(12.dp),
    border = border
  ) {
    Row(
      modifier = Modifier
        .padding(4.dp)
        .height(40.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Start
    ) {
      icon(
        Modifier
          .padding(horizontal = 8.dp)
          .size(24.dp)
      )
      Text(
        text = text,
        modifier = Modifier.padding(horizontal = 4.dp),
        fontSize = 24.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
      )
    }
  }
}

/**
 * [Preview] for [BasicItem].
 */
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
@Preview
private fun BasicItemPreview() {
  BasicItem(icon = R.drawable.ic_settings, text = "Basic item") {}
}
