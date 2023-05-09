package com.kreinto.chefico.views.account.blacklist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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

class BlackList {
  val name : String = "pippo"
  val id : Int = 1
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun BlackListView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  val blackList = arrayOf(BlackList (), BlackList (), BlackList ())
  val selectedBlackList = arrayListOf(1, 2, 3)
//  val sharing = authViewModel.getSharings().collectAsStateWithLifecycle(emptyList()) //<- Da implementare la query getSharings
//  val selectedPois = remember { mutableStateListOf<Int>() }
  var filter: String by rememberSaveable { mutableStateOf("") }

  SimpleFrame(
    onBackPressed = onNavigate
  ) {
    LazyColumn(
      modifier = Modifier
        .padding(top = it.calculateTopPadding())
        .fillMaxHeight()
    ) {
      if (filter.isEmpty()) {
        items(blackList.size) { index ->
          SelectableItem(
            icon = R.drawable.ic_poi,
            text = blackList[index].name,
            selectable = selectedBlackList.isNotEmpty(),
            onClick = { onNavigate(CheFicoRoute.PoiDetail.path(blackList[index].id.toString())) },
            onLongClick = { selectedBlackList.add(blackList[index].id) },
            onCheckedChange = { checked ->
              if (checked) {
                selectedBlackList.add(blackList[index].id)
              } else {
                selectedBlackList.removeIf { id -> id == blackList[index].id }
              }
            }
          )
        }
      } else {
        val filteredPoi = blackList.filter { poi -> poi.name.contains(filter) }
        items(filteredPoi.size) { index ->
          SelectableItem(
            icon = R.drawable.ic_poi,
            text = filteredPoi[index].name,
            selectable = selectedBlackList.size > 0,
            onClick = { onNavigate(CheFicoRoute.PoiDetail.path) },
            onLongClick = { selectedBlackList.add(filteredPoi[index].id) },
            onCheckedChange = { checked ->
              if (checked) {
                selectedBlackList.add(filteredPoi[index].id)
              } else {
                selectedBlackList.removeIf { id -> id == filteredPoi[index].id }
              }
            }
          )
        }
      }
    }
  }
}
