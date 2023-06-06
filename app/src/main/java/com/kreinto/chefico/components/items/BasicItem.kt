package com.kreinto.chefico.components.items

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.R
import com.kreinto.chefico.ui.theme.*

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
    shape = MaterialTheme.shapes.small,
    border = border
  ) {
    Row(
      modifier = Modifier
        .padding(PaddingSmall)
        .height(InteractSizeMedium),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Start
    ) {
      icon(
        Modifier
          .padding(horizontal = PaddingMedium)
          .size(IconSizeMedium)
      )
      Text(
        text = text,
        modifier = Modifier.padding(horizontal = PaddingSmall),
        fontSize = LabelExtraLarge,
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
