package com.kreinto.chefico.views.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.room.AuthViewModel

@ExperimentalMaterial3Api
@Composable
fun AccountView(authViewModel: AuthViewModel, onNavigate: (String) -> Unit) {
  var backupOnline by rememberSaveable { mutableStateOf(false) }
  SimpleFrame(
    onBackPressed = onNavigate,
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier
        .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp, bottom = 16.dp)
        .fillMaxSize()
    ) {
      Spacer(modifier = Modifier.height(16.dp))
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
      Spacer(modifier = Modifier.height(16.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(Firebase.auth.currentUser?.displayName ?: "")
        Text("#000000")
      }
      Text(Firebase.auth.currentUser?.email ?: "")
      Spacer(modifier = Modifier.height(16.dp))
      Divider()
      Spacer(modifier = Modifier.height(16.dp))
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clickable {
              onNavigate(CheFicoRoute.AccountEdit.path)
            }
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("Modifica Informazioni", modifier = Modifier.fillMaxWidth())
        }
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { backupOnline = !backupOnline }
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("BackUp Online")
          Switch(checked = backupOnline, onCheckedChange = { checked -> backupOnline = checked })
        }
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clickable {

            }
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("Visualizza Condivisioni", modifier = Modifier.fillMaxWidth())
        }
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clickable {

            }
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("Utenti Bloccati", modifier = Modifier.fillMaxWidth())
        }
      }

      Button(
        onClick = {
          authViewModel.logOut()
          onNavigate(CheFicoRoute.Settings.path)
        },
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
      ) {
        Text("Esci dall'account", color = Color.Red)
      }
    }
  }
}

object AccountRoute {
  const val Detail = "detail"
  const val Security = "security"
  const val Notification = "notification"

}