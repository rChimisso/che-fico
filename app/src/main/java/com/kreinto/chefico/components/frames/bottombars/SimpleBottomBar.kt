package com.kreinto.chefico.components.frames.bottombars

import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.FilledButton
import com.kreinto.chefico.components.buttons.data.ButtonData

/**
 * Simple Bottom Bar following Material3 guidelines.
 *
 * @param leftButtonData [ButtonData] for the left button.
 * @param centerButtonData Optional [ButtonData] for the central button.
 * @param rightButtonData [ButtonData] for the right button.
 */
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
    FilledButton(leftButtonData)
    if (centerButtonData != null) {
      FilledButton(
        ButtonData(
          icon = centerButtonData.icon,
          contentDescription = centerButtonData.contentDescription,
          colors = centerButtonData.colors,
          height = 28.dp,
          width = 80.dp,
          enabled = centerButtonData.enabled,
          onClick = centerButtonData.onClick
        )
      )
    }
    FilledButton(rightButtonData)
  }
}

/**
 * [Preview] for [SimpleBottomBar].
 */
@Composable
@Preview
private fun SimpleBottomBarPreview() {
  SimpleBottomBar(
    leftButtonData = ButtonData(
      icon = R.drawable.ic_list,
      contentDescription = ""
    ) {},
    centerButtonData = ButtonData(
      icon = R.drawable.ic_photo_camera,
      contentDescription = ""
    ) {},
    rightButtonData = ButtonData(
      icon = R.drawable.ic_share,
      contentDescription = ""
    ) {}
  )
}
