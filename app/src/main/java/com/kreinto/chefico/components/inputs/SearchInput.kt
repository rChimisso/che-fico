package com.kreinto.chefico.components.inputs

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton


/**
 * Search input.
 *
 * @param onValueChange Function called when the input value changes.
 */
@ExperimentalMaterial3Api
@Composable
fun SearchInput(onValueChange: (String) -> Unit) {
  TextInput(
    singleLine = true,
    placeholder = {
      Text(
        stringResource(R.string.search_placeholder),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineMedium
      )
    },
    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
    trailingIcon = { value, setter ->
      if (value.isEmpty()) {
        Icon(Icons.Default.Search, stringResource(R.string.search_placeholder), Modifier.size(32.dp), MaterialTheme.colorScheme.primary)
      } else {
        TransparentButton(R.drawable.ic_close, stringResource(R.string.empty_query)) { setter("") }
      }
    },
    onValueChange = onValueChange,
  )
}

/**
 * [Preview] for [SearchInput].
 */
@ExperimentalMaterial3Api
@Composable
@Preview(showBackground = true)
private fun SearchInputPreview() {
  SearchInput {}
}
