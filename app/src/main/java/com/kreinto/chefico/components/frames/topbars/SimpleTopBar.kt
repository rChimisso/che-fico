package com.kreinto.chefico.components.frames.topbars

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.R
import com.kreinto.chefico.Route
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.buttons.data.ButtonData

/**
 * Simple Top Bar following Material3 guidelines.
 *
 * Transparent [TopAppBar] with just a [RoundButton] to go back.
 *
 * @param onBackPressed Function called when the back button is clicked.
 */
@ExperimentalMaterial3Api
@Composable
fun SimpleTopBar(onBackPressed: (String) -> Unit) {
  TopAppBar(
    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    navigationIcon = {
      TransparentButton(
        ButtonData(
          icon = R.drawable.ic_arrow_back,
          contentDescription = "Go back",
          onClick = { onBackPressed(Route.Back.path) },
          colors = IconButtonDefaults.iconButtonColors(
            contentColor = Color.White
          ),
        )
      )
    },
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.Transparent),
    title = {}
  )
}

/**
 * [Preview] for [SimpleTopBar].
 */
@ExperimentalMaterial3Api
@Composable
@Preview
private fun SimpleTopBarPreview() {
  SimpleTopBar {}
}
