package com.kreinto.chefico.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.ui.theme.bodyStyle

/**
 * Standardized [TextField].
 *
 * @param modifier An ordered, immutable collection of modifier elements that decorate or add behavior to this element.
 * @param init Initial value to display, defaults to `""`
 * @param placeholder Optional placeholder to display.
 * @param fontSize Font size.
 * @param trailingIcon Optional [Composable] traling icon to display. Receives the current input value and a setter to change it.
 * @param onFocusChanged Optional function called when the input loses focus.
 * @param onValueChange Function called when the input value changes.
 */
@ExperimentalMaterial3Api
@Composable
fun TextInput(
  modifier: Modifier = Modifier,
  isPassword: Boolean = false,
  init: String = "",
  placeholder: @Composable (() -> Unit)? = null,
  fontSize: TextUnit = bodyStyle.fontSize,
  singleLine: Boolean = true,
  readOnly: Boolean = false,
  label: String = "",
  isError: Boolean = false,
  underline: Boolean = true,
  trailingIcon: @Composable ((String, (String) -> Unit) -> Unit)? = null,
  leadingIcon: @Composable (() -> Unit)? = null,
  onFocusChanged: ((FocusState) -> Unit)? = null,
  onValueChange: (String) -> Unit
) {
  var value: String by rememberSaveable { mutableStateOf(init) }
  val changeValue: (String) -> Unit = {
    value = it
    onValueChange(value)
  }
  var isClicked by rememberSaveable { mutableStateOf(false) }
  var passwordVisibility by rememberSaveable { mutableStateOf(false) }

  TextField(
    modifier = modifier
      .fillMaxWidth()
      .onFocusChanged {
        if (it.hasFocus) {
          isClicked = true
        }
        if (onFocusChanged != null) {
          onFocusChanged(it)
        }
      },
    onValueChange = changeValue,
    value = value,
    singleLine = singleLine,
    placeholder = placeholder,
    textStyle = bodyStyle.merge(TextStyle(fontSize = fontSize)),
    readOnly = readOnly,
    colors = TextFieldDefaults.colors(
      focusedContainerColor = Color.Transparent,
      unfocusedContainerColor = Color.Transparent,
      disabledContainerColor = Color.Transparent,
      errorContainerColor = Color.Transparent,
      focusedIndicatorColor = if (underline) MaterialTheme.colorScheme.primary else Color.Transparent,
      unfocusedIndicatorColor = if (underline) MaterialTheme.colorScheme.primary else Color.Transparent,
      errorIndicatorColor = if (underline) MaterialTheme.colorScheme.error else Color.Transparent,
      focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
      unfocusedTrailingIconColor = MaterialTheme.colorScheme.primary,
      errorTrailingIconColor = MaterialTheme.colorScheme.error,
      cursorColor = MaterialTheme.colorScheme.primary
    ),
    label = { Text(label) },
    leadingIcon = leadingIcon,
    trailingIcon = {
      if (isPassword) {
        TransparentButton(
          icon = if (passwordVisibility) R.drawable.ic_visible else R.drawable.ic_hidden,
          contentDescription = "Password visibility",
          width = 24.dp,
          height = 24.dp
        ) { passwordVisibility = !passwordVisibility }
      } else if (trailingIcon != null) {
        trailingIcon(value, changeValue)
      }
    },
    isError = isClicked && isError,
    visualTransformation = if (!isPassword || passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
    keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text),
  )
}

/**
 * [Preview] for [TextInput].
 */
@ExperimentalMaterial3Api
@Composable
@Preview(showBackground = true)
private fun TextInputPreview() {
  TextInput {}
}
