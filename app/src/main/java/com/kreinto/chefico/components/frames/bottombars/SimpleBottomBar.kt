package com.kreinto.chefico.components.frames.bottombars

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.components.buttons.RoundButton
import com.kreinto.chefico.components.data.ButtonData
import com.kreinto.chefico.ui.theme.*

/**
 * Simple Bottom Bar following Material3 guidelines.
 *
 * @param leftButtonData [ButtonData] for the left button.
 * @param centerButtonData Optional [ButtonData] for the central button.
 * @param rightButtonData [ButtonData] for the right button.
 */
@ExperimentalMaterial3Api
@Composable
fun SimpleBottomBar(
  leftButtonData: ButtonData,
  centerButtonData: ButtonData? = null,
  rightButtonData: ButtonData
) {
  Row(
    modifier = Modifier
      .height(80.dp)
      .fillMaxWidth()
      .padding(BottomAppBarDefaults.ContentPadding),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    RoundButton(leftButtonData)
    if (centerButtonData != null) {
      Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(40.dp),
        shadowElevation = 12.dp
      ) {
        IconButton(
          modifier = Modifier
            .width(80.dp)
            .height(28.dp),
          onClick = centerButtonData.onClick
        ) {
          Icon(
            imageVector = centerButtonData.icon,
            contentDescription = centerButtonData.contentDescription,
            tint = MaterialTheme.colorScheme.onPrimary,
          )
        }
      }
    }
    RoundButton(rightButtonData)
  }
}

/**
 * [Preview] for [SimpleBottomBar].
 */
@ExperimentalMaterial3Api
@Composable
@Preview
private fun SimpleBottomBarPreview() {
  SimpleBottomBar(
    leftButtonData = ButtonData(
      icon = Icons.Default.List,
      contentDescription = ""
    ) {},
    centerButtonData = ButtonData(
      icon = Icons.Default.Search,
      contentDescription = ""
    ) {},
    rightButtonData = ButtonData(
      icon = Icons.Default.Place,
      contentDescription = ""
    ) {}
  )
}
