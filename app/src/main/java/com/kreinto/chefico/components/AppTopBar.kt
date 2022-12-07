package com.kreinto.chefico.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// costruita secondo i principi del material design

@Composable
fun AppTopBar(
  navigationIcon: @Composable () -> Unit,
  title: @Composable () -> Unit,
  actionButton: @Composable () -> Unit,
) {
  Surface(
    elevation = 12.dp,
    modifier = Modifier
      .fillMaxWidth()
      .height(56.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Spacer(modifier = Modifier.size(16.dp))
      navigationIcon()
      Spacer(modifier = Modifier.size(16.dp))
      Spacer(modifier = Modifier.weight(1f))
      title()
      Spacer(modifier = Modifier.weight(1f))
      Spacer(modifier = Modifier.size(16.dp))
      actionButton()
      Spacer(modifier = Modifier.size(16.dp))
    }
  }
}