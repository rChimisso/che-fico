package com.kreinto.chefico.views.poilist

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.SubmitButton
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.components.inputs.SearchInput
import com.kreinto.chefico.components.inputs.TextInput
import com.kreinto.chefico.components.items.SelectableItem
import com.kreinto.chefico.room.viewmodels.AuthViewModel
import com.kreinto.chefico.room.viewmodels.LocalViewModel
import com.kreinto.chefico.ui.theme.IconSizeMedium
import com.kreinto.chefico.ui.theme.PaddingLarge
import com.kreinto.chefico.ui.theme.PaddingMedium

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
        verticalArrangement = Arrangement.spacedBy(PaddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .padding(PaddingLarge)
          .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small),
      ) {
        Text(stringResource(R.string.share), color = MaterialTheme.colorScheme.primary)
        TextInput(
          onValueChange = { user = it },
          label = R.string.user_id,
          leadingIcon = { Icon(painterResource(R.drawable.ic_account), null, Modifier.size(IconSizeMedium)) }
        )
        SubmitButton(R.string.share) {
          authViewModel.share(user, *selectedPois.toLongArray()) {
            Toast.makeText(context, if (it) R.string.share_success else R.string.share_failure, Toast.LENGTH_SHORT).show()
            openShareDialog = false
          }
        }
      }
    }
  }

  StandardFrame(
    onNavigate,
    { SearchInput { query -> filter = query } },
    bottomBar = {
      if (selectedPois.size > 0) {
        SimpleBottomBar(
          leftButtonData = ButtonData(R.drawable.ic_trash, R.string.del, MaterialTheme.colorScheme.error) {
            selectedPois.forEach { id -> viewModel.deletePoi(id) }
            selectedPois.clear()
          },
          rightButtonData = ButtonData(R.drawable.ic_share, R.string.share) {
            if (authViewModel.isUserSignedIn()) {
              openShareDialog = true
            } else {
              Toast.makeText(context, R.string.required_account, Toast.LENGTH_SHORT).show()
            }
          }
        )
      }
    }
  ) {
    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(PaddingMedium),
      modifier = Modifier
        .padding(top = it.calculateTopPadding())
        .fillMaxHeight()
        .padding(PaddingLarge),
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
