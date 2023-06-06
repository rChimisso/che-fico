package com.kreinto.chefico.views.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.misc.Loader
import com.kreinto.chefico.room.viewmodels.AuthViewModel

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
    SimpleFrame(onNavigate) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
          .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp, bottom = 16.dp)
          .fillMaxSize()
      ) {
        Spacer(Modifier.height(16.dp))
        Image(painterResource(R.drawable.che_fico_icon), null, Modifier.size(128.dp))
        Spacer(Modifier.height(16.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) { Text(Firebase.auth.currentUser?.displayName ?: "") }
        Text(Firebase.auth.currentUser?.email ?: "")
        Spacer(Modifier.height(16.dp))
        Divider()
        Spacer(Modifier.height(16.dp))
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {
          if (!authViewModel.isGoogleUserProvider()) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigate(CheFicoRoute.AccountEdit.path) }
                .padding(16.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) { Text(stringResource(R.string.edit_profile_label), Modifier.fillMaxWidth()) }
          }
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { backupOnline = !backupOnline }
              .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(stringResource(R.string.backup_label))
            Switch(checked = backupOnline, onCheckedChange = { checked ->
              backupOnline = checked
              authViewModel.setOnlineBackup(checked)
            })
          }
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onNavigate(CheFicoRoute.Blacklist.path) }
              .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) { Text(stringResource(R.string.see_blacklist_label), Modifier.fillMaxWidth()) }
        }
        Button(
          onClick = {
            authViewModel.signOut()
            onNavigate(CheFicoRoute.Back.path)
          },
          contentPadding = ButtonDefaults.TextButtonContentPadding
        ) { Text(stringResource(R.string.logout_label), color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center) }
      }
    }
  }
  Loader(loading)
}
