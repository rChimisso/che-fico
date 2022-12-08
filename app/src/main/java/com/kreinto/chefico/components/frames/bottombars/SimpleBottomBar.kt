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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.components.buttons.RoundButton

/**
 * Simple Bottom Bar following Material3 guidelines.
 *
 * 2 [RoundButton] left and right, 1 Rounded Button at the center.
 *
 * If you don't need the central button, consider using another overload.
 *
 * @param leftIcon [Icon][ImageVector] for the left button.
 * @param leftAction Action for the left button.
 * @param centerIcon [Icon][ImageVector] for the central button.
 * @param centerAction Action for the central button.
 * @param rightIcon [Icon][ImageVector] for the right button.
 * @param rightAction Action for the right button.
 */
@ExperimentalMaterial3Api
@Composable
fun SimpleBottomBar(
  leftIcon: ImageVector,
  leftAction: () -> Unit,
  centerIcon: ImageVector,
  centerAction: () -> Unit,
  rightIcon: ImageVector,
  rightAction: () -> Unit
) {
  Row(
    modifier = Modifier
      .height(80.dp)
      .fillMaxWidth()
      .padding(BottomAppBarDefaults.ContentPadding),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    RoundButton(icon = leftIcon, onClick = leftAction)
    Surface(
      shape = RoundedCornerShape(40.dp),
      shadowElevation = 12.dp
    ) {
      IconButton(
        onClick = centerAction,
        modifier = Modifier
          .width(80.dp)
          .height(28.dp)
      ) {
        Icon(
          imageVector = centerIcon,
          contentDescription = "",
          tint = Color(0xff4caf50),
        )
      }
    }
    RoundButton(icon = rightIcon, onClick = rightAction)
  }
}

/**
 * Simple Bottom Bar following Material3 guidelines.
 *
 * 2 [RoundButton] left and right.
 *
 * @param leftIcon [Icon][ImageVector] for the left button.
 * @param leftAction Action for the left button.
 * @param rightIcon [Icon][ImageVector] for the right button.
 * @param rightAction Action for the right button.
 */
@ExperimentalMaterial3Api
@Composable
fun SimpleBottomBar(
  leftIcon: ImageVector,
  leftAction: () -> Unit,
  rightIcon: ImageVector,
  rightAction: () -> Unit
) {
  Row(
    modifier = Modifier
      .height(80.dp)
      .fillMaxWidth()
      .padding(BottomAppBarDefaults.ContentPadding),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    RoundButton(icon = leftIcon, onClick = leftAction)
    RoundButton(icon = rightIcon, onClick = rightAction)
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
    leftIcon = Icons.Default.List,
    leftAction = {},
    centerIcon = Icons.Default.Search,
    centerAction = {},
    rightIcon = Icons.Default.Place,
    rightAction = {},
  )
}