package com.kreinto.chefico.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppFrame(
  title: @Composable () -> Unit,
  navigationIcon: @Composable () -> Unit,
  actions: @Composable () -> Unit,
  floatingActionButton: @Composable () -> Unit,
  content: @Composable () -> Unit,
) {
  Scaffold(
    topBar = {
      Surface(
        shadowElevation = 12.dp,
      ) {
        TopAppBar(
          modifier = Modifier,
          title = title,
          colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White
          ),
          navigationIcon = navigationIcon,
          scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
          actions = {
            if (actions != null) {
              actions()
            } else {
              Spacer(modifier = Modifier.size(32.dp))
            }
          }
        )
      }
    },
    floatingActionButtonPosition = FabPosition.Center,
    floatingActionButton = floatingActionButton,
    content = { padding ->
      Column(
        modifier = Modifier.padding(padding)
      ) {
        content()
      }
    }
  )
}

@ExperimentalMaterial3Api
@Composable
@Preview
fun AppFramePreview() {
}
