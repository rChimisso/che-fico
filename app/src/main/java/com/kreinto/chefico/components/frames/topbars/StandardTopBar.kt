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

/**
 * Standard Top Bar following Material3 guidelines.
 *
 * @param onNavigate Function called when the top bar navigation button is clicked.
 * @param title [Composable] to show at the center.
 * @param actions list of actions.
 */
@ExperimentalMaterial3Api
@Composable
fun StandardTopBar(onNavigate: (String) -> Unit, title: @Composable () -> Unit, actions: @Composable (RowScope.() -> Unit) = {}) {
  Surface {
    CenterAlignedTopAppBar(
      scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
      navigationIcon = { TransparentButton(R.drawable.ic_arrow_back, R.string.go_back) { onNavigate(CheFicoRoute.Back.path) } },
      title = title,
      actions = actions
    )
  }
}

@ExperimentalMaterial3Api
@Composable
@Preview
private fun StandardTopBarPreview() {
  StandardTopBar({}, {}) {}
}
