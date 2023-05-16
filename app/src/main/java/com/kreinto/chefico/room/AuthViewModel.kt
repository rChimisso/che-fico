package com.kreinto.chefico.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow

/**
A client for the sign-in API.
The Sign-In APIs can be used for both sign-in and sign-up scenarios. The two scenarios share the same flow in the code, but different BeginSignInRequest should be provided in different scenarios.
The Sign-In APIs guide the user through credential selection before returning an instance of SignInCredential containing the data for sign-in or sign-up.
The recommended process for retrieving credentials using this API is as follows:
Get a new API client instance by calling Identity.getSignInClient.
Call SignInClient.beginSignIn, supplying the constructed BeginSignInRequest as an input.
If the request is successful,
at least one matching credential is available.
Launch the PendingIntent from the result of the operation to display
the UI that guides the user through sign-in.
The result of sign-in will be returned in Activity.onActivityResult;
calling SignInClient.getSignInCredentialFromIntent will either return the SignInCredential if the operation was successful,
or throw an ApiException that indicates the reason for failure.
If the request is unsuccessful, no matching credential was found on the device that can be used to sign the user in. No further action needs to be taken.
When the user signs out of your application, please make sure to call SignInClient.signOut.
The usage of BeginSignInRequest
Different BeginSignInRequest should be used for sign-in and sign-up.
Sign an existing user in
Two types of credentials are supported in SignInCredential: Google ID token and password. To give users more options to choose from when selecting a credential to sign in with, and by extension, increase your app's sign-in rate, it is strongly recommended that applications support both Google ID token and password credentials:
If your application supports username/password login, configure an instance of PasswordRequestOptions in the request.
If your application supports federated sign-in using Google ID tokens, configure an instance of GoogleIdTokenRequestOptions accordingly - be sure to supply your server client ID (you can find this in your Google API console project).
For the sign-in scenario, it is strongly recommended to set GoogleIdTokenRequestOptions.Builder.setFilterByAuthorizedAccounts to true so only the Google accounts that the user has authorized before will show up in the credential list. This can help prevent a new account being created when the user has an existing account registered with the application.
 */

open class AuthViewModel(application: Application) : AndroidViewModel(application) {
  private val auth = Firebase.auth
  private var database = Firebase.firestore

  private val blockedUsersDocumentPath = "blocked_users"

  val user = MutableStateFlow(auth.currentUser)

  fun signIn(email: String, password: String, name: String, onResult: (Throwable?) -> Unit) {
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
      task.result.user!!.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())
      onResult(task.exception)
    }
  }

  fun logIn(email: String, password: String, onResult: (Throwable?) -> Unit) {
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
      onResult(task.exception)
    }
  }

  fun isUserLoggedIn(): Boolean {
    return auth.currentUser != null
  }

  fun logOut() {
    auth.signOut()
  }

  fun getUserProviderIds(): List<String> {
    var providersId = mutableListOf<String>()
    if (auth.currentUser != null) {
      auth.currentUser!!.providerData.forEach { data ->
        providersId.add(data.providerId)
      }
    }
    return providersId
  }


  fun getBlockedUsers(onResult: (List<String>) -> Unit) {
    var blockedUsers = ArrayList<String>()
    if (isUserLoggedIn()) {
      database.collection(auth.currentUser!!.uid).document(blockedUsersDocumentPath).get().addOnCompleteListener {
        if (it.result.data != null) {
          blockedUsers = it.result.data!!["users"] as ArrayList<String>
        }
      }
    }
    onResult(blockedUsers)
  }

  fun blockUser(user: String) {
    if (user.isNotEmpty()) {
      if (isUserLoggedIn()) {
        getBlockedUsers {
          var blockedUsers = ArrayList<String>(it)
          if (!blockedUsers.contains(user)) {
            blockedUsers.add(user)
          }
          database.collection(auth.currentUser!!.uid).document(blockedUsersDocumentPath).set(blockedUsers)
        }
      }
    }
  }

  fun unblockUser(user: String) {
    if (user.isNotEmpty()) {
      if (isUserLoggedIn()) {
        getBlockedUsers {
          var blockedUsers = ArrayList<String>(it)
          val index = blockedUsers.indexOf(user)
          if (index != -1) {
            blockedUsers.removeAt(index)
          }
          database.collection(auth.currentUser!!.uid).document(blockedUsersDocumentPath).set(blockedUsers)
        }
      }
    }
  }
}
