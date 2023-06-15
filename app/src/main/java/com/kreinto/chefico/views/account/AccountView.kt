package com.kreinto.chefico.views.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.SubmitButton
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.components.items.MenuItem
import com.kreinto.chefico.components.misc.Loader
import com.kreinto.chefico.room.viewmodels.AuthViewModel
import com.kreinto.chefico.ui.theme.CheFicoIconSize
import com.kreinto.chefico.ui.theme.PaddingLarge
import com.kreinto.chefico.ui.theme.PaddingSmall

@ExperimentalMaterial3Api
@Composable
fun AccountView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  var loading by remember { mutableStateOf(true) }
  var backupOnline by rememberSaveable { mutableStateOf(false) }
  LaunchedEffect(Unit) {
    authViewModel.isOnlineBackupActive {
      loading = false
      backupOnline = it
    }
  }
  if (!loading) {
    StandardFrame(
      onNavigate,
      title = { Text(authViewModel.currentUser?.displayName ?: stringResource(R.string.settings)) },
      bottomBar = {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
          SubmitButton(R.string.delete_account, textOnly = true, isDanger = true) {
            authViewModel.signOut()
            onNavigate(CheFicoRoute.Back.path)
          }
        }
      }
    ) { paddingValues ->
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(PaddingLarge),
        modifier = Modifier
          .padding(paddingValues)
          .fillMaxSize()
      ) {
        Image(
          painterResource(R.drawable.che_fico_icon),
          null,
          Modifier
            .size(CheFicoIconSize)
        )
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(PaddingSmall)
        ) {
          Text(Firebase.auth.currentUser?.email ?: "")
          SelectionContainer {
            Text(Firebase.auth.currentUser?.uid ?: "")
          }
        }
        Divider()
        Column(Modifier.fillMaxWidth()) {
          if (!authViewModel.isGoogleUserProvider()) {
            MenuItem(
              R.string.edit_account,
              onClick = { onNavigate(CheFicoRoute.AccountEdit.path) }
            )
          }
          MenuItem(R.string.backup) {
            Switch(
              checked = backupOnline,
              onCheckedChange = { checked ->
                backupOnline = checked
                authViewModel.setOnlineBackup(checked)
              }
            )
          }
          MenuItem(
            R.string.blocked_users,
            onClick = { onNavigate(CheFicoRoute.Blacklist.path) }
          )
          MenuItem(
            R.string.logout,
            isDanger = true,
            onClick = {
              authViewModel.signOut()
              onNavigate(CheFicoRoute.Back.path)
            }
          )
        }
      }
    }
  }
  Loader(loading)
}
