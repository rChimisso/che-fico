package com.kreinto.chefico.views.account.blacklist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.components.items.SwipeableItem
import com.kreinto.chefico.room.AuthViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun BlackListView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  var loading by remember { mutableStateOf(true) }
  var blackList by rememberSaveable {
    mutableStateOf(emptyList<Map.Entry<String, String>>())
  }
  LaunchedEffect(Unit) {
    authViewModel.getBlockedUsers({
      blackList = it.entries.toList()
      loading = false
    }, {})
  }

  StandardFrame(
    onNavPressed = onNavigate,
    title = { Text("Utenti bloccati") },
  ) {
    LazyColumn(
      modifier = Modifier
        .padding(top = it.calculateTopPadding())
        .fillMaxSize()
    ) {
      items(blackList.size) { index ->
        SwipeableItem(
          icon = R.drawable.ic_poi,
          text = blackList[index].value,
          actions = arrayOf({
            TransparentButton(
              icon = R.drawable.ic_snooze,
              contentDescription = "Sblocca",
              colors = IconButtonDefaults.iconButtonColors(
                contentColor = Color(0xFFFFC107)
              )
            ) { authViewModel.unblockUser(blackList[index].key) }
          }),
        ) {}
      }
    }
  }
}
