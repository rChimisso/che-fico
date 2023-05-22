package com.kreinto.chefico.views.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.FilledButton
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.room.AuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun SettinsView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  val context = LocalContext.current
  var language by remember { mutableStateOf("Italiano") }
  StandardFrame(
    onNavPressed = onNavigate,
    title = {
      Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (authViewModel.isUserSignedIn()) {
          // TODO: cambiare recupero nome utente e visualizzazione ID (#&&&&&& tipo discord).
          Text("${Firebase.auth.currentUser?.displayName}")
        } else {
          Text("Impostazioni")
        }
      }
    },
    actions = {
      if (authViewModel.isUserSignedIn()) {
        FilledButton(
          icon = R.drawable.che_fico_icon,
          contentDescription = ""
        ) {
          onNavigate(CheFicoRoute.Account.path)
        }
      }
    },
    bottomBar = {
      Row(
        modifier = Modifier
          .height(80.dp)
          .fillMaxWidth()
          .drawBehind {
            drawLine(color = Color.White, start = Offset.Zero, end = Offset(size.width, 0f))
          }
          .padding(BottomAppBarDefaults.ContentPadding),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("Privacy")
        Text("·")
        Text("Copyright")
        Text("·")
        Text("Versione")
      }
    }
  ) {
    var automaticDeletion by rememberSaveable { mutableStateOf(false) }
    var showMenu by rememberSaveable { mutableStateOf(false) }
    Column(
      modifier = Modifier
        .padding(top = it.calculateTopPadding())
        .fillMaxSize()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { automaticDeletion = !automaticDeletion }
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("Autoeliminazione notifiche (30gg)")
        Switch(checked = automaticDeletion, onCheckedChange = { checked -> automaticDeletion = checked })
      }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable {
            val alertBuilder = android.app.AlertDialog.Builder(context)
            alertBuilder.setCancelable(true)
            alertBuilder.setTitle("Eliminazione Notifiche")
            alertBuilder.setMessage("Vuoi eliminare tutte le notifiche?")
            alertBuilder.setPositiveButton("Sì") { _, _ -> }
            alertBuilder.setNegativeButton("No") { _, _ -> }
            val alert = alertBuilder.create()
            alert.show()
          }
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("Elimina notifiche", modifier = Modifier.fillMaxWidth())
      }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { showMenu = true }
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("Lingua")
        Box {
          Text(text = "${language}", modifier = Modifier.align(Alignment.Center))
          DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
            DropdownMenuItem(text = { Text("Italiano") }, onClick = { language = "Italiano" })
            DropdownMenuItem(text = { Text("Inglese") }, onClick = { language = "Inglese" })
          }
        }

      }

      val radioOptions = listOf("Chi", "Scu", "Def")
      val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[2]) }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(IntrinsicSize.Min)
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(
//          modifier = Modifier
//            .fillMaxHeight(),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Text("Tema:")
        }
        Row(
          modifier = Modifier
            .fillMaxHeight(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          radioOptions.forEach { text ->
            RadioButton(
              selected = (text == selectedOption),
              onClick = { onOptionSelected(text) }
            )
            Text(
              text = text,
            )
            Spacer(modifier = Modifier.width(16.dp))
          }
        }
      }
      Row(
        modifier = Modifier
          .fillMaxSize()
          .padding(bottom = it.calculateBottomPadding() + 16.dp, start = 16.dp, top = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
      ) {
        Button(
          onClick = {
            if (authViewModel.isUserSignedIn()) {
              onNavigate(CheFicoRoute.Account.path)
            } else {
              onNavigate(CheFicoRoute.Login.path)
            }
          },
          contentPadding = ButtonDefaults.ButtonWithIconContentPadding
        ) {
          if (authViewModel.isUserSignedIn()) {
            Text("Impostazioni Account")
          } else {
            Text("Registrati")
          }
        }
      }
    }
  }
}
