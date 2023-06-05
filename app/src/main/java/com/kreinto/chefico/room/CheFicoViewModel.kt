package com.kreinto.chefico.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.kreinto.chefico.managers.PoiNotificationManager
import com.kreinto.chefico.room.entities.Notification
import com.kreinto.chefico.room.entities.Poi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CheFicoViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: CheFicoRepository
  private val mapBoundariesFlow = MutableStateFlow(
    LatLngBounds(
      LatLng(0.0, 0.0),
      LatLng(0.0, 0.0)
    )
  )
  private val creatingPoiFlow = MutableStateFlow(Poi.NullPoi)

  @ExperimentalCoroutinesApi
  val poisWithin = mapBoundariesFlow.flatMapLatest { selectPoisWithin(it) }

  init {
    val database = CheFicoDatabase.getInstance(application)
    repository = CheFicoRepository(
      database.poiDao(),
      database.notificationDao()
    )
  }

  fun addPoi(poi: Poi) = launch {
    if (poi != Poi.NullPoi) {
      repository.updateAllNewPoiNotifications(repository.insertPoi(poi))
    }
  }

  fun addNotification(notification: Notification) = launch {
    repository.insertNotification(notification)
  }

  fun updatePoi(poi: Poi) = launch {
    repository.updatePoi(poi)
  }

  fun getPoi(id: Long): Flow<Poi> {
    return repository.selectPoi(id)
  }

  fun getPois(): Flow<List<Poi>> {
    return repository.selectPois()
  }

  fun getNotification(id: Long): Flow<Notification> {
    return repository.selectNotification(id)
  }

  fun getPoiNotifications(poiId: Long): Flow<List<Notification>> {
    return repository.selectPoiNotifications(poiId)
  }

  fun getNotifications(): Flow<List<Notification>> {
    return repository.selectNotifications()
  }

  fun deletePoi(id: Long) = launch {
    repository.deletePoi(id)
    repository.deletePoiNotifications(id)
  }

  fun deleteNotification(id: Long) = launch {
    repository.selectNotification(id).first {
      PoiNotificationManager.cancelNotification(getApplication<Application>().applicationContext, it)
      repository.deleteNotification(id)
      return@first true
    }
  }

  fun deletePois() = launch {
    repository.deletePois()
  }

  fun deleteNotifications() = launch {
    repository.deleteNotifications()
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

  fun deleteAllPoiNotifications(id: Long) = launch {
    repository.selectPoiNotifications(id).first().forEach {
      PoiNotificationManager.cancelNotification(getApplication<Application>().applicationContext, it)
    }
    repository.deletePoiNotifications(id)
  }

  private fun launch(block: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) { block() }
  }
}
