package com.kreinto.chefico.views.poilist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.components.inputs.SearchInput
import com.kreinto.chefico.components.items.SelectableItem

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun PoiListView() {
  var selectables by remember { mutableStateOf(0) }
  StandardFrame(
    title = { SearchInput(onValueChange = {}) },
    bottomBar = {
      if (selectables > 0) {
        SimpleBottomBar(
          leftIcon = Icons.Default.Delete,
          leftAction = {},
          rightIcon = Icons.Default.Share,
          rightAction = {}
        )
      }
    }
  ) {
    Spacer(
      modifier = Modifier
        .fillMaxWidth()
        .height(16.dp)
    )
    for (i in 0..20) {
      SelectableItem(
        icon = Icons.Default.Star,
        text = "POI Name $i",
        selectable = selectables > 0,
        onClick = {},
        onLongClick = { selectables++ },
        onCheckedChange = {
          if (it) {
            selectables++
          } else {
            selectables--
          }
        }
      )
      Spacer(
        modifier = Modifier
          .fillMaxWidth()
          .height(16.dp)
      )
    }
  }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
@Preview
private fun PoiListViewPreview() {
  PoiListView()
}