package com.kreinto.chefico.views.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.room.AuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable

//bugg con l'icona di back, a volte funziona a volte no rimanendo nel loop del menu delle impostazioni e della view per registrarsi

fun SettinsView(authViewModel: AuthViewModel, onNavigate: (String) -> Unit) {
  SimpleFrame(
    onBackPressed = onNavigate,
    bottomBar = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Divider()
        ListItem(
          //list item per le info delll'app ecc... da mettere in fondo alla pagina
          headlineContent = { Text("INFO") },
        )
      }
    }
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
      //verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(modifier = Modifier.height(50.dp))
      ListItem(
        //singolo item
        headlineContent = {
          Button(
            onClick = {
              if (authViewModel.isUserLoggedIn()) {
                onNavigate(CheFicoRoute.Account.path)
              } else {
                onNavigate(CheFicoRoute.Login.path)
              }
            },
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
          ) {
            if (authViewModel.isUserLoggedIn()) {
              Text("Impostazioni Account")
            } else {
              Text("Registrati")
            }
          }
        },
      )
      Divider()//linea divisiva
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight(),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        ListItem(
          headlineContent = { Text("One ") },
          leadingContent = {
            Icon(
              Icons.Filled.Favorite,
              contentDescription = "Localized description",
            )
          }
        )
        ListItem(
          headlineContent = { Text("Two") },
          leadingContent = {
            Icon(
              Icons.Filled.Favorite,
              contentDescription = "Localized description",
            )
          }
        )
        ListItem(
          headlineContent = { Text("Three") },
          leadingContent = {
            Icon(
              Icons.Filled.Favorite,
              contentDescription = "Localized description",
            )
          }
        )
        ListItem(
          headlineContent = { Text("Four") },
          leadingContent = {
            Icon(
              Icons.Filled.Favorite,
              contentDescription = "Localized description",
            )
          }
        )

      }

    }
  }
}
