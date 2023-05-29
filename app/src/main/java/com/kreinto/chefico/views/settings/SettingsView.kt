package com.kreinto.chefico.views.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.*
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.FilledButton
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.room.AuthViewModel
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun SettinsView(onNavigate: (String) -> Unit, authViewModel: AuthViewModel) {
  val context = LocalContext.current
  val settingsManager = SettingsManager(context)
  var language by remember { mutableStateOf(settingsManager.getLanguage()) }
  val themeOptions = mapOf(
    Theme.DARK to stringResource(R.string.dark_label),
    Theme.LIGHT to stringResource(R.string.light_label),
    Theme.SYSTEM to stringResource(R.string.defaulf_label)
  )
  val (selectedTheme, onThemeSelected) = remember { mutableStateOf(themeOptions[settingsManager.getTheme()]) }
  var automaticDeletion by remember { mutableStateOf(settingsManager.getAutoDeleteNotification()) }
  var showMenu by remember { mutableStateOf(false) }
  LaunchedEffect(selectedTheme) {
    when (selectedTheme) {
      themeOptions[Theme.LIGHT] -> settingsManager.useLightTheme()
      themeOptions[Theme.DARK] -> settingsManager.useDarkTheme()
      themeOptions[Theme.SYSTEM] -> settingsManager.useSystemTheme()
    }
  }
  val isUserSignedIn by remember { mutableStateOf(authViewModel.isUserSignedIn()) }

  StandardFrame(
    onNavPressed = onNavigate,
    title = {
      Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (isUserSignedIn) {
          Text("${Firebase.auth.currentUser?.displayName}")
        } else {
          Text(text = stringResource(R.string.settings_label))
        }
      }
    },
    actions = {
      if (isUserSignedIn) {
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
        Text(text = stringResource(R.string.privacy_label))
        Text(text = stringResource(R.string.dot_spacer_label))
        Text(text = stringResource(R.string.copyright_label))
        Text(text = stringResource(R.string.dot_spacer_label))
        Text(text = stringResource(R.string.version_label))
      }
    }
  ) {

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
        Text(text = stringResource(R.string.auto_delete_label))
        Switch(checked = automaticDeletion, onCheckedChange = { checked ->
          automaticDeletion = checked
          settingsManager.setAutoDeleteNotification(checked)
        })
      }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable {
            val alertBuilder = android.app.AlertDialog.Builder(context)
            alertBuilder.setCancelable(true)
            alertBuilder.setTitle(R.string.delete_notifications_title_label)
            alertBuilder.setMessage(R.string.delete_message_label)
            alertBuilder.setPositiveButton(R.string.positive_label) { _, _ -> }
            alertBuilder.setNegativeButton(R.string.negative_label) { _, _ -> }
            val alert = alertBuilder.create()
            alert.show()
          }
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = stringResource(R.string.delete_notifications_label), modifier = Modifier.fillMaxWidth())
      }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { showMenu = true }
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = stringResource(R.string.lenguage_label))
        Box {
          Text(text = language, modifier = Modifier.align(Alignment.Center))
          DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
            DropdownMenuItem(text = { Text(text = stringResource(R.string.it_label)) }, onClick = {
              showMenu = false
              language = "Italiano"
              settingsManager.setLanguage(Language.Italian)
            })
            DropdownMenuItem(text = { Text(text = stringResource(R.string.en_label)) }, onClick = {
              showMenu = false
              language = "English"
              settingsManager.setLanguage(Language.English)
            })
          }
        }

      }


      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(IntrinsicSize.Min)
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Text(text = stringResource(R.string.theme_label))
        }
        Row(
          modifier = Modifier
            .fillMaxHeight(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          themeOptions.forEach { text ->
            RadioButton(
              selected = (text.value == selectedTheme),
              onClick = {
                onThemeSelected(text.value)
              }
            )
            Text(
              text = text.value,
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
            Text(text = stringResource(R.string.account_settings_label))
          } else {
            Text(text = stringResource(R.string.signup_or_login_label))
          }
        }
      }
    }
  }
}

