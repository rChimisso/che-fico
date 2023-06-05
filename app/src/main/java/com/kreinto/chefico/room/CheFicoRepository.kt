package com.kreinto.chefico.room

import com.kreinto.chefico.room.daos.NotificationDao
import com.kreinto.chefico.room.daos.PoiDao
import com.kreinto.chefico.room.entities.Notification
import com.kreinto.chefico.room.entities.Poi
import kotlinx.coroutines.flow.Flow

/**
 * Che Fico! Repository.
 *
 * Holds all DAOs and functions as bridge to access the Database.
 *
 * @property poiDao
 * @property notificationDao
 */
class CheFicoRepository(private val poiDao: PoiDao, private val notificationDao: NotificationDao) {
  /**
   * Inserts the given [Poi].
   *
   * @param poi
   * @return Autogenerated ID.
   */
  fun insertPoi(poi: Poi): Long {
    return poiDao.insert(poi)
  }

  /**
   * Gets the specified [Poi].
   *
   * @param id
   * @return
   */
  fun selectPoi(id: Int): Flow<Poi> {
    return poiDao.select(id)
  }

  /**
   * Gets all specified POIs.
   *
   * @return
   */
  fun selectPois(ids: List<Int>): Flow<List<Poi>> {
    return poiDao.selectAll(ids)
  }

  /**
   * Gets all POIs.
   *
   * @return
   */
  fun selectPois(): Flow<List<Poi>> {
    return poiDao.selectAll()
  }

  /**
   * Deletes the specified [Poi].
   *
   * @param id
   */
  fun deletePoi(id: Int) {
    poiDao.delete(id)
  }

  /**
   * Deletes all POIs.
   */
  fun deletePois() {
    poiDao.deleteAll()
  }

  /**
   * Updates the given [Poi].
   *
   * @param poi
   */
  fun updatePoi(poi: Poi) {
    poiDao.update(poi)
  }

  /**
   * Inserts the given [Notification].
   *
   * @param notification
   * @return Autogenerated ID.
   */
  fun insertNotification(notification: Notification): Long {
    return notificationDao.insert(notification)
  }

  /**
   * Gets the specified [Notification].
   *
   * @param id
   * @return
   */
  fun selectNotification(id: Int): Flow<Notification> {
    return notificationDao.select(id)
  }

  /**
   * Gets all notifications associated with the specified [Poi].
   *
   * @param poiId
   * @return
   */
  fun selectPoiNotifications(poiId: Int): Flow<List<Notification>> {
    return notificationDao.selectAllOfPoi(poiId)
  }

  /**
   * Gets all notifications.
   *
   * @return
   */
  fun selectNotifications(): Flow<List<Notification>> {
    return notificationDao.selectAll()
  }

  /**
   * Deletes the specified [Notification].
   *
   * @param id
   */
  fun deleteNotification(id: Int) {
    notificationDao.delete(id)
  }

  /**
   * Deletes all notifications associated with the specified [Poi].
   *
   * @param id
   */
  fun deletePoiNotifications(id: Int) {
    notificationDao.deleteAllOfPoi(id)
  }

  /**
   * Deletes all notifications.
   */
  fun deleteNotifications() {
    notificationDao.deleteAll()
  }

  /**
   * Updates all notifications associated with the newly added POI to associate them with the given ID.
   *
   * @param newId
   */
  fun updateAllNewPoiNotifications(newId: Long) {
    notificationDao.updateAllOfNewPoi(newId)
  }
}