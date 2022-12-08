package com.kreinto.chefico.components.frames.topbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kreinto.chefico.components.buttons.RoundButton

/**
 * Simple Top Bar following Material3 guidelines.
 *
 * Transparent [TopAppBar] with just a [RoundButton] to go back.
 */
@ExperimentalMaterial3Api
@Composable
fun SimpleTopBar() {
  TopAppBar(
    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    navigationIcon = {
      RoundButton(icon = Icons.Default.ArrowBack, contentDescriptor = "Go back", onClick = {})
    },
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
      containerColor = Color.Transparent
    ),
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
  SimpleTopBar()
}