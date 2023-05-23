package com.kreinto.chefico.room

import com.kreinto.chefico.room.daos.NotificationDao
import com.kreinto.chefico.room.daos.PoiDao
import com.kreinto.chefico.room.daos.UserDao
import com.kreinto.chefico.room.entities.Notification
import com.kreinto.chefico.room.entities.Poi
import com.kreinto.chefico.room.entities.User
import kotlinx.coroutines.flow.Flow

class CheFicoRepository(
  private val notificationDao: NotificationDao,
  private val poiDao: PoiDao,
  private val userDao: UserDao
) {

  fun insertPoi(poi: Poi) {
    poiDao.insert(poi)
  }

  fun insertNotification(notification: Notification) {
    notificationDao.insert(notification)
  }

  fun insertUser(user: User) {
    userDao.insert(user)
  }

  fun selectPoi(id: Int): Flow<Poi> {
    return poiDao.select(id)
  }

  fun selectUser(id: Int): Flow<User> {
    return userDao.select(id)
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

  fun selectUsers(): Flow<List<User>> {
    return userDao.selectAll()
  }

  fun deletePoi(id: Int) {
    poiDao.delete(id)
  }

  fun deleteNotification(id: Int) {
    notificationDao.delete(id)
  }

  fun deleteUser(id: Int) {
    userDao.delete(id)
  }

  fun deletePois() {
    poiDao.deleteAll()
  }

  fun deleteUsers() {
    userDao.deleteAll()
  }

  fun deleteNotifications() {
    notificationDao.deleteAll()
  }
}