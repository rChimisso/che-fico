package com.kreinto.chefico.components.frames.topbars

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
  showBackAction: Boolean = true,
  showSettingsAction: Boolean = false,
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
        if (showBackAction) {
          IconButton(
            onClick = {},
            modifier = Modifier.size(40.dp),
            colors = IconButtonDefaults.iconButtonColors(
              containerColor = Color.Transparent
            )
          ) {
            Icon(
              imageVector = Icons.Default.ArrowBack,
              contentDescription = "Go back",
              tint = Color(0xff4caf50),
            )
          }
        }
      },
      title = title,
      actions = {
        if (showSettingsAction) {
          IconButton(
            onClick = {},
            modifier = Modifier.size(40.dp),
            colors = IconButtonDefaults.iconButtonColors(
              containerColor = Color.Transparent
            )
          ) {
            Icon(
              imageVector = Icons.Default.Settings,
              contentDescription = "Settings",
              tint = Color(0xff4caf50),
            )
          }
        }
      }
    )
  }
}

@ExperimentalMaterial3Api
@Composable
@Preview
private fun StandardTopBarPreview() {
  StandardTopBar {}
}