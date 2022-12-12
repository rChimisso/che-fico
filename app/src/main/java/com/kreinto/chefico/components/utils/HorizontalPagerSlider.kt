package com.kreinto.chefico.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalPagerSlider(
  modifier: Modifier = Modifier,
  currentPage: Int,
  count: Int,
) {
  LazyRow(
    modifier = modifier
      .padding(4.dp)
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    items(count) { page ->
      if (page == currentPage) {
        Box(
          modifier = Modifier
            .fillParentMaxWidth((1f / count) - ((count - 1) * 0.001f))
            .height(2.dp)
            .background(Color.White)
        )
      } else {
        Box(
          modifier = Modifier
            .fillParentMaxWidth((1f / count) - ((count - 1) * 0.001f))
            .height(2.dp)
            .background(Color.LightGray.copy(0.5f))
        )
      }
    }
  }
}