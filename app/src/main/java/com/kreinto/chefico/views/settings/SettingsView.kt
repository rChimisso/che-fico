package com.kreinto.chefico.views.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.FilledButton
import com.kreinto.chefico.components.frames.StandardFrame
import com.kreinto.chefico.components.items.MenuItem
import com.kreinto.chefico.managers.Language
import com.kreinto.chefico.managers.SettingsManager
import com.kreinto.chefico.managers.Theme
import com.kreinto.chefico.room.viewmodels.AuthViewModel
import com.kreinto.chefico.room.viewmodels.LocalViewModel
import com.kreinto.chefico.ui.theme.PaddingSmall

@ExperimentalMaterial3Api
@Composable
fun SettinsView(onNavigate: (String) -> Unit, viewModel: LocalViewModel, authViewModel: AuthViewModel) {
  val context = LocalContext.current
  val settingsManager = SettingsManager(context)
  val language by remember { mutableStateOf(Language.NAME[settingsManager.language]!!) }
  val themeOptions = mapOf(
    stringResource(R.string.dark) to Theme.DARK,
    stringResource(R.string.light) to Theme.LIGHT,
    stringResource(R.string.system) to Theme.SYSTEM
  )
  val (selectedTheme, onThemeSelected) = remember { mutableStateOf(settingsManager.theme) }
  var showMenu by remember { mutableStateOf(false) }
  val isUserSignedIn by remember { mutableStateOf(authViewModel.isUserSignedIn()) }

  LaunchedEffect(selectedTheme) { settingsManager.theme = selectedTheme }

  StandardFrame(
    onNavigate = onNavigate,
    title = { Text(stringResource(R.string.settings)) },
    actions = {
      if (isUserSignedIn) {
        FilledButton(R.drawable.che_fico_icon, R.string.settings) { onNavigate(CheFicoRoute.Account.path) }
      }
    }
  ) {
    Column(
      modifier = Modifier
        .padding(it)
        .fillMaxSize()
    ) {
      MenuItem(
        text = if (authViewModel.isUserSignedIn()) R.string.account_settings else R.string.login,
        onClick = {
          if (authViewModel.isUserSignedIn()) {
            onNavigate(CheFicoRoute.Account.path)
          } else {
            onNavigate(CheFicoRoute.Login.path)
          }
        }
      )
      MenuItem(R.string.language, { showMenu = true }) {
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
      MenuItem(R.string.theme) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(PaddingSmall),
          verticalAlignment = Alignment.CenterVertically
        ) {
          themeOptions.forEach { text ->
            RadioButton(text.value == selectedTheme, { onThemeSelected(text.value) })
            Text(text.key)
          }
        }
      }
      MenuItem(
        text = R.string.delete_notifications,
        isDanger = true,
        onClick = {
          val alertBuilder = android.app.AlertDialog.Builder(context)
          alertBuilder.setCancelable(true)
          alertBuilder.setTitle(R.string.confirm)
          alertBuilder.setPositiveButton(R.string.yes) { _, _ -> viewModel.deleteNotifications() }
          alertBuilder.setNegativeButton(R.string.no) { _, _ -> }
          alertBuilder.create().show()
        }
      )
    }
  }
}
