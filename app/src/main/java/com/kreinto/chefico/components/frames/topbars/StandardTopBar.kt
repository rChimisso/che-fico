package com.kreinto.chefico.components.frames.topbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.components.buttons.SimpleButton

/**
 * Standard Top Bar following Material3 guidelines.
 *
 * @param showBackAction Whether to show the [IconButton] to go back.
 * @param showSettingsAction Whether to show the [IconButton] to go open the app settings.
 * @param title [Composable] to show at the center.
 */
@ExperimentalMaterial3Api
@Composable
fun StandardTopBar(
  isDashboard: Boolean = false,
  onClick: () -> Unit,
  title: @Composable () -> Unit
) {
  Surface(
    shadowElevation = 12.dp,
  ) {
    TopAppBar(
      scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
      colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.White
      ),
      navigationIcon = {
        if (!isDashboard) {
          SimpleButton(
            onClick = onClick,
            icon = Icons.Default.ArrowBack,
            contentDescriptor = "Go back",
          )
        }
      },
      title = title,
      actions = {
        if (isDashboard) {
          SimpleButton(
            onClick = onClick,
            icon = Icons.Default.Settings,
            contentDescriptor = "Settings"
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