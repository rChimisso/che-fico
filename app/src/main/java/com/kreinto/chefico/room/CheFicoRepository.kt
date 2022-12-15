package com.kreinto.chefico.room

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CheFicoRepository(private val notificationDao: NotificationDao, private val poiDao: PoiDao) {

  private val coroutineScope = CoroutineScope(Dispatchers.Main)

  fun insertPoi(poi: Poi) {
    coroutineScope.launch(Dispatchers.IO) {
      poiDao.insertPoi(poi)
    }
  }

  fun insertNotification(notification: Notification) {
    coroutineScope.launch(Dispatchers.IO) {
      notificationDao.insertNotification(notification)
    }
  }

  fun selectPoi(id: Int): Flow<Poi> {
    return poiDao.selectPoi(id)
  }

  fun selectNotification(id: Int): Flow<Notification> {
    return notificationDao.selectNotification(id)
  }

  fun selectPois(): Flow<List<Poi>> {
    return poiDao.selectPois()
  }

  fun selectNotifications(): Flow<List<Notification>> {
    return notificationDao.selectNotifications()
  }

  fun deletePoi(id: Int) {
    coroutineScope.launch(Dispatchers.IO) {
      poiDao.deletePoi(id)
    }
  }

  fun deleteNotification(id: Int) {
    coroutineScope.launch(Dispatchers.IO) {
      notificationDao.deleteNotification(id)
    }
  }

  fun deletePois() {
    coroutineScope.launch(Dispatchers.IO) {
      poiDao.deletePois()
    }
  }

  fun deleteNotifications() {
    coroutineScope.launch(Dispatchers.IO) {
      notificationDao.deleteNotifications()
    }
  }

}