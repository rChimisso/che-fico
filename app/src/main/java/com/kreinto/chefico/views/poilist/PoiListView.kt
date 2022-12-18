package com.kreinto.chefico.views.poilist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kreinto.chefico.AppRoute
import com.kreinto.chefico.components.data.ButtonData
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.components.inputs.SearchInput
import com.kreinto.chefico.components.items.SelectableItem
import com.kreinto.chefico.room.CheFicoViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun PoiListView(
  viewModel: CheFicoViewModel,
  onNavigate: (route: String) -> Unit
) {

  var selectedPoi = remember { mutableStateListOf<Int>() }
  var pois = viewModel.getPois().collectAsStateWithLifecycle(initialValue = emptyList())
  var filter: String by remember { mutableStateOf("") }


  StandardFrame(
    onClick = { onNavigate(AppRoute.Dashboard.route) },
    title = {
      SearchInput(onValueChange = { query -> filter = query })
    },
    bottomBar = {
      if (selectedPoi.size > 0) {
        SimpleBottomBar(
          leftButtonData = ButtonData(
            icon = Icons.Default.Delete,
            contentDescription = "Delete selected",
            tint = Color.Red
          ) {
            selectedPoi.forEach { id ->
              viewModel.deletePoi(id)
            }
            selectedPoi.clear()
          },
          rightButtonData = ButtonData(
            icon = Icons.Default.Share,
            contentDescription = "Share selected",
          ) {}
        )
      }
    }
  ) {
    LazyColumn(modifier = Modifier.padding(it)) {
      if (filter.isEmpty()) {
        items(pois.value.size) { index ->
          SelectableItem(
            icon = Icons.Default.Star,
            text = pois.value[index].name,
            selectable = selectedPoi.size > 0,
            onClick = {
              onNavigate(
                AppRoute.PoiDetail.route.replace(
                  "{${AppRoute.PoiDetail.arg}}",
                  pois.value[index].id.toString()
                )
              )
            },
            onLongClick = { selectedPoi.add(pois.value[index].id) },
            onCheckedChange = { checked ->
              if (checked) {
                selectedPoi.add(pois.value[index].id)
              } else {
                selectedPoi.removeIf { id -> id == pois.value[index].id }
              }
            }
          )
        }
      } else {
        var filteredPoi = pois.value.filter { poi -> poi.name.contains(filter) }
        items(filteredPoi.size) { index ->
          SelectableItem(
            icon = Icons.Default.Star,
            text = filteredPoi[index].name,
            selectable = selectedPoi.size > 0,
            onClick = { onNavigate(AppRoute.PoiDetail.route) },
            onLongClick = { selectedPoi.add(filteredPoi[index].id) },
            onCheckedChange = { checked ->
              if (checked) {
                selectedPoi.add(filteredPoi[index].id)
              } else {
                selectedPoi.removeIf { id -> id == filteredPoi[index].id }
              }
            }
          )
        }
      }
    }
  }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
@Preview
private fun PoiListViewPreview() {

}
