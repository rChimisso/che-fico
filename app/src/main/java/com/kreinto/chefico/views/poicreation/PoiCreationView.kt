package com.kreinto.chefico.views.poicreation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kreinto.chefico.components.data.ButtonData
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
          Icons.Default.Close,
          "Cancel",
          Color.Red
        ) {},
        rightButtonData = ButtonData(
          Icons.Default.Check,
          "Confirm"
        ) {}
      )
    }
  ) {

  }
}
