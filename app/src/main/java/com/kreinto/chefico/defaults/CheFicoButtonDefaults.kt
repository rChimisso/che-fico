package com.kreinto.chefico.defaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.R

object CheFicoButtonDefaults {

  fun roundedButton(
    icon: ButtonIcon,
    size: ButtonSize = buttonSize(40.dp),
  ): Button = Button(
    icon = icon,
    size = size,
    elevation = buttonElevation(
      default = 3.dp,
      pressed = 0.dp
    ),
    color = buttonColor(
      content = Color(0xff4caf50),
      container = Color(0xffffffff)
    )
  )

  fun transparentButton(
    icon: ButtonIcon,
  ): Button = Button(
    icon = icon,
    size = buttonSize(40.dp),
    elevation = buttonElevation(
      default = 3.dp,
      pressed = 0.dp
    ),
    color = buttonColor(
      content = Color(0xff4caf50),
      container = Color(0xffffff00)
    )
  )

  @Composable
  fun buttonIcon(
    iconDescription: String,
    iconResource: Int
  ): ButtonIcon = ButtonIcon(
    resource = painterResource(id = iconResource),
    description = iconDescription
  )


  fun buttonSize(size: Dp): ButtonSize = ButtonSize(
    width = size,
    height = size,
  )

  fun buttonSize(width: Dp, height: Dp) = ButtonSize(
    width = width,
    height = height,
  )

  private fun buttonElevation(
    default: Dp,
    pressed: Dp
  ): ButtonElevation = ButtonElevation(
    default = default,
    pressed = pressed
  )

  private fun buttonColor(
    content: Color,
    container: Color
  ): ButtonColor = ButtonColor(
    content = content,
    container = container
  )

  class Button internal constructor(
    val icon: ButtonIcon,
    val size: ButtonSize,
    val elevation: ButtonElevation,
    val color: ButtonColor
  )

  class ButtonIcon internal constructor(
    var resource: Painter,
    var description: String
  )

  class ButtonSize internal constructor(
    val width: Dp,
    val height: Dp,
  )

  internal class ButtonElevation internal constructor(
    var default: Dp,
    var pressed: Dp
  )

  internal class ButtonColor internal constructor(
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
