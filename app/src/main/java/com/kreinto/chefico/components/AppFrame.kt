package com.kreinto.chefico.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.components.inputs.SearchInput

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun AppFrame() {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { SearchInput() },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
          containerColor = Color.White
        ),
        navigationIcon = {
          Icon(
            modifier = Modifier.size(32.dp),
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Green
          )
        },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        actions = {}
      )
    },
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
      FloatingActionButton(onClick = {}) {
        Text("X")
      }
    },
    content = { Text("BodyContent") }
  )
}

@ExperimentalMaterial3Api
@Composable
@Preview
fun AppFramePreview() {
  AppFrame()
}
