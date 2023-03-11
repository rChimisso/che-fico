package com.kreinto.chefico.views.poidetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.items.SwipeableItem

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun PoiDetailEvent() {
  SwipeableItem(
    icon = painterResource(id = R.drawable.ic_check),
    text = "Evento 1",
    actions = arrayOf({
      TransparentButton(
        icon = R.drawable.ic_trash,
        contentDescription = "Delete"
      ) {}
    })
  ) {}
}
