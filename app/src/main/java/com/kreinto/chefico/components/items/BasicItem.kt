package com.kreinto.chefico.components.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun BasicItem(
  icon: ImageVector,
  text: String,
  modifier: Modifier = Modifier,
  tint: Color = Color(0xff4caf50),
  onLongClick: (() -> Unit)? = null,
  onClick: () -> Unit
) {
  BasicItem(
    icon = {
      Icon(
        modifier = it,
        imageVector = icon,
        contentDescription = "",
        tint = tint
      )
    },
    text = text,
    modifier = modifier,
    onLongClick = onLongClick,
    onClick = onClick
  )
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun BasicItem(
  icon: @Composable (Modifier) -> Unit,
  text: String,
  modifier: Modifier = Modifier,
  color: Color = Color.White,
  border: BorderStroke? = null,
  onLongClick: (() -> Unit)? = null,
  onClick: () -> Unit
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .combinedClickable(onClick = onClick, onLongClick = onLongClick),
    shadowElevation = 12.dp,
    shape = RoundedCornerShape(10.dp),
    color = color,
    border = border
  ) {
    Row(
      modifier = Modifier
        .padding(4.dp)
        .height(40.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Start
    ) {
      icon(Modifier.padding(horizontal = 8.dp))
      Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = text,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        fontSize = 18.sp
      )
    }
  }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
@Preview
private fun BasicItemPreview() {
  BasicItem(icon = Icons.Default.ShoppingCart, text = "Basic item") {}
}
