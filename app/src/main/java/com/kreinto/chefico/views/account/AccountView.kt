package com.kreinto.chefico.views.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.views.account.settings.AccountDetailView
import com.kreinto.chefico.views.account.settings.AccountNotificationView
import com.kreinto.chefico.views.account.settings.AccountSecurityView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountView(authViewModel: AuthViewModel, onNavigate: (String) -> Unit) {
  Scaffold(
    topBar = {
      TopAppBar(
        colors = topAppBarColors(Color.Transparent),
        title = { Text("Impostazioni") },
        navigationIcon = {
          IconButton(onClick = { onNavigate(CheFicoRoute.Settings.path) }) {
            Icon(painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = "Back")
          }
        }
      )
    },
    content = { paddingValues ->
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
          .padding(top = paddingValues.calculateTopPadding(), start = 16.dp, end = 16.dp, bottom = 16.dp)
          .fillMaxSize()
      ) {
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
          Text("Ciao, ${Firebase.auth.currentUser?.displayName}!")
        }
        Spacer(modifier = Modifier.height(16.dp))
        val navController = rememberNavController()
        val currentRoute = navController.currentBackStackEntryAsState()
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextButton(
            onClick = { navController.navigate(AccountRoute.Detail) },
            modifier = Modifier
              .height(40.dp)
              .weight(1f),
            colors = ButtonDefaults.textButtonColors(
              containerColor = if (currentRoute.value?.destination?.route == AccountRoute.Detail) Color(0x6632C896) else Color.Transparent,
              contentColor = Color(0xff32C896),

              ),
            shape = RoundedCornerShape(12.dp)

          ) {
            Text("Dettagli")
          }
          TextButton(
            onClick = { navController.navigate(AccountRoute.Security) },
            modifier = Modifier
              .height(40.dp)
              .weight(1f),
            colors = ButtonDefaults.textButtonColors(
              containerColor = if (currentRoute.value?.destination?.route == AccountRoute.Security) Color(0x6632C896) else Color.Transparent,
              contentColor = Color(0xff32C896)
            ),
            shape = RoundedCornerShape(12.dp)

          ) {
            Text("Sicurezza")
          }
          TextButton(
            onClick = { navController.navigate(AccountRoute.Notification) },
            modifier = Modifier
              .height(40.dp)
              .weight(1f),
            colors = ButtonDefaults.textButtonColors(
              containerColor = if (currentRoute.value?.destination?.route == AccountRoute.Notification) Color(0x6632C896) else Color.Transparent,
              contentColor = Color(0xff32C896)
            ),
            shape = RoundedCornerShape(12.dp)
          ) {
            Text("Notifiche")
          }

        }
        NavHost(
          navController = navController,
          startDestination = AccountRoute.Detail,
          modifier = Modifier.weight(1f)
        ) {
          composable(
            route = AccountRoute.Detail,
            content = { AccountDetailView(authViewModel) }
          )
          composable(
            route = AccountRoute.Security,
            content = { AccountSecurityView() }
          )
          composable(
            route = AccountRoute.Notification,
            content = { AccountNotificationView() }
          )
        }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
          onClick = {
            authViewModel.logOut()
            onNavigate(CheFicoRoute.Settings.path)
          }) {
          Text("Esci dall'account", color = Color.Red)
        }

      }
    }
  )

}

object AccountRoute {
  const val Detail = "detail"
  const val Security = "security"
  const val Notification = "notification"

}