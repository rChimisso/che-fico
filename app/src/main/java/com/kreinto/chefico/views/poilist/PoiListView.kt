package com.kreinto.chefico.views.poilist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kreinto.chefico.R
import com.kreinto.chefico.Route
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.components.inputs.SearchInput
import com.kreinto.chefico.components.items.SelectableItem
import com.kreinto.chefico.room.CheFicoViewModel

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun PoiListView(
  onNavigate: (String) -> Unit,
  viewModel: CheFicoViewModel
) {
  val pois = viewModel.getPois().collectAsStateWithLifecycle(emptyList())
  val selectedPois = remember { mutableStateListOf<Int>() }
  var filter: String by rememberSaveable { mutableStateOf("") }

  StandardFrame(
    onNavPressed = onNavigate,
    title = { SearchInput(onValueChange = { query -> filter = query }) },
    bottomBar = {
      if (selectedPois.size > 0) {
        SimpleBottomBar(
          leftButtonData = ButtonData(
            icon = R.drawable.ic_trash,
            contentDescription = "Delete selected",
            colors = IconButtonDefaults.filledIconButtonColors(
              contentColor = Color.Red
            )
          ) {
            selectedPois.forEach { id -> viewModel.deletePoi(id) }
            selectedPois.clear()
          },
          rightButtonData = ButtonData(
            icon = R.drawable.ic_share,
            contentDescription = "Share selected",
          ) {
            selectedPois.clear()
          }
        )
      }
    }
  ) {
    LazyColumn(
      modifier = Modifier
        .padding(top = it.calculateTopPadding())
        .fillMaxHeight()
    ) {
      if (filter.isEmpty()) {
        items(pois.value.size) { index ->
          SelectableItem(
            icon = Icons.Default.Star,
            text = pois.value[index].name,
            selectable = selectedPois.size > 0,
            onClick = { onNavigate(Route.PoiDetail.route(pois.value[index].id.toString())) },
            onLongClick = { selectedPois.add(pois.value[index].id) },
            onCheckedChange = { checked ->
              if (checked) {
                selectedPois.add(pois.value[index].id)
              } else {
                selectedPois.removeIf { id -> id == pois.value[index].id }
              }
            }
          )
        }
      } else {
        val filteredPoi = pois.value.filter { poi -> poi.name.contains(filter) }
        items(filteredPoi.size) { index ->
          SelectableItem(
            icon = Icons.Default.Star,
            text = filteredPoi[index].name,
            selectable = selectedPois.size > 0,
            onClick = { onNavigate(Route.PoiDetail.path) },
            onLongClick = { selectedPois.add(filteredPoi[index].id) },
            onCheckedChange = { checked ->
              if (checked) {
                selectedPois.add(filteredPoi[index].id)
              } else {
                selectedPois.removeIf { id -> id == filteredPoi[index].id }
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
