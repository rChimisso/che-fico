package com.kreinto.chefico.views.account.sharings

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

class Sharing {
  val name : String = "pippo"
  val id : Int = 1
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun SharingsView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  val sharing = arrayOf(Sharing (), Sharing (), Sharing ())
  val selectedSharing = arrayListOf(1, 2, 3)
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
        items(sharing.size) { index ->
          SelectableItem(
            icon = R.drawable.ic_poi,
            text = sharing[index].name,
            selectable = selectedSharing.isNotEmpty(),
            onClick = { onNavigate(CheFicoRoute.PoiDetail.path(sharing[index].id.toString())) },
            onLongClick = { selectedSharing.add(sharing[index].id) },
            onCheckedChange = { checked ->
              if (checked) {
                selectedSharing.add(sharing[index].id)
              } else {
                selectedSharing.removeIf { id -> id == sharing[index].id }
              }
            }
          )
        }
      } else {
        val filteredPoi = sharing.filter { poi -> poi.name.contains(filter) }
        items(filteredPoi.size) { index ->
          SelectableItem(
            icon = R.drawable.ic_poi,
            text = filteredPoi[index].name,
            selectable = selectedSharing.size > 0,
            onClick = { onNavigate(CheFicoRoute.PoiDetail.path) },
            onLongClick = { selectedSharing.add(filteredPoi[index].id) },
            onCheckedChange = { checked ->
              if (checked) {
                selectedSharing.add(filteredPoi[index].id)
              } else {
                selectedSharing.removeIf { id -> id == filteredPoi[index].id }
              }
            }
          )
        }
      }
    }
  }
}
