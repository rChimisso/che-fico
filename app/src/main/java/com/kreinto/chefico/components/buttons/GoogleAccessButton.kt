package com.kreinto.chefico.components.buttons

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.R
import com.kreinto.chefico.ui.theme.*

/**
 * Button to access a Che Fico! account via Google.
 *
 * @param onSuccess Function called when the authentication succeeds, with whether the account is new or existing.
 */
@Composable
fun GoogleAccessButton(onSuccess: (Boolean) -> Unit) {
  val context = LocalContext.current
  val oneTapClient = Identity.getSignInClient(context)
  val signInRequest =
    BeginSignInRequest
      .builder()
      .setGoogleIdTokenRequestOptions(
        BeginSignInRequest
          .GoogleIdTokenRequestOptions
          .builder()
          .setSupported(true)
          .setServerClientId(stringResource(R.string.default_web_client_id))
          .setFilterByAuthorizedAccounts(false)
          .build()
      )
      .setAutoSelectEnabled(false)
      .build()
  val googleAccessErrorMessage = stringResource(R.string.access_error)

  val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
    val googleCredential = oneTapClient.getSignInCredentialFromIntent(result.data)
    val idToken = googleCredential.googleIdToken
    if (result.resultCode == Activity.RESULT_OK) {
      Firebase.auth.signInWithCredential(GoogleAuthProvider.getCredential(idToken, null)).addOnSuccessListener {
        onSuccess(it.additionalUserInfo?.isNewUser ?: true)
      }
    } else {
      Toast.makeText(context, googleAccessErrorMessage, Toast.LENGTH_SHORT).show()
    }
  }
  Button(
    onClick = {
      oneTapClient.beginSignIn(signInRequest).addOnCompleteListener {
        launcher.launch(IntentSenderRequest.Builder(it.result.pendingIntent).build())
      }
    },
    modifier = Modifier
      .padding(GoogleButtonPadding)
      .width(WideButtonWidth)
      .height(InteractSizeMedium),
    shape = MaterialTheme.shapes.small,
    colors = ButtonDefaults.buttonColors(Color.White, Color.Black),
    contentPadding = PaddingValues(PaddingNone, PaddingNone, PaddingMedium, PaddingNone)
  ) {
    Image(painterResource(R.drawable.ic_google_logo), stringResource(R.string.google_access))
    Text(stringResource(R.string.google_access), fontSize = LabelMedium)
  }
}

/**
 * [Preview] for [GoogleAccessButton].
 */
@Composable
@Preview
private fun GoogleAccessButtonPreview() {
  GoogleAccessButton {}
}
