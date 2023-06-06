package com.kreinto.chefico.views.account.blacklist

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.SubmitButton
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.components.inputs.TextInput
import com.kreinto.chefico.components.items.SwipeableItem
import com.kreinto.chefico.room.viewmodels.AuthViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun BlackListView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  var loading by remember { mutableStateOf(true) }
  var blackList by rememberSaveable { mutableStateOf(emptyList<Map.Entry<String, String>>()) }
  val context = LocalContext.current
  var openDialog by rememberSaveable { mutableStateOf(false) }
  LaunchedEffect(Unit) {
    authViewModel.getBlockedUsers({
      blackList = it.entries.toList()
      loading = false
    })
  }

  if (openDialog) {
    Dialog({ openDialog = false }) {
      var user by rememberSaveable { mutableStateOf("") }
      Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
          .padding(16.dp),
      ) {
        Text("Blocca utente", color = MaterialTheme.colorScheme.primary)
        TextInput(
          onValueChange = { user = it },
          singleLine = true,
          label = "ID utente",
          leadingIcon = { Icon(painterResource(R.drawable.ic_account), "account", Modifier.size(24.dp)) }
        )
        SubmitButton(
          enabled = user.isNotBlank(),
          text = "Blocca"
        ) {
          authViewModel.blockUser(user) {
            if (it) {
              Toast.makeText(context, "Utente bloccato", Toast.LENGTH_SHORT).show()
            } else {
              Toast.makeText(context, "Impossibile bloccare utente", Toast.LENGTH_SHORT).show()
            }
            openDialog = false
          }
        }
      }
    }
  }

  StandardFrame(
    onNavigate,
    { Text("Utenti bloccati") },
    bottomBar = {
      Row(
        modifier = Modifier
          .height(80.dp)
          .fillMaxWidth()
          .padding(BottomAppBarDefaults.ContentPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        SubmitButton("Blocca utente") {
          openDialog = true
        }
      }
    }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .padding(top = paddingValues.calculateTopPadding() + 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        .fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      items(blackList.size) { index ->
        SwipeableItem(
          icon = R.drawable.ic_account,
          text = blackList[index].value,
          actions = arrayOf({
            TransparentButton(R.drawable.ic_unlock, "Sblocca", MaterialTheme.colorScheme.primary) {
              authViewModel.unblockUser(blackList[index].key) {
                authViewModel.getBlockedUsers({ users ->
                  blackList = users.entries.toList()
                  loading = false
                })
                Toast.makeText(context, if (it) "Utente sbloccato" else "Impossibile sbloccare utente", Toast.LENGTH_SHORT).show()
              }
            }
          })
        )
      }
    }
  }
}
