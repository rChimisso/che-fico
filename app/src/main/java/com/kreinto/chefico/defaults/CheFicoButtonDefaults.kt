package com.kreinto.chefico.defaults

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.R

object CheFicoButtonDefaults {

  data class RoundedButton(
    val icon: ButtonIcon,
    val size: ButtonSize = ButtonSize(
      width = 40.dp,
      height = 40.dp
    )
  ) {
    val elevation = ButtonElevation(
      default = 3.dp,
      pressed = 0.dp
    )
    val color = ButtonColor(
      content = Color(0xFF4CAF50),
      container = Color.White
    )
  }

  class TransparentButton constructor(
    val icon: ButtonIcon
  ) {
    val size = ButtonSize(
      width = 40.dp,
      height = 40.dp
    )
    val color = ButtonColor(
      content = Color(0xFF4CAF50),
      container = Color.Transparent
    )
  }

  data class ButtonIcon(
    var resource: Painter,
    var description: String
  )

  data class ButtonSize(
    val width: Dp,
    val height: Dp,
  )

  data class ButtonElevation(
    var default: Dp,
    var pressed: Dp
  )

  data class ButtonColor(
    val content: Color,
    val container: Color
  )
}


object CheFicoIcons {
  const val Close: Int = R.drawable.ic_close
  const val GoBack: Int = R.drawable.ic_arrow_back
  const val Check: Int = R.drawable.ic_check
  const val List: Int = R.drawable.ic_list
  const val Camera: Int = R.drawable.ic_photo_camera
  const val Remove: Int = R.drawable.ic_remove
  const val Settings: Int = R.drawable.ic_settings
  const val Share: Int = R.drawable.ic_share
  const val Snooze: Int = R.drawable.ic_snooze
  const val Trash: Int = R.drawable.ic_trash
}
