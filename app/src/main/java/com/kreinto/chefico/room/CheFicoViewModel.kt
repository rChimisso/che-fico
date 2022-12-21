package com.kreinto.chefico.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kreinto.chefico.room.entities.Notification
import com.kreinto.chefico.room.entities.Poi
import com.kreinto.chefico.views.maps.MapBoundaries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class CheFicoViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: CheFicoRepository
  private val mapBoundariesFlow = MutableStateFlow(MapBoundaries(0.0, 0.0, 0.0, 0.0))
  val poisWithin = mapBoundariesFlow.flatMapLatest { selectPoisWithin(it) }

  init {
    var database = CheFicoDatabase.getInstance(application)
    repository = CheFicoRepository(
      database.notificationDao(),
      database.poiDao()
    )
  }


  fun addPoi(poi: Poi) = launch {
    repository.insertPoi(poi)
  }

  fun addNotification(notification: Notification) = launch {
    repository.insertNotification(notification)
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

  fun getNotifications(): Flow<List<Notification>> {
    return repository.selectNotifications()
  }

  fun deletePoi(id: Int) = launch {
    repository.deletePoi(id)
  }

  fun deleteNotification(id: Int) = launch {
    repository.deleteNotification(id)
  }

  fun deletePois() = launch {
    repository.deletePois()
  }

  fun deleteNotifications() = launch {
    repository.deleteNotifications()
  }

  fun selectPoisWithin(mapBoundaries: MapBoundaries): Flow<List<Poi>> {
    return repository.selectPoisWithin(
      mapBoundaries.top,
      mapBoundaries.right,
      mapBoundaries.bottom,
      mapBoundaries.left
    )
  }

  fun setMapBoundaries(mapBoundaries: MapBoundaries) {
    mapBoundariesFlow.value = mapBoundaries
  }

  private fun launch(block: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) { block() }
  }
}