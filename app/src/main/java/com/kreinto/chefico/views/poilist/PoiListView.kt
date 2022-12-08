package com.kreinto.chefico.views.poilist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.components.inputs.SearchInput

@ExperimentalMaterial3Api
@Composable
fun PoiListView() {
  StandardFrame(
    title = { SearchInput(onValueChange = {}) },
    bottomBar = {
      SimpleBottomBar(
        leftIcon = Icons.Default.Delete,
        leftAction = {},
        rightIcon = Icons.Default.Share,
        rightAction = {}
      )
    }
  ) {

  }
}

@ExperimentalMaterial3Api
@Composable
@Preview
private fun PoiListViewPreview() {
  PoiListView()
}