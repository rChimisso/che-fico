package com.kreinto.chefico.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CheFicoViewModel(application: Application) : AndroidViewModel(application) {
  private val database: CheFicoDatabase
  private val repository: CheFicoRepository
  private var pois: Flow<List<Poi>>
  private val notifications: Flow<List<Notification>>

  init {
    database = CheFicoDatabase.getInstance(application)
    repository = CheFicoRepository(
      database.notificationDao(),
      database.poiDao()
    )
    pois = repository.selectPois()
    notifications = repository.selectNotifications()
  }

  fun addPoi(poi: Poi) {
    viewModelScope.launch(Dispatchers.IO) {
      repository.insertPoi(poi)
    }
  }

  fun addNotification(notification: Notification) {
    viewModelScope.launch(Dispatchers.IO) {
      repository.insertNotification(notification)
    }
  }

  fun updatePoi(poi: Poi) {
    viewModelScope.launch(Dispatchers.IO) {
      repository.updatePoi(poi)
    }
  }

  fun getPoi(id: Int): Flow<Poi> {
    return repository.selectPoi(id)
  }

  fun getNotification(id: Int): Flow<Notification> {
    return repository.selectNotification(id)
  }

  fun getPois(): Flow<List<Poi>> {
    return pois
  }

  fun getNotifications(): Flow<List<Notification>> {
    return notifications
  }

  fun deletePoi(id: Int) {
    viewModelScope.launch(Dispatchers.IO) {
      repository.deletePoi(id)
    }
  }

  fun deleteNotification(id: Int) {
    viewModelScope.launch(Dispatchers.IO) {
      repository.deleteNotification(id)
    }
  }

  fun deletePois() {
    viewModelScope.launch(Dispatchers.IO) {
      repository.deletePois()
    }
  }

  fun deleteNotifications() {
    viewModelScope.launch(Dispatchers.IO) {
      repository.deleteNotifications()
    }
  }
}