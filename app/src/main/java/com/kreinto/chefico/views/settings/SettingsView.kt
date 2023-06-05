package com.kreinto.chefico.views.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.FilledButton
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.managers.Language
import com.kreinto.chefico.managers.SettingsManager
import com.kreinto.chefico.managers.Theme
import com.kreinto.chefico.room.viewmodels.AuthViewModel
import com.kreinto.chefico.room.viewmodels.LocalViewModel

@ExperimentalMaterial3Api
@Composable
fun SettinsView(onNavigate: (String) -> Unit, viewModel: LocalViewModel, authViewModel: AuthViewModel) {
  val context = LocalContext.current
  val settingsManager = SettingsManager(context)
  val language by remember { mutableStateOf(Language.NAME[settingsManager.language]!!) }
  val themeOptions = mapOf(
    stringResource(R.string.dark_theme) to Theme.DARK,
    stringResource(R.string.light_theme) to Theme.LIGHT,
    stringResource(R.string.system_theme) to Theme.SYSTEM
  )
  val (selectedTheme, onThemeSelected) = remember { mutableStateOf(settingsManager.theme) }
  var automaticDeletion by remember { mutableStateOf(settingsManager.autoDeleteNotifications) }
  var showMenu by remember { mutableStateOf(false) }
  val isUserSignedIn by remember { mutableStateOf(authViewModel.isUserSignedIn()) }

  LaunchedEffect(selectedTheme) { settingsManager.theme = selectedTheme }

  StandardFrame(
    onNavigate = onNavigate,
    title = { Text(if (isUserSignedIn) "${Firebase.auth.currentUser?.displayName}" else stringResource(R.string.settings_label)) },
    actions = {
      if (isUserSignedIn) {
        FilledButton(R.drawable.che_fico_icon, "") { onNavigate(CheFicoRoute.Account.path) }
      }
    },
    bottomBar = {
      val lineColor = MaterialTheme.colorScheme.onBackground
      Row(
        modifier = Modifier
          .height(80.dp)
          .fillMaxWidth()
          .drawBehind { drawLine(color = lineColor, start = Offset.Zero, end = Offset(size.width, 0f)) }
          .padding(BottomAppBarDefaults.ContentPadding),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(stringResource(R.string.privacy))
        Text(stringResource(R.string.dot_spacer))
        Text(stringResource(R.string.copyright))
        Text(stringResource(R.string.dot_spacer))
        Text(stringResource(R.string.version))
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
          .height(64.dp)
          .clickable { automaticDeletion = !automaticDeletion }
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(stringResource(R.string.auto_delete))
        Switch(
          checked = automaticDeletion,
          onCheckedChange = { checked ->
            automaticDeletion = checked
            settingsManager.autoDeleteNotifications = checked
          }
        )
      }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(64.dp)
          .clickable {
            val alertBuilder = android.app.AlertDialog.Builder(context)
            alertBuilder.setCancelable(true)
            alertBuilder.setTitle(R.string.delete_notifications_title)
            alertBuilder.setMessage(R.string.delete_notifications_message)
            alertBuilder.setPositiveButton(R.string.yes) { _, _ -> viewModel.deleteNotifications() }
            alertBuilder.setNegativeButton(R.string.no) { _, _ -> }
            alertBuilder
              .create()
              .show()
          }
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) { Text(stringResource(R.string.delete_notifications), Modifier.fillMaxWidth()) }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(64.dp)
          .clickable { showMenu = true }
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(stringResource(R.string.lenguage))
        Box {
          Text(language, Modifier.align(Alignment.Center))
          DropdownMenu(showMenu, { showMenu = false }) {
            DropdownMenuItem(
              { Text(stringResource(R.string.italian)) },
              {
                showMenu = false
                settingsManager.language = Language.ITALIAN
              }
            )
            DropdownMenuItem(
              { Text(stringResource(R.string.english)) },
              {
                showMenu = false
                settingsManager.language = Language.ENGLISH
              }
            )
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
        ) { Text(stringResource(R.string.theme)) }
        Row(
          modifier = Modifier.fillMaxHeight(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          themeOptions.forEach { text ->
            RadioButton(text.value == selectedTheme, { onThemeSelected(text.value) })
            Text(text.key)
            Spacer(Modifier.width(8.dp))
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
          onClick = { onNavigate(if (authViewModel.isUserSignedIn()) CheFicoRoute.Account.path else CheFicoRoute.Login.path) },
          contentPadding = ButtonDefaults.TextButtonContentPadding
        ) {
          Text(
            stringResource(if (authViewModel.isUserSignedIn()) R.string.account_settings else R.string.login),
            fontSize = 18.sp
          )
        }
      }
    }
  }
}
