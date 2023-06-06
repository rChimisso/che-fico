package com.kreinto.chefico.components.frames.topbars

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton

/**
 * Simple Top Bar following Material3 guidelines.
 *
 * Transparent [TopAppBar] with just a [TransparentButton] to go back.
 *
 * @param onNavigate Function called when the back button is clicked.
 */
@ExperimentalMaterial3Api
@Composable
fun SimpleTopBar(onNavigate: (String) -> Unit) {
  CenterAlignedTopAppBar(
    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    navigationIcon = { TransparentButton(R.drawable.ic_arrow_back, R.string.go_back) { onNavigate(CheFicoRoute.Back.path) } },
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
