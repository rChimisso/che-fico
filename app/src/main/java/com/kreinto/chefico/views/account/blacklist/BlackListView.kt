package com.kreinto.chefico.views.account.blacklist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.components.inputs.SearchInput
import com.kreinto.chefico.components.items.SelectableItem
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.room.CheFicoViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.items.SwipeableItem

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun BlackListView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  var blackList by rememberSaveable {
    mutableStateOf(emptyList<Map.Entry<String, String>>())
  }
  LaunchedEffect(Unit){
    authViewModel.getBlockedUsers { blackList = it.entries.toList() }
  }

  val selectedAccounts = remember { mutableStateListOf<String>() }
  var filter: String by rememberSaveable { mutableStateOf("") }

  StandardFrame(
    onNavPressed = onNavigate,
    title = { SearchInput(onValueChange = { query -> filter = query }) },
  ) {
    LazyColumn(
      modifier = Modifier
        .padding(top = it.calculateTopPadding())
        .fillMaxHeight()
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
            ) {authViewModel.unblockUser(blackList[index].key)}
          }),
        ){}
      }
    }
  }
}
