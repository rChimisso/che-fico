package com.kreinto.chefico.components.frames.topbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.components.buttons.SimpleButton
import com.kreinto.chefico.components.data.ButtonData

/**
 * Standard Top Bar following Material3 guidelines.
 *
 * @param isDashboard Whether this top bar is for the dashboard. Controls whether to show the
 * "Go back" icon or the "Settings" icon.
 * @param onClick Function called when the top bar button is clicked.
 * @param title [Composable] to show at the center.
 */
@ExperimentalMaterial3Api
@Composable
fun StandardTopBar(
  isDashboard: Boolean = false,
  onClick: () -> Unit,
  title: @Composable () -> Unit
) {
  Surface(shadowElevation = 12.dp) {
    TopAppBar(
      scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
      colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.White),
      navigationIcon = {
        if (!isDashboard) {
          SimpleButton(
            ButtonData(
              icon = Icons.Default.ArrowBack,
              contentDescription = "Go back",
              onClick = onClick,
            )
          )
        }
      },
      title = title,
      actions = {
        if (isDashboard) {
          SimpleButton(
            ButtonData(
              icon = Icons.Default.Settings,
              contentDescription = "Settings",
              onClick = onClick,
            )
          )
        }
      }
    )
  }
}

@ExperimentalMaterial3Api
@Composable
@Preview
private fun StandardTopBarPreview() {
  StandardTopBar(onClick = {}) {}
}
