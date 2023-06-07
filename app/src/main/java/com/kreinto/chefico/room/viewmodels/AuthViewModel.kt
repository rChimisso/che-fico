package com.kreinto.chefico.room.viewmodels

import android.app.Application
import com.google.firebase.Timestamp
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kreinto.chefico.room.entities.Poi
import kotlinx.coroutines.flow.first
import java.util.*

/**
 * Extends [CheFicoViewModel], handles auth operations.
 *
 * @param application
 */
@Suppress("UNCHECKED_CAST")
class AuthViewModel(application: Application) : CheFicoViewModel(application) {
  /**
   * Utility for application defined database document.
   *
   * @property path
   */
  sealed class Document(val path: String) {
    object BlockedUsers : Document("blocked_users")
    object Pois : Document("pois")
    object Info : Document("info")
    object Settings : Document("backup_online")
    object SharedPois : Document("shared_pois")
  }

  private val auth = Firebase.auth
  private val db = Firebase.firestore

  /**
   * Current user logged in.
   */
  val currentUser: FirebaseUser?
    get() {
      return auth.currentUser
    }

  /**
   * Initializes newly created account.
   *
   * @param user
   */
  private fun initAccount(user: FirebaseUser) {
    val collection = db.collection(user.uid)
    collection.document(Document.Info.path).set(mapOf("username" to user.displayName, "email" to user.email, "photoUrl" to user.photoUrl))
    collection.document(Document.BlockedUsers.path).set(mapOf("data" to emptyList<String>()))
    collection.document(Document.Settings.path).set(mapOf("backupOnline" to false, "lastUpdate" to Timestamp.now()))
    collection.document(Document.Pois.path).set(mapOf("data" to emptyList<Poi>()))
    collection.document(Document.SharedPois.path).set(mapOf("data" to emptyList<Poi>()))
  }

  /**
   * Gets latest [Timestamp] from cloud.
   *
   * @param onResult
   */
  private fun getLastUpdate(onResult: (Timestamp) -> Unit) {
    if (isUserSignedIn()) {
      val request = db.collection(currentUser!!.uid).document(Document.Settings.path).get()
      request.addOnSuccessListener { document ->
        if (document.data != null && document.data!!["lastUpdate"] != null) {
          onResult(document.data!!["lastUpdate"] as Timestamp)
        }
      }
      request.addOnFailureListener { onResult(Timestamp(Date(0))) }
    }
  }

  /**
   * Creates a new user.
   *
   * @param email
   * @param password
   * @param username
   * @param onResult
   */
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

  /**
   * Checks if current user is signed in with Google.
   *
   * @return [Boolean]
   */
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

  /**
   * Updates user's info.
   *
   * @param currPassword
   * @param email
   * @param newPassword
   * @param username
   * @param onResult
   */
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

  /**
   * Initializes account creted via Google.
   *
   * @param isNewUser
   */
  fun initGoogleAccount(isNewUser: Boolean) {
    if (currentUser != null && isNewUser) {
      initAccount(currentUser!!)
    }
  }

  /**
   * Checks if current user exists.
   *
   * @return [Boolean]
   */
  fun isUserSignedIn(): Boolean {
    return currentUser != null
  }

  /**
   * Tries account login.
   *
   * @param email
   * @param password
   * @param onResult
   */
  fun signIn(email: String, password: String, onResult: (Boolean) -> Unit) {
    val request = auth.signInWithEmailAndPassword(email, password)
    request.addOnSuccessListener {
      onResult(true)
    }
    request.addOnFailureListener {
      onResult(false)
    }
  }

  /**
   * Logs out current user.
   */
  fun signOut() {
    auth.signOut()
  }

  /**
   * Retrieves List<[Poi]> from cloud.
   *
   * @param onSuccess
   */
  private fun getPois(onSuccess: (List<Poi>) -> Unit) {
    if (currentUser != null) {
      db.collection(currentUser!!.uid).document(Document.Pois.path).get().addOnSuccessListener { document ->
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

  /**
   * Retrieves shared List<[Poi]> from cloud.
   *
   * @param onSuccess
   */
  private fun getSharedPois(onSuccess: (List<Poi>) -> Unit) {
    if (currentUser != null) {
      db.collection(currentUser!!.uid).document(Document.SharedPois.path).get().addOnSuccessListener { document ->
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

  /**
   * Retrieves blocked users from cloud.
   *
   * @param onSuccess
   * @param onFailure
   */
  fun getBlockedUsers(onSuccess: (Map<String, String>) -> Unit, onFailure: () -> Unit = {}) {
    if (currentUser != null) {
      val result = db.collection(currentUser!!.uid).document(Document.BlockedUsers.path).get()
      result.addOnSuccessListener {
        onSuccess(it.data!!["data"] as Map<String, String>)
      }
      result.addOnFailureListener { onFailure() }
    }
  }

  /**
   * Blocks user.
   *
   * @param uid
   * @param onResult
   */
  fun blockUser(uid: String, onResult: (Boolean) -> Unit) {
    if (uid.isNotBlank() && currentUser != null) {
      val userInfo = db.collection(uid).document(Document.Info.path).get()
      userInfo.addOnSuccessListener {
        if (it.data != null) {
          val request =
            db.collection(currentUser!!.uid).document(Document.BlockedUsers.path).update("data", mapOf(uid to it.data!!["username"]))
          request.addOnSuccessListener { onResult(true) }
          request.addOnFailureListener { onResult(false) }
        } else {
          onResult(false)
        }
      }
      userInfo.addOnFailureListener { onResult(false) }
    } else {
      onResult(false)
    }
  }

  /**
   * Unblocks user.
   *
   * @param uid
   * @param onResult
   */
  fun unblockUser(uid: String, onResult: (Boolean) -> Unit) {
    if (uid.isNotBlank() && currentUser != null) {
      val request = db.collection(currentUser!!.uid).document(Document.BlockedUsers.path).update("data.$uid", FieldValue.delete())
      request.addOnSuccessListener { onResult(true) }
      request.addOnFailureListener { onResult(false) }
    }
  }

  /**
   * Checks if user allowed oline backup.
   *
   * @param onResult
   */
  fun isOnlineBackupActive(onResult: (Boolean) -> Unit) {
    if (currentUser != null) {
      db.collection(currentUser!!.uid).document(Document.Settings.path).get().addOnSuccessListener {
        onResult(it.data?.get("backupOnline") as Boolean)
      }
    }
  }

  /**
   * Updates online backup flag.
   *
   * @param value
   */
  fun setOnlineBackup(value: Boolean) {
    if (currentUser != null) {
      db.collection(currentUser!!.uid).document(Document.Settings.path).update("backupOnline", value)
    }
  }

  /**
   * Syncs data with cloud.
   */
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
                pois.add(
                  repository.selectPoi(repository.insertPoi(Poi(poi.name, poi.description, poi.image, poi.latitude, poi.longitude))).first()
                )
              }
              db.collection(currentUser!!.uid).document(Document.SharedPois.path).set(mapOf("data" to listOf<Poi>())).addOnSuccessListener {
                db.collection(currentUser!!.uid).document(Document.Settings.path).update("lastUpdate", timestamp)
              }
              db.collection(currentUser!!.uid).document(Document.Pois.path).set(mapOf("data" to pois.map { poi ->
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
                db.collection(currentUser!!.uid).document(Document.Settings.path).update("lastUpdate", timestamp)
              }
            }
          }
        }
      }
    }
  }

  /**
   * Shares [Poi] to given [user].
   *
   * @param user
   * @param ids
   * @param onResult
   */
  fun share(user: String, vararg ids: Long, onResult: (Boolean) -> Unit) = launch {
    if (currentUser != null) {
      val result = db.collection(user).document(Document.SharedPois.path).get()
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
          val setData = db.collection(user).document(Document.SharedPois.path).set(mapOf("data" to toSave.toList()))
          setData.addOnSuccessListener {
            db.collection(user).document(Document.Settings.path).update("lastUpdate", Timestamp.now())
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
