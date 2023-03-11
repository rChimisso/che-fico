package com.kreinto.chefico.views.plantdetail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun PlantDetailFloatingActionButton() {
  FilledIconButton(
    onClick = { /*TODO*/ },
    colors = IconButtonDefaults.filledIconButtonColors(
      contentColor = Color(0xff20211e),
      containerColor = Color(0xff32C896),
    )
  ) {
    Icon(
      Icons.Rounded.Add,
      ""
    )
  }
}