package com.kreinto.chefico.views.poicreation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar

@ExperimentalMaterial3Api
@Composable
fun PoiCreationView(onNavigate: (String) -> Unit) {
  SimpleFrame(
    onBackPressed = onNavigate,
    bottomBar = {
      SimpleBottomBar(
        leftButtonData = ButtonData(
          icon = R.drawable.ic_close,
          contentDescription = "Cancel",
          colors = IconButtonDefaults.filledIconButtonColors(
            contentColor = Color.Red
          )
        ) {},
        rightButtonData = ButtonData(
          icon = R.drawable.ic_check,
          contentDescription = "Confirm"
        ) {}
      )
    }
  ) {

  }
}
