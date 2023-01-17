package com.kreinto.chefico.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.defaults.CheFicoButtonDefaults
import com.kreinto.chefico.defaults.CheFicoIcons

@Composable
fun RoundedButton(
  properties: CheFicoButtonDefaults.Button,
  onClick: () -> Unit
) {
  Button(
    modifier = Modifier
      .height(properties.size.height)
      .width(properties.size.width),
    contentPadding = PaddingValues(0.dp),
    shape = CircleShape,
    elevation = ButtonDefaults.buttonElevation(
      defaultElevation = properties.elevation.default,
      pressedElevation = properties.elevation.pressed,
    ),
    colors = ButtonDefaults.buttonColors(
      contentColor = properties.color.content,
      containerColor = properties.color.container
    ),
    onClick = onClick
  ) {
    Icon(
      painter = properties.icon.resource,
      contentDescription = properties.icon.description,
      modifier = Modifier.size(24.dp)
    )
  }
}

@Preview
@Composable
fun roundedButtonPreview() {
  RoundedButton(
    properties = CheFicoButtonDefaults.roundedButton(
      icon = CheFicoButtonDefaults.buttonIcon(
        iconDescription = "",
        iconResource = CheFicoIcons.Check
      )
    )
  ) {
  }
}