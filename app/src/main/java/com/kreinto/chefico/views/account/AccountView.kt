package com.kreinto.chefico.views.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.room.CheFicoViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun AccountView(onNavigate: (String) -> Unit, viewModel: CheFicoViewModel, authViewModel: AuthViewModel) {
  var loading by remember { mutableStateOf(true) }
  var backupOnline by rememberSaveable { mutableStateOf(false) }
  LaunchedEffect(Unit) {
    authViewModel.isOnlineBackupActive {
      loading = false
      backupOnline = it
    }
  }
  val coroutine = rememberCoroutineScope()
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
        Surface(
          tonalElevation = 12.dp,
          shape = CircleShape
        ) {
          AsyncImage(
            model = Firebase.auth.currentUser?.photoUrl ?: "",
            contentDescription = "",
            modifier = Modifier
              .size(128.dp)
              .clip(CircleShape)
          )
        }
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
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onNavigate(CheFicoRoute.AccountEdit.path) }
              .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) { Text(text = stringResource(R.string.edit_profile_label), modifier = Modifier.fillMaxWidth()) }
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { backupOnline = !backupOnline }
              .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = stringResource(R.string.backup_label))
            Switch(checked = backupOnline, onCheckedChange = { checked ->
              backupOnline = checked
              authViewModel.setOnlineBackup(checked)
              if (checked) {
                coroutine.launch {
                  authViewModel.backup(viewModel.getPois().first()) {
                    authViewModel.getPois { pois -> pois.forEach { poi -> viewModel.updatePoi(poi) } }
                  }
                }
              }
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
            onNavigate(CheFicoRoute.Settings.path)
          },
          contentPadding = ButtonDefaults.ButtonWithIconContentPadding
        ) { Text(stringResource(R.string.logout_label), color = Color.Red, textAlign = TextAlign.Center) }
      }
    }
  }
}
