package com.kreinto.chefico.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.kreinto.chefico.room.entities.Notification
import com.kreinto.chefico.room.entities.Poi
import com.kreinto.chefico.room.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class CheFicoViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: CheFicoRepository
  private val mapBoundariesFlow = MutableStateFlow(
    LatLngBounds(
      LatLng(0.0, 0.0),
      LatLng(0.0, 0.0)
    )
  )

  @ExperimentalCoroutinesApi
  val poisWithin = mapBoundariesFlow.flatMapLatest { selectPoisWithin(it) }

  private val creatingPoiFlow = MutableStateFlow(Poi.NullPoi)
  private var user = MutableStateFlow(User.NullUser)

  init {
    val database = CheFicoDatabase.getInstance(application)
    repository = CheFicoRepository(
      database.notificationDao(),
      database.poiDao(),
      database.userDao()
    )
  }

  fun addPoi(poi: Poi) = launch {
    if (poi != Poi.NullPoi) {
      repository.insertPoi(poi)
    }
  }

  fun addNotification(notification: Notification) = launch {
    repository.insertNotification(notification)
  }

  fun addUser(user: User) = launch {
    repository.insertUser(user)
  }

  fun updatePoi(poi: Poi) = launch {
    repository.updatePoi(poi)
  }

  fun getPoi(id: Int): Flow<Poi> {
    return repository.selectPoi(id)
  }

  fun getPois(): Flow<List<Poi>> {
    return repository.selectPois()
  }

  fun getNotification(id: Int): Flow<Notification> {
    return repository.selectNotification(id)
  }

  fun getUser(id: Int): Flow<User> {
    return repository.selectUser(id)
  }

  fun getNotifications(): Flow<List<Notification>> {
    return repository.selectNotifications()
  }

  fun getUsers(): Flow<List<User>> {
    return repository.selectUsers()
  }

  fun deletePoi(id: Int) = launch {
    repository.deletePoi(id)
  }

  fun deleteNotification(id: Int) = launch {
    repository.deleteNotification(id)
  }

  fun deleteUser(id: Int) = launch {
    repository.deleteUser(id)
  }

  fun deletePois() = launch {
    repository.deletePois()
  }

  fun deleteNotifications() = launch {
    repository.deleteNotifications()
  }

  fun deleteUsers() = launch {
    repository.deleteUsers()
  }

  fun getCreatingPoi(): Poi {
    return creatingPoiFlow.value
  }

  fun setCreatingPoi(poi: Poi) {
    creatingPoiFlow.value = poi
  }

  fun removeCreatingPoi() {
    creatingPoiFlow.value = Poi.NullPoi
  }

  fun setLatLngBounds(latLngBounds: LatLngBounds) {
    mapBoundariesFlow.value = latLngBounds
  }

  @ExperimentalCoroutinesApi
  private fun selectPoisWithin(latLngBounds: LatLngBounds): Flow<List<Poi>> {
    return getPois().mapLatest { it.filter { poi -> latLngBounds.contains(LatLng(poi.latitude, poi.longitude)) } }
  }

  private fun launch(block: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) { block() }
  }
}
