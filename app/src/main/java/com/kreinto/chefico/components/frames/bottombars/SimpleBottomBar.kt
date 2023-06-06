package com.kreinto.chefico.components.frames.bottombars

import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.FilledButton
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.ui.theme.InteractSizeLarge
import com.kreinto.chefico.ui.theme.InteractSizeSmall

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
      .fillMaxWidth()
      .height(InteractSizeLarge)
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
          iconColor = centerButtonData.iconColor,
          width = InteractSizeSmall,
          height = InteractSizeLarge,
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
    leftButtonData = ButtonData(R.drawable.ic_list, R.string.preview) {},
    centerButtonData = ButtonData(R.drawable.ic_photo_camera, R.string.preview) {},
    rightButtonData = ButtonData(R.drawable.ic_share, R.string.preview) {}
  )
}
