package com.kreinto.chefico.views.poidetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.items.SwipeableItem

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun PoiDetailEvent() {
  SwipeableItem(
    icon = Icons.Default.CheckCircle,
    text = "Evento 1",
    actions = arrayOf({
      TransparentButton(
        icon = R.drawable.ic_trash,
        contentDescription = "Delete"
      ) {}
    })
  ) {}
}
