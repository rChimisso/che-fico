package com.kreinto.chefico.components.frames.topbars

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.ui.theme.level0

/**
 * Standard Top Bar following Material3 guidelines.
 *
 * @param onNavPressed Function called when the top bar navigation button is clicked.
 * @param title [Composable] to show at the center.
 * @param actions list of actions.
 */
@ExperimentalMaterial3Api
@Composable
fun StandardTopBar(
  onNavPressed: (String) -> Unit,
  title: @Composable () -> Unit,
  actions: @Composable (RowScope.() -> Unit) = {}
) {
  Surface(tonalElevation = level0) {
    CenterAlignedTopAppBar(
      scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
      navigationIcon = {
        TransparentButton(
          ButtonData(
            icon = R.drawable.ic_arrow_back,
            contentDescription = "Go back",
            onClick = { onNavPressed(CheFicoRoute.Back.path) },
          )
        )
      },
      title = title,
      actions = actions
    )
  }
}

@ExperimentalMaterial3Api
@Composable
@Preview
private fun StandardTopBarPreview() {
  StandardTopBar(onNavPressed = {}, title = {}) {}
}
