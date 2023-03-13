package com.kreinto.chefico.views.account.settings

import android.app.AlertDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun AccountSecurityView() {
  val context = LocalContext.current
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = Modifier
      .padding(32.dp)
      .fillMaxSize()
  ) {
    TextButton(
      onClick = {
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle("Attenzione!")
        alertBuilder.setMessage("L'azzione sarà irreversibile, procedere?")
        alertBuilder.setPositiveButton(
          "Elimina Account"
        ) { _, _ -> Firebase.auth.currentUser!!.delete() }
        alertBuilder.setNegativeButton(
          "Annulla"
        ) { _, _ -> }
        val alert = alertBuilder.create()
        alert.show()
      },
      modifier = Modifier
        .height(40.dp)
        .width(128.dp),
      colors = ButtonDefaults.textButtonColors(
        containerColor = Color.Red,
        contentColor = Color.White
      ),
      shape = RoundedCornerShape(12.dp)
    ) {
      Text("Elimina Account")
    }
  }
}