package com.kreinto.chefico.views.poidetail.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.items.SwipeableItem

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun PoiDetailEvent() {
  SwipeableItem(
    icon = R.drawable.ic_check,
    text = stringResource(R.string.event_1_label),
    actions = arrayOf({
      TransparentButton(
        icon = R.drawable.ic_trash,
        contentDescription = "Delete"
      ) {}
    })
  ) {}
}
