package com.kreinto.chefico.views.account.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.R
import com.kreinto.chefico.views.account.AccountField

@Composable
internal fun AccountSigninContent(it: PaddingValues) {
  Column(
    modifier = Modifier
      .padding(paddingValues = it)
      .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Image(painter = painterResource(id = R.drawable.che_fico_icon), contentDescription = "", Modifier.size(64.dp))
    AccountField(R.drawable.ic_email, "Email") {}
    Spacer(modifier = Modifier.height(16.dp))
    AccountField(R.drawable.ic_lock, "Password") {}
    Spacer(modifier = Modifier.height(16.dp))
    AccountField(R.drawable.ic_lock, "Ripeti Password") {}
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
        Text("Registrati", fontSize = 16.sp, modifier = Modifier.align(Alignment.Center))
      }
    }

  }
}

