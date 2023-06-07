package com.kreinto.chefico.views.account.blacklist

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.SubmitButton
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.components.inputs.TextInput
import com.kreinto.chefico.components.items.SwipeableItem
import com.kreinto.chefico.room.viewmodels.AuthViewModel
import com.kreinto.chefico.ui.theme.IconSizeMedium
import com.kreinto.chefico.ui.theme.InteractSizeLarge
import com.kreinto.chefico.ui.theme.PaddingLarge

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

  val blockUserSuccessMessage = stringResource(R.string.block_user_success)
  val blockUserFailureMessage = stringResource(R.string.block_user_failure)
  val unlockUserSuccessMessage = stringResource(R.string.unlock_user_success)
  val unlockUserFailureMessage = stringResource(R.string.unlock_user_failure)

  if (openDialog) {
    Dialog({ openDialog = false }) {
      var user by rememberSaveable { mutableStateOf("") }
      Column(
        verticalArrangement = Arrangement.spacedBy(PaddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.extraSmall)
          .padding(PaddingLarge),
      ) {
        Text(stringResource(R.string.block_user), color = MaterialTheme.colorScheme.primary)
        TextInput(
          onValueChange = { user = it },
          singleLine = true,
          label = R.string.user_id,
          leadingIcon = { Icon(painterResource(R.drawable.ic_account), null, Modifier.size(IconSizeMedium)) }
        )
        SubmitButton(
          enabled = user.isNotBlank(),
          text = R.string.block
        ) {
          authViewModel.blockUser(user) {
            Toast.makeText(
              context,
              if (it) blockUserSuccessMessage else blockUserFailureMessage,
              Toast.LENGTH_SHORT
            ).show()
            openDialog = false
          }
        }
      }
    }
  }

  StandardFrame(
    onNavigate,
    { Text(stringResource(R.string.blocked_users)) },
    bottomBar = {
      Row(
        modifier = Modifier
          .height(InteractSizeLarge)
          .fillMaxWidth()
          .padding(BottomAppBarDefaults.ContentPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        SubmitButton(R.string.block_user) {
          openDialog = true
        }
      }
    }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .padding(top = paddingValues.calculateTopPadding() + PaddingLarge, start = PaddingLarge, end = PaddingLarge, bottom = PaddingLarge)
        .fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(PaddingLarge),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      items(blackList.size) { index ->
        SwipeableItem(
          icon = R.drawable.ic_account,
          text = blackList[index].value,
          actions = arrayOf({
            TransparentButton(R.drawable.ic_unlock, R.string.unlock_user, MaterialTheme.colorScheme.primary) {
              authViewModel.unblockUser(blackList[index].key) {
                authViewModel.getBlockedUsers({ users ->
                  blackList = users.entries.toList()
                  loading = false
                })
                Toast.makeText(context, if (it) unlockUserSuccessMessage else unlockUserFailureMessage, Toast.LENGTH_SHORT).show()
              }
            }
          })
        )
      }
    }
  }
}
