package com.kreinto.chefico.components.inputs

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
@Composable
fun SearchInput(onValueChange: (String) -> Unit) {
  TextInput(
    placeholder = { Text(text = stringResource(R.string.search_placeholder_label), color = MaterialTheme.colorScheme.primary) },
    textColor = MaterialTheme.colorScheme.primary,
    trailingIcon = { value, setter ->
      if (value.isEmpty()) {
        Icon(
          modifier = Modifier.size(24.dp),
          imageVector = Icons.Default.Search,
          contentDescription = "Search",
          tint = Color(0xff4caf50)
        )
      } else {
        TransparentButton(
          icon = R.drawable.ic_close,
          contentDescription = "Empty query"
        ) { setter("") }
      }
    },
    onValueChange = onValueChange,
  )
}

/**
 * [Preview] for [SearchInput].
 */
@Composable
@Preview(showBackground = true)
private fun SearchInputPreview() {
  SearchInput {}
}
