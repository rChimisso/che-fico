package com.kreinto.chefico.room

import com.kreinto.chefico.room.daos.NotificationDao
import com.kreinto.chefico.room.daos.PoiDao
import com.kreinto.chefico.room.entities.Notification
import com.kreinto.chefico.room.entities.Poi
import kotlinx.coroutines.flow.Flow

class CheFicoRepository(private val notificationDao: NotificationDao, private val poiDao: PoiDao) {
  fun selectPoiNotifications(poiId: Int): Flow<List<Notification>> {
    return notificationDao.selectAllOfPoi(poiId)
  }

  fun insertPoi(poi: Poi): Long {
    return poiDao.insert(poi)
  }

  fun insertNotification(notification: Notification): Long {
    return notificationDao.insert(notification)
  }

  fun selectPoi(id: Int): Flow<Poi> {
    return poiDao.select(id)
  }

  fun updatePoi(poi: Poi) {
    poiDao.update(poi)
  }

  fun selectNotification(id: Int): Flow<Notification> {
    return notificationDao.select(id)
  }

  fun selectPois(): Flow<List<Poi>> {
    return poiDao.selectAll()
  }

  fun selectNotifications(): Flow<List<Notification>> {
    return notificationDao.selectAll()
  }

  fun deletePoi(id: Int) {
    poiDao.delete(id)
  }

  fun deleteNotification(id: Int) {
    notificationDao.delete(id)
  }

  fun deletePois() {
    poiDao.deleteAll()
  }

  fun deleteNotifications() {
    notificationDao.deleteAll()
  }

  fun deleteAllPoiNotifications(id: Int) {
    notificationDao.deleteAllOfPoi(id)
  }

  fun updateAllNewPoiNotifications(newId: Long) {
    notificationDao.updateAllOfNewPoi(newId)
  }
}