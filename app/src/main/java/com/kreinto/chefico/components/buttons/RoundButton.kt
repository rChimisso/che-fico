package com.kreinto.chefico.components.buttons

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.components.data.ButtonData

/**
 * Round button.
 *
 * @param buttonData [ButtonData].
 */
@Composable
fun RoundButton(buttonData: ButtonData) {
  Surface(shape = CircleShape, shadowElevation = 12.dp) { SimpleButton(buttonData) }
}

/**
 * [Preview] for [RoundButton].
 */
@Composable
@Preview()
private fun RoundButtonPreview() {
  RoundButton(ButtonData(icon = Icons.Default.Add, contentDescription = "", onClick = {}))
}
