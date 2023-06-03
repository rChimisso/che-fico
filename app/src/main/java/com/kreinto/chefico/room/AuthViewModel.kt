package com.kreinto.chefico.room

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.room.entities.Poi

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

class AuthViewModel(application: Application) : AndroidViewModel(application) {
  abstract class DatabaseDocument(val path: String)
  data class UserInfo(
    val uid: String,
    val username: String,
    val email: String,
    val photoUrl: Uri
  )

  private val auth = Firebase.auth
  private val db = Firebase.firestore

  private object BlockedUsers : DatabaseDocument("blocked_users")
  private object Pois : DatabaseDocument("pois")
  private object Info : DatabaseDocument("info")
  private object Settings : DatabaseDocument("backup_online")

  private var blockedUsers: MutableMap<String, String> = mutableMapOf()
  private var currentUser: UserInfo? = null

  fun createUser(email: String, password: String, username: String, onResult: () -> Unit) {
    val context = getApplication<Application>().applicationContext
    if (email.isNotBlank() && password.isNotBlank() && username.isNotBlank()) {
      val request = auth.createUserWithEmailAndPassword(email, password)

      request.addOnSuccessListener {
        if (it.user != null) {
          val collection = db.collection(it.user!!.uid)
          collection.document(Info.path).set(
            object {
              val username = username
              val email = it.user!!.email
              val photoUrl = it.user!!.photoUrl
            }
          )
          collection.document(BlockedUsers.path).set(emptyMap<String, String>())
          collection.document(Settings.path).set(
            object {
              val backupOnline = false
            }
          )
          collection.document(Pois.path).set(
            object {
              val data = emptyList<Poi>()
            }
          )
          currentUser = UserInfo(
            uid = it.user!!.uid,
            username = username,
            email = it.user!!.email!!,
            photoUrl = it.user!!.photoUrl ?: Uri.EMPTY
          )
          it.user!!.updateProfile(
            UserProfileChangeRequest.Builder()
              .setDisplayName(username)
              .build()
          ).addOnCompleteListener {
            onResult()
          }
        }
      }
      request.addOnFailureListener {
        Toast.makeText(context, "Registrazione fallita", Toast.LENGTH_SHORT).show()
      }
    } else {
      Toast.makeText(context, "Campi non validi", Toast.LENGTH_SHORT).show()
    }
  }

  fun initGoogleAccount() {
    if (auth.currentUser != null) {
      val collection = Firebase.firestore.collection(auth.currentUser!!.uid)
      collection.document(Info.path).set(
        object {
          val username = auth.currentUser!!.displayName
          val email = auth.currentUser!!.email
          val photoUrl = auth.currentUser!!.photoUrl
        }
      )
      collection.document(BlockedUsers.path).set(emptyMap<String, String>())
      collection.document(Settings.path).set(
        object {
          val backupOnline = false
        }
      )
      collection.document(Pois.path).set(
        object {
          val data = emptyList<Poi>()
        }
      )
      currentUser = UserInfo(
        uid = auth.currentUser!!.uid,
        username = auth.currentUser!!.displayName!!,
        email = auth.currentUser!!.email!!,
        photoUrl = auth.currentUser!!.photoUrl ?: Uri.EMPTY
      )
    }
  }

  fun isUserSignedIn(): Boolean {
    return auth.currentUser != null
  }

  fun signIn(email: String, password: String, onSuccess: (UserInfo) -> Unit, onFailure: () -> Unit) {
    val request = auth.signInWithEmailAndPassword(email, password)
    request.addOnSuccessListener {
      onSuccess(
        UserInfo(
          uid = it.user!!.uid,
          username = it.user!!.displayName!!,
          email = it.user!!.email!!,
          photoUrl = it.user!!.photoUrl ?: Uri.EMPTY
        )
      )
    }
    request.addOnFailureListener {
      onFailure()
    }
  }

  fun signOut() {
    auth.signOut()
  }

  fun getPois(onSuccess: (List<Poi>) -> Unit) {
    if (auth.currentUser != null) {
      db.collection(auth.currentUser!!.uid).document(Pois.path).get().addOnSuccessListener { document ->
        if (document.data != null && document.data!!["data"] != null) {
          val pois = mutableListOf<Poi>()
          (document.data!!["data"] as List<Map<String, Any>>).forEach {
            pois.add(
              Poi(
                (it["id"]!! as Long).toInt(),
                it["name"]!! as String,
                it["description"]!! as String,
                it["image"]!! as String,
                it["latitude"]!! as Double,
                it["longitude"]!! as Double
              )
            )
          }
          onSuccess(pois)
        }
      }
    }
  }

  fun getUserProviderIds(): List<String> {
    val providersId = mutableListOf<String>()
    if (auth.currentUser != null) {
      auth.currentUser!!.providerData.forEach { data ->
        providersId.add(data.providerId)
      }
    }
    return providersId
  }

  fun getBlockedUsers(onSuccess: (Map<String, String>) -> Unit, onFailure: () -> Unit) {
    if (auth.currentUser != null) {
      val result = db.collection(auth.currentUser!!.uid).document(BlockedUsers.path).get()
      result.addOnSuccessListener {
        blockedUsers = it.data as MutableMap<String, String>
        onSuccess(blockedUsers)
      }
      result.addOnFailureListener {
        onFailure()
      }
    }
  }

  fun getUserInfo(uid: String, onResult: (UserInfo) -> Unit) {
    db.collection(uid).document(Info.path).get().addOnCompleteListener {
      onResult(
        UserInfo(
          uid = it.result["uid"] as String,
          username = it.result["username"] as String,
          email = it.result["email"] as String,
          photoUrl = it.result["photoUrl"] as Uri
        )
      )
    }
  }

  fun blockUser(uid: String) {
    if (uid.isNotBlank() && auth.currentUser != null) {
      getUserInfo(uid) {
        blockedUsers.putIfAbsent(uid, it.username)
      }
    }
  }

  fun unblockUser(uid: String) {
    if (uid.isNotBlank() && auth.currentUser != null) {
      blockedUsers.remove(uid)
    }
  }

  fun isOnlineBackupActive(onResult: (Boolean) -> Unit) {
    if (auth.currentUser != null) {
      val result = db.collection(auth.currentUser!!.uid).document(Settings.path).get()
      result.addOnSuccessListener {
        onResult(it.data?.get("backupOnline") as Boolean)
      }
    }
  }

  fun setOnlineBackup(value: Boolean) {
    if (auth.currentUser != null) {
      db.collection(auth.currentUser!!.uid).document(Settings.path).set(
        object {
          val backupOnline = value
        }
      )
    }
  }

  fun backup(pois: List<Poi>, onResult: () -> Unit) {
    if (auth.currentUser != null) {
      getPois { onlinePois ->
        var toSave = onlinePois.toMutableList()
        pois.forEach { localPoi ->
          toSave = toSave.filter {
            it.name != localPoi.name &&
              it.image != localPoi.image &&
              it.description != localPoi.description &&
              it.latitude != localPoi.latitude &&
              it.longitude != localPoi.longitude
          }.toMutableList()
        }
        toSave.addAll(pois)
        db.collection(auth.currentUser!!.uid).document(Pois.path).set(
          object {
            val data = toSave
          }
        ).addOnSuccessListener {
          onResult()
        }
      }
    }
  }

  fun share(user: String, poi: Poi) {
    val context = getApplication<Application>().applicationContext
    var pois: MutableList<Map<String, Any>>
    if (auth.currentUser != null) {
      val result = db.collection(user).document(Pois.path).get()
      result.addOnSuccessListener {
        if (it.data != null) {
          pois = (it.data!!["data"] as List<Map<String, Any>>).toMutableList()
          pois = pois.filter {
            it["name"] != poi.name &&
              it["image"] != poi.image &&
              it["description"] != poi.description &&
              it["latitude"] != poi.latitude &&
              it["longitude"] != poi.longitude
          }.toMutableList()
          pois.add(
            mapOf<String, Any>(
              "name" to poi.name,
              "image" to poi.image,
              "description" to poi.description,
              "latitude" to poi.latitude,
              "longitude" to poi.longitude,
              "id" to poi.id
            )
          )
          db.collection(user).document(Pois.path).set(
            object {
              val data = pois.toList()
            }
          ).addOnFailureListener {
            Toast.makeText(context, "Qualcosa è andato storto con la condivisione", Toast.LENGTH_SHORT).show()
          }
        }
      }
      result.addOnFailureListener {
        Toast.makeText(context, "Qualcosa è andato storto con la condivisione", Toast.LENGTH_SHORT).show()
      }
    }
  }
}
