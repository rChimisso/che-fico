package com.kreinto.chefico.components.frames.topbars

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.Route
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
          TransparentButton(
            ButtonData(
              icon = R.drawable.ic_arrow_back,
              contentDescription = "Go back",
              onClick = { onNavPressed(Route.Back.path) },
            )
          )
        }
      },
      title = title,
      actions = {
        if (isDashboard) {
          TransparentButton(
            ButtonData(
              icon = R.drawable.ic_settings,
              contentDescription = "Settings",
              onClick = { onNavPressed(Route.Settings.path) },
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
