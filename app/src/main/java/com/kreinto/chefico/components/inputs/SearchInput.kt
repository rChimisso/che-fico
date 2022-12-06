package com.kreinto.chefico.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
fun SearchInput() {
  var query: String by rememberSaveable { mutableStateOf("") }

  TextField(
    value = query,
    onValueChange = { query = it },
    maxLines = 1,
    singleLine = true,
    colors = TextFieldDefaults.textFieldColors(
      backgroundColor = Color.LightGray,
      textColor = Color.Black,
      cursorColor = Color.Black,
      focusedIndicatorColor = Color.Transparent,
      unfocusedIndicatorColor = Color.Transparent,
      disabledIndicatorColor = Color.Transparent
    ),
    shape = RoundedCornerShape(50),
    placeholder = { Text(text = "TODO") },
    leadingIcon = {
      Icon(
        modifier = Modifier.size(32.dp),
        imageVector = Icons.Default.Search,
        contentDescription = "SearchInput",
        tint = Color.Green
      )
    },
    trailingIcon = {
      IconButton(onClick = { query = "" }) {
        Icon(
          modifier = Modifier.size(32.dp),
          imageVector = Icons.Default.Close,
          contentDescription = "Empty",
          tint = Color.Green
        )
      }
    },
    modifier = Modifier
      .padding(vertical = 0.dp)
      .fillMaxWidth(0.8F)
  )
}

@Composable
@Preview(showBackground = true)
fun SearchInputPreview() {
  SearchInput()
}