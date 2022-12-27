package com.kreinto.chefico.views.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.kreinto.chefico.components.frames.StandardFrame

@ExperimentalMaterial3Api
@Composable
fun SettinsView(onNavigate: (String) -> Unit) {
  StandardFrame(
    onNavPressed = onNavigate,
    title = { /*TODO*/ }
  ) {

  }
}
