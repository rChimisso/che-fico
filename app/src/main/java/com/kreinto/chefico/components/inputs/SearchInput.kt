package com.kreinto.chefico.components.inputs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.ui.theme.IconSizeMedium
import com.kreinto.chefico.ui.theme.PaddingSmall


/**
 * Search input.
 *
 * @param onValueChange Function called when the input value changes.
 */
@ExperimentalMaterial3Api
@Composable
fun SearchInput(onValueChange: (String) -> Unit) {
  TextInput(
    modifier = Modifier.padding(start = PaddingSmall),
    singleLine = true,
    underline = false,
    placeholder = R.string.search_placeholder,
    textStyle = MaterialTheme.typography.headlineMedium,
    trailingIcon = { value, setter ->
      if (value.isEmpty()) {
        Icon(
          Icons.Default.Search,
          stringResource(R.string.search_placeholder),
          Modifier.size(IconSizeMedium),
          MaterialTheme.colorScheme.primary
        )
      } else {
        TransparentButton(R.drawable.ic_close, R.string.empty_query) { setter("") }
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
