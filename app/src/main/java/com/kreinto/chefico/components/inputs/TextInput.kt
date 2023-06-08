package com.kreinto.chefico.components.inputs

import androidx.annotation.StringRes
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.ui.theme.IconSizeMedium
import com.kreinto.chefico.ui.theme.bodyStyle

/**
 * Standardized [TextField].
 *
 * @param modifier
 * @param isPassword Whether the text input contains a password.
 * @param init Initial value.
 * @param placeholder Placeholder to diplay when the text input has no value.
 * @param textStyle Text style.
 * @param singleLine Whether the text input can have only 1 line.
 * @param readOnly Whether the text input vallue cannot be modified.
 * @param label Text input label.
 * @param isError Whether the text input has errors.
 * @param underline Whether to show the text input underline.
 * @param trailingIcon Optional [Composable] traling icon to display. Receives the current input value and a setter to change it.
 * @param leadingIcon Optional [Composable] leading icon to display.
 * @param onFocusChanged Optional function called when the input loses focus.
 * @param onValueChange Function called when the input value changes.
 */
@ExperimentalMaterial3Api
@Composable
fun TextInput(
  modifier: Modifier = Modifier,
  isPassword: Boolean = false,
  init: String = "",
  @StringRes placeholder: Int? = null,
  textStyle: TextStyle = bodyStyle,
  singleLine: Boolean = true,
  readOnly: Boolean = false,
  @StringRes label: Int? = null,
  @StringRes supportingText: Int? = null,
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
  var showPassword by rememberSaveable { mutableStateOf(false) }
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
    supportingText = if (supportingText != null) ({ Text(stringResource(supportingText)) }) else null,
    onValueChange = changeValue,
    value = value,
    singleLine = singleLine,
    placeholder = {
      if (placeholder != null) {
        Text(stringResource(placeholder), style = textStyle)
      }
    },
    textStyle = textStyle,
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
    label = if (label != null) ({ Text(stringResource(label)) }) else null,
    leadingIcon = leadingIcon,
    trailingIcon = {
      if (isPassword) {
        TransparentButton(
          icon = if (showPassword) R.drawable.ic_visible else R.drawable.ic_hidden,
          contentDescription = if (showPassword) R.string.hide_psw else R.string.show_psw,
          width = IconSizeMedium
        ) { showPassword = !showPassword }
      } else if (trailingIcon != null) {
        trailingIcon(value, changeValue)
      }
    },
    isError = isClicked && isError,
    visualTransformation = if (!isPassword || showPassword) VisualTransformation.None else PasswordVisualTransformation(),
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
