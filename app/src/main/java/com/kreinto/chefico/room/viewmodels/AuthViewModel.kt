package com.kreinto.chefico.room.viewmodels

import android.app.Application
import android.net.Uri
import com.google.firebase.Timestamp
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.room.entities.Poi
import kotlinx.coroutines.flow.first
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
class AuthViewModel(application: Application) : CheFicoViewModel(application) {
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
  private object SharedPois : DatabaseDocument("shared_pois")

  private var blockedUsers: MutableMap<String, String> = mutableMapOf()
  val currentUser: FirebaseUser?
    get() {
      return auth.currentUser
    }

  private fun initAccount(user: FirebaseUser) {
    val collection = db.collection(user.uid)
    collection.document(Info.path).set(mapOf("username" to user.displayName, "email" to user.email, "photoUrl" to user.photoUrl))
    collection.document(BlockedUsers.path).set(mapOf("data" to emptyList<String>()))
    collection.document(Settings.path).set(mapOf("backupOnline" to false, "lastUpdate" to Timestamp.now()))
    collection.document(Pois.path).set(mapOf("data" to emptyList<Poi>()))
    collection.document(SharedPois.path).set(mapOf("data" to emptyList<Poi>()))
  }

  fun getLastUpdate(onResult: (Timestamp) -> Unit) {
    if (isUserSignedIn()) {
      val request = db.collection(currentUser!!.uid).document(Settings.path).get()
      request.addOnSuccessListener { document ->
        if (document.data != null && document.data!!["lastUpdate"] != null) {
          onResult(document.data!!["lastUpdate"] as Timestamp)
        }
      }
      request.addOnFailureListener { onResult(Timestamp(Date(0))) }
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
      creationRequest.addOnFailureListener { onResult(false) }
    } else {
      onResult(false)
    }
  }

  fun isGoogleUserProvider(): Boolean {
    if (currentUser != null) {
      for (provider in currentUser!!.providerData) {
        if (provider.providerId == "google.com") {
          return true
        }
      }
    }
    return false
  }

  fun updateUserInfo(currPassword: String, email: String, newPassword: String, username: String, onResult: (Boolean) -> Unit) {
    if (currentUser != null && (email.isNotBlank() || newPassword.isNotBlank() || username.isNotBlank())) {
      val reauthenticate = currentUser!!.reauthenticate(EmailAuthProvider.getCredential(currentUser!!.email!!, currPassword))
      reauthenticate.addOnSuccessListener {
        if (email.isNotBlank()) {
          currentUser!!.updateEmail(email)
        }
        if (newPassword.isNotBlank()) {
          currentUser!!.updatePassword(newPassword)
        }
        if (username.isNotBlank()) {
          currentUser!!.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(username).build())
        }
        onResult(true)
      }
      reauthenticate.addOnFailureListener {
        onResult(false)
      }
    } else {
      onResult(false)
    }
  }

  fun initGoogleAccount(isNewUser: Boolean) {
    if (currentUser != null && isNewUser) {
      initAccount(currentUser!!)
    }
  }

  fun isUserSignedIn(): Boolean {
    return currentUser != null
  }

  fun signIn(email: String, password: String, onSuccess: (UserInfo) -> Unit, onFailure: () -> Unit) {
    val request = auth.signInWithEmailAndPassword(email, password)
    request.addOnSuccessListener {
      onSuccess(UserInfo(it.user!!.uid, it.user!!.displayName!!, it.user!!.email!!, it.user!!.photoUrl ?: Uri.EMPTY))
    }
    request.addOnFailureListener {
      onFailure()
    }
  }

  fun signOut() {
    auth.signOut()
  }

  fun getPois(onSuccess: (List<Poi>) -> Unit) {
    if (currentUser != null) {
      db.collection(currentUser!!.uid).document(Pois.path).get().addOnSuccessListener { document ->
        if (document.data != null && document.data!!["data"] != null) {
          onSuccess((document.data!!["data"] as List<Map<String, Any>>).map {
            Poi(
              it["name"]!! as String,
              it["description"]!! as String,
              it["image"]!! as String,
              it["latitude"]!! as Double,
              it["longitude"]!! as Double,
              it["id"]!! as Long,
            )
          })
        }
      }
    }
  }

  fun getSharedPois(onSuccess: (List<Poi>) -> Unit) {
    if (currentUser != null) {
      db.collection(currentUser!!.uid).document(SharedPois.path).get().addOnSuccessListener { document ->
        if (document.data != null && document.data!!["data"] != null) {
          onSuccess((document.data!!["data"] as List<Map<String, Any>>).map {
            Poi(
              it["name"]!! as String,
              it["description"]!! as String,
              it["image"]!! as String,
              it["latitude"]!! as Double,
              it["longitude"]!! as Double,
              it["id"]!! as Long
            )
          })
        }
      }
    }
  }

  fun getBlockedUsers(onSuccess: (Map<String, String>) -> Unit, onFailure: () -> Unit = {}) {
    if (currentUser != null) {
      val result = db.collection(currentUser!!.uid).document(BlockedUsers.path).get()
      result.addOnSuccessListener {
        blockedUsers = it.data as MutableMap<String, String>
        onSuccess(blockedUsers)
      }
      result.addOnFailureListener { onFailure() }
    }
  }

  fun getUserInfo(uid: String, onResult: (UserInfo) -> Unit) {
    db.collection(uid).document(Info.path).get().addOnCompleteListener {
      onResult(
        UserInfo(it.result["uid"] as String, it.result["username"] as String, it.result["email"] as String, it.result["photoUrl"] as Uri)
      )
    }
  }

  fun blockUser(uid: String) {
    if (uid.isNotBlank() && currentUser != null) {
      getUserInfo(uid) {
        blockedUsers.putIfAbsent(uid, it.username)
      }
    }
  }

  fun unblockUser(uid: String) {
    if (uid.isNotBlank() && currentUser != null) {
      blockedUsers.remove(uid)
    }
  }

  fun isOnlineBackupActive(onResult: (Boolean) -> Unit) {
    if (currentUser != null) {
      db.collection(currentUser!!.uid).document(Settings.path).get().addOnSuccessListener {
        onResult(it.data?.get("backupOnline") as Boolean)
      }
    }
  }

  fun setOnlineBackup(value: Boolean) {
    if (currentUser != null) {
      db.collection(currentUser!!.uid).document(Settings.path).update("backupOnline", value)
    }
  }

  fun sync() = launch {
    if (currentUser != null) {
      println(currentUser!!.uid)
      val localPois = repository.selectPois().first().sortedBy { it.id }
      val notifications = repository.selectNotifications().first()
      getLastUpdate { lastUpdate ->
        getPois { onlinePois ->
          val localLastUpdate = Timestamp(settings.lastUpdate, 0)
          val pois = localPois.toMutableList()
          val timestamp = Timestamp.now()
          if (lastUpdate >= localLastUpdate) {
            for (poi in onlinePois) {
              val index = pois.indexOfFirst { it.id == poi.id }
              if (index >= 0) {
                pois[index] = poi
                launch { repository.updatePoi(poi) }
              } else {
                pois.add(poi)
                launch { repository.insertPoi(poi) }
              }
            }
          }
          settings.lastUpdate = timestamp.seconds
          getSharedPois { sharedPois ->
            launch {
              for (poi in sharedPois) {
                val sharedPoi = Poi(poi.name, poi.description, poi.image, poi.latitude, poi.longitude)
                repository.insertPoi(sharedPoi)
                pois.add(sharedPoi)
              }
              db.collection(currentUser!!.uid).document(SharedPois.path).set(mapOf("data" to listOf<Poi>())).addOnSuccessListener {
                db.collection(currentUser!!.uid).document(Settings.path).update("lastUpdate", timestamp)
              }
              db.collection(currentUser!!.uid).document(Pois.path).set(mapOf("data" to pois.map { poi ->
                mapOf(
                  "id" to poi.id,
                  "image" to poi.image,
                  "longitude" to poi.longitude,
                  "latitude" to poi.latitude,
                  "description" to poi.description,
                  "name" to poi.name,
                  "notifications" to notifications.filter { it.poiId == poi.id }
                )
              })).addOnSuccessListener {
                db.collection(currentUser!!.uid).document(Settings.path).update("lastUpdate", timestamp)
              }
            }
          }
        }
      }
    }
  }

  fun share(user: String, vararg ids: Long, onResult: (Boolean) -> Unit) = launch {
    if (currentUser != null) {
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
          val setData = db.collection(user).document(SharedPois.path).set(mapOf("data" to toSave.toList()))
          setData.addOnSuccessListener {
            db.collection(user).document(Settings.path).update("lastUpdate", Timestamp.now())
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
}
