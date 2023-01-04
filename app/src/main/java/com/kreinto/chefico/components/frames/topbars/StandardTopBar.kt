package com.kreinto.chefico.components.frames.topbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.AppRoute
import com.kreinto.chefico.components.buttons.SimpleButton
import com.kreinto.chefico.components.data.ButtonData
import com.kreinto.chefico.ui.theme.level0

/**
 * Standard Top Bar following Material3 guidelines.
 *
 * @param isDashboard Whether this top bar is for the dashboard. Controls whether to show the
 * "Go back" icon or the "Settings" icon.
 * @param onNavPressed Function called when the top bar navigation button is clicked.
 * @param title [Composable] to show at the center.
 */
@ExperimentalMaterial3Api
@Composable
fun StandardTopBar(
  isDashboard: Boolean = false,
  onNavPressed: (String) -> Unit,
  title: @Composable () -> Unit
) {
  Surface(tonalElevation = level0) {
    TopAppBar(
      scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
      colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.surface),
      navigationIcon = {
        if (!isDashboard) {
          SimpleButton(
            ButtonData(
              icon = Icons.Default.ArrowBack,
              contentDescription = "Go back",
              onClick = { onNavPressed(AppRoute.Back.route) },
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
              onClick = { onNavPressed(AppRoute.Settings.route) },
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
  StandardTopBar(onNavPressed = {}) {}
}
