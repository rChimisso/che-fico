package com.kreinto.chefico.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchInput(
  onValueChange: (query: String) -> Unit
) {
  var query: String by rememberSaveable { mutableStateOf("") }
  TextField(
    value = query,
    onValueChange = {
      query = it
      onValueChange(query)
    },
    maxLines = 1,
    singleLine = true,
    colors = TextFieldDefaults.textFieldColors(
      backgroundColor = Color.Transparent,
      textColor = Color.Black,
      cursorColor = Color.Black,
      focusedIndicatorColor = Color.Transparent,
      unfocusedIndicatorColor = Color.Transparent,
      disabledIndicatorColor = Color.Transparent
    ),
    placeholder = { Text(text = "Search...") },
    trailingIcon = {
      if (query.isEmpty()) {
        Icon(
          modifier = Modifier.size(24.dp),
          imageVector = Icons.Default.Search,
          contentDescription = null,
          tint = Color(0xff4caf50)
        )
      } else {
        IconButton(onClick = { query = "" }) {
          Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Default.Close,
            contentDescription = null,
            tint = Color(0xff4caf50)
          )
        }
      }
    },
    modifier = Modifier.fillMaxWidth()
  )
}

@Composable
@Preview(showBackground = true)
private fun SearchInputPreview() {
  SearchInput {

  }
}