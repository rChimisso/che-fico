package com.kreinto.chefico.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun AppScaffold(
  topBar: @Composable () -> Unit,
  bottomBar: @Composable () -> Unit,
  content: @Composable () -> Unit
) {
  Column(
    verticalArrangement = Arrangement.SpaceBetween,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    topBar()
    content()
    Spacer(modifier = Modifier.weight(1f))
    bottomBar()
  }
}