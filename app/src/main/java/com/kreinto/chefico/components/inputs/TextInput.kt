package com.kreinto.chefico.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

/**
 * Standardized [TextField].
 *
 * @param modifier An ordered, immutable collection of modifier elements that decorate or add behavior to this element.
 * @param init Initial value to display, defaults to `""`
 * @param placeholder Optional placeholder to display.
 * @param textColor Text color.
 * @param textStyle Style to be applied to the input text. The default textStyle uses the LocalTextStyle defined by the theme.
 * @param trailingIcon Optional [Composable] traling icon to display. Receives the current input value and a setter to change it.
 * @param onFocusChanged Optional function called when the input loses focus.
 * @param onValueChange Function called when the input value changes.
 */
@Composable
fun TextInput(
  modifier: Modifier = Modifier,
  init: String = "",
  placeholder: @Composable (() -> Unit)? = null,
  textColor: Color = Color.Black,
  textStyle: TextStyle = LocalTextStyle.current,
  singleLine: Boolean = true,
  trailingIcon: @Composable ((String, (String) -> Unit) -> Unit)? = null,
  onFocusChanged: ((FocusState) -> Unit)? = null,
  onValueChange: (String) -> Unit
) {
  var value: String by rememberSaveable { mutableStateOf(init) }
  val changeValue: (String) -> Unit = {
    value = it
    onValueChange(value)
  }
  TextField(
    modifier = modifier
      .fillMaxWidth()
      .onFocusChanged {
        if (onFocusChanged != null) {
          onFocusChanged(it)
        }
      },
    onValueChange = changeValue,
    value = value,
    maxLines = 1,
    singleLine = singleLine,
    placeholder = placeholder,
    textStyle = textStyle,
    colors = TextFieldDefaults.textFieldColors(
      textColor = textColor,
      cursorColor = Color.Black,
      backgroundColor = Color.Transparent,
      focusedIndicatorColor = Color.Transparent,
      unfocusedIndicatorColor = Color.Transparent,
      disabledIndicatorColor = Color.Transparent
    ),
    trailingIcon = {
      if (trailingIcon != null) {
        trailingIcon(value, changeValue)
      }
    }
  )
}

/**
 * [Preview] for [TextInput].
 */
@Composable
@Preview(showBackground = true)
private fun TextInputPreview() {
  TextInput {}
}
