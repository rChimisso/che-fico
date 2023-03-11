package com.kreinto.chefico.views.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.R
import com.kreinto.chefico.Route
import com.kreinto.chefico.views.account.signin.GoogleSignInButton

@Composable
internal fun AccountLoginContent(paddingValues: PaddingValues, onNavigate: (String) -> Unit) {
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxSize()
      .padding(paddingValues)
  ) {
    Image(painter = painterResource(id = R.drawable.che_fico_icon), contentDescription = "", Modifier.size(156.dp))
    Spacer(modifier = Modifier.height(64.dp))
    GoogleSignInButton {

    }
    Spacer(modifier = Modifier.height(40.dp))
    Row(
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Divider(color = Color(0x6632C896), modifier = Modifier.width(128.dp))
      Spacer(modifier = Modifier.width(8.dp))
      Text("oppure", fontSize = 16.sp, color = Color(0xff32C896))
      Spacer(modifier = Modifier.width(8.dp))
      Divider(color = Color(0x6632C896), modifier = Modifier.width(128.dp))
    }
    Spacer(modifier = Modifier.height(32.dp))
    AccountField(R.drawable.ic_email, "Email") {}
    Spacer(modifier = Modifier.height(16.dp))
    AccountField(R.drawable.ic_lock, "Password") {}
    Spacer(modifier = Modifier.height(32.dp))
    Row(
      horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
    ) {
      Text("Password dimenticata?", fontSize = 16.sp, color = Color(0xff32C896))
      Spacer(modifier = Modifier.width(8.dp))
      ClickableText(
        AnnotatedString("Recupera password"),
        style = TextStyle(
          color = Color(0xff32C896),
          fontSize = 16.sp,


          )
      ) {}

    }
    Spacer(modifier = Modifier.height(32.dp))
    TextButton(
      colors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = Color.Black
      ),
      contentPadding = PaddingValues(0.dp),
      shape = RoundedCornerShape(12.dp),
      modifier = Modifier
        .width(208.dp)
        .height(40.dp),
      onClick = { /*TODO*/ }
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .clip(RoundedCornerShape(12.dp))
          .background(Brush.verticalGradient(listOf(Color(0xff32C896), Color(0x6632C896))))
      ) {
        Text("Accedi", fontSize = 16.sp, modifier = Modifier.align(Alignment.Center))
      }
    }
    Spacer(modifier = Modifier.height(16.dp))
    TextButton(
      colors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = Color(0xff32C896)
      ),
      contentPadding = PaddingValues(0.dp),
      shape = RoundedCornerShape(12.dp),
      modifier = Modifier
        .width(208.dp)
        .height(40.dp),
      onClick = { onNavigate(Route.Signin.path) },
    ) {
      Text("Registrati", fontSize = 16.sp)
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "Accedendo si accettano i termini e condizioni d'uso dell'applicazione.")
  }
}
