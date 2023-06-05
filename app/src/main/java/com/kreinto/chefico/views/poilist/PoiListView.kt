package com.kreinto.chefico.views.poilist

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.components.inputs.SearchInput
import com.kreinto.chefico.components.items.SelectableItem
import com.kreinto.chefico.room.viewmodels.AuthViewModel
import com.kreinto.chefico.room.viewmodels.LocalViewModel

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun PoiListView(onNavigate: (String) -> Unit, viewModel: LocalViewModel, authViewModel: AuthViewModel) {
  val context = LocalContext.current
  val pois = viewModel.getPois().collectAsStateWithLifecycle(emptyList())
  val selectedPois = remember { mutableStateListOf<Long>() }
  var filter: String by rememberSaveable { mutableStateOf("") }
  var openShareDialog by remember { mutableStateOf(false) }
  var user by remember { mutableStateOf("") }
  if (openShareDialog) {
    Dialog({ openShareDialog = false }) {
      Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp)),
      ) {
        Text("Condividi", color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 16.dp))
        TextField(
          modifier = Modifier.padding(16.dp),
          value = user, onValueChange = { user = it },
          singleLine = true,
          placeholder = { Text("ID utente") },
          enabled = true,
          readOnly = false,
          leadingIcon = { Icon(painterResource(R.drawable.ic_account), "account", Modifier.size(24.dp)) }
        )
        TextButton(
          enabled = user.isNotBlank(),
//          colors = ButtonDefaults.buttonColors(
//            containerColor = MaterialTheme.colorScheme.primary,
//            contentColor = MaterialTheme.colorScheme.onPrimary
//          ),
          contentPadding = PaddingValues(0.dp),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .padding(bottom = 16.dp)
            .width(208.dp)
            .height(40.dp),
          onClick = {
            authViewModel.share(user, *selectedPois.toLongArray()) {
              Toast.makeText(context, if (it) "Condivisione riuscita" else "Condivisione non riuscita", Toast.LENGTH_SHORT).show()
              openShareDialog = false
            }
          }
        ) { Text("Condividi", fontSize = 16.sp) }
      }
    }
  }

  StandardFrame(
    onNavigate,
    { SearchInput { query -> filter = query } },
    bottomBar = {
      if (selectedPois.size > 0) {
        SimpleBottomBar(
          leftButtonData = ButtonData(R.drawable.ic_trash, "Delete selected", MaterialTheme.colorScheme.error) {
            selectedPois.forEach { id -> viewModel.deletePoi(id) }
            selectedPois.clear()
          },
          rightButtonData = ButtonData(R.drawable.ic_share, "Share selected") {
            openShareDialog = true
          }
        )
      }
    }
  ) {
    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier
        .padding(top = it.calculateTopPadding())
        .fillMaxHeight()
        .padding(16.dp),
    ) {
      if (filter.isEmpty()) {
        items(pois.value.size) { index ->
          SelectableItem(
            icon = R.drawable.ic_poi,
            text = pois.value[index].name,
            selectable = selectedPois.size > 0,
            onClick = { onNavigate(CheFicoRoute.PoiDetail.path(pois.value[index].id.toString())) },
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
        val filteredPoi = pois.value.filter { poi -> poi.name.lowercase().contains(filter.lowercase()) }
        items(filteredPoi.size) { index ->
          SelectableItem(
            icon = R.drawable.ic_poi,
            text = filteredPoi[index].name,
            selectable = selectedPois.size > 0,
            onClick = { onNavigate(CheFicoRoute.PoiDetail.path) },
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
