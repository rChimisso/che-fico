package com.kreinto.chefico.room

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.managers.SettingsManager
import com.kreinto.chefico.room.entities.Poi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

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

@Suppress("UNCHECKED_CAST")
class AuthViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: CheFicoRepository

  abstract class DatabaseDocument(val path: String)
  data class UserInfo(
    val uid: String,
    val username: String,
    val email: String,
    val photoUrl: Uri
  )

  init {
    val database = CheFicoDatabase.getInstance(application)
    repository = CheFicoRepository(
      database.poiDao(),
      database.notificationDao()
    )
  }

  private val auth = Firebase.auth
  private val db = Firebase.firestore

  private object BlockedUsers : DatabaseDocument("blocked_users")
  private object Pois : DatabaseDocument("pois")
  private object Info : DatabaseDocument("info")
  private object Settings : DatabaseDocument("backup_online")
  private object SharedPois : DatabaseDocument("shared_pois")

  private var blockedUsers: MutableMap<String, String> = mutableMapOf()
  private var currentUser: UserInfo? = null

  fun initAccount(user: FirebaseUser) {
    val collection = db.collection(user.uid)
    collection.document(Info.path).set(
      mapOf(
        "username" to user.displayName,
        "email" to user.email,
        "photoUrl" to user.photoUrl
      )
    )
    collection.document(BlockedUsers.path).set(
      mapOf(
        "data" to emptyList<String>()
      )
    )
    collection.document(Settings.path).set(
      mapOf(
        "backupOnline" to false,
        "lastUpdate" to Timestamp.now()
      )
    )
    collection.document(Pois.path).set(
      mapOf(
        "data" to emptyList<Poi>()
      )
    )
    collection.document(SharedPois.path).set(
      mapOf(
        "data" to emptyList<Poi>()
      )
    )
    currentUser = UserInfo(
      uid = user.uid,
      username = user.displayName!!,
      email = user.email!!,
      photoUrl = user.photoUrl ?: Uri.EMPTY
    )
  }

  fun getLastUpdate(onResult: (Timestamp) -> Unit) {
    if (isUserSignedIn()) {
      val request = db.collection(auth.currentUser!!.uid).document(Settings.path).get()
      request.addOnSuccessListener { document ->
        if (document.data != null && document.data!!["lastUpdate"] != null) {
          onResult(document.data!!["lastUpdate"] as Timestamp)
        }
      }
      request.addOnFailureListener {
        onResult(Timestamp(Date(0)))
      }
    }
  }

  fun createUser(email: String, password: String, username: String, onResult: (Boolean) -> Unit) {
    if (email.isNotBlank() && password.isNotBlank() && username.isNotBlank()) {
      val creationRequest = auth.createUserWithEmailAndPassword(email, password)
      creationRequest.addOnSuccessListener { result ->
        if (result.user != null) {
          val userProfileChangeRequest = result.user!!.updateProfile(
            UserProfileChangeRequest.Builder()
              .setDisplayName(username)
              .build()
          )
          userProfileChangeRequest.addOnSuccessListener {
            initAccount(result.user!!)
            onResult(true)
          }
          userProfileChangeRequest.addOnFailureListener {
            result.user!!.delete()
            onResult(false)
          }
        } else {
          onResult(false)
        }
      }
      creationRequest.addOnFailureListener {
        onResult(false)
      }
    } else {
      onResult(false)
    }
  }

  fun initGoogleAccount() {
    if (auth.currentUser != null) {
      initAccount(auth.currentUser!!)
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
          onSuccess((document.data!!["data"] as List<Map<String, Any>>).map {
            Poi(
              (it["id"]!! as Long).toInt(),
              it["name"]!! as String,
              it["description"]!! as String,
              it["image"]!! as String,
              it["latitude"]!! as Double,
              it["longitude"]!! as Double
            )
          })
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

  fun getBlockedUsers(onSuccess: (Map<String, String>) -> Unit, onFailure: () -> Unit = {}) {
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
        mapOf(
          "backupOnline" to value
        )
      )
    }
  }

  fun backup(onResult: (List<Poi>) -> Unit) = launch {
    if (auth.currentUser != null) {
      val settings = SettingsManager(getApplication())
      val localPois = repository.selectPois().first()
      val notifications = repository.selectNotifications().first()
      getLastUpdate { onlineLastUpdate ->
        if (onlineLastUpdate < Timestamp(settings.lastUpdate, 0)) {
          settings.lastUpdate = onlineLastUpdate.toDate().time
          getPois { onlinePois ->
            var toSave = onlinePois.toMutableList()
            localPois.forEach { localPoi ->
              toSave = toSave.filter {
                it.name != localPoi.name &&
                  it.image != localPoi.image &&
                  it.description != localPoi.description &&
                  it.latitude != localPoi.latitude &&
                  it.longitude != localPoi.longitude
              }.toMutableList()
            }
            toSave.addAll(localPois)
            db.collection(auth.currentUser!!.uid).document(Pois.path).set(
              mapOf(
                "data" to toSave.map { poi ->
                  mapOf(
                    "id" to poi.id,
                    "image" to poi.image,
                    "longitude" to poi.longitude,
                    "latitude" to poi.latitude,
                    "description" to poi.description,
                    "name" to poi.name,
                    "notifications" to notifications.filter { it.poiId == poi.id }
                  )
                }
              )
            ).addOnSuccessListener {
              onResult(toSave)
            }
          }
        }
      }
    }
  }


  fun share(user: String, vararg ids: Int, onResult: (Boolean) -> Unit) = launch {
    if (auth.currentUser != null) {
      val result = db.collection(user).document(SharedPois.path).get()
      val toShare = repository.selectPois(ids.toList()).first()
      result.addOnSuccessListener { document ->
        if (document.data != null) {
          val toSave = (document.data!!["data"] as List<Map<String, Any>>).toMutableList()
          toSave.addAll(toShare.map {
            mapOf(
              "name" to it.name,
              "image" to it.image,
              "description" to it.description,
              "latitude" to it.latitude,
              "longitude" to it.longitude,
              "id" to it.id
            )
          })
          val setData = db.collection(user).document(SharedPois.path).set(
            mapOf(
              "data" to toSave.toList()
            )
          )
          setData.addOnSuccessListener {
            onResult(true)
          }
          setData.addOnFailureListener {
            onResult(false)
          }
        }
      }
      result.addOnFailureListener {
        onResult(false)
      }
    }
  }

  private fun launch(block: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) { block() }
  }
}
