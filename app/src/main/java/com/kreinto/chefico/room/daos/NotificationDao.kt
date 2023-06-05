package com.kreinto.chefico.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kreinto.chefico.room.entities.Notification
import kotlinx.coroutines.flow.Flow

/**
 * Notification Data Access Object.
 */
@Dao
interface NotificationDao {
  /**
   * Inserts the given [Notification].
   *
   * @param notification
   * @return ID of the newly added row.
   */
  @Insert
  fun insert(notification: Notification): Long

  /**
   * Selects the specified [Notification].
   *
   * @param id
   * @return
   */
  @Query("SELECT * FROM notifications WHERE id = :id")
  fun select(id: Long): Flow<Notification>

  /**
   * Selects all notification associated with the specified POI.
   *
   * @param poiId
   * @return
   */
  @Query("SELECT * FROM notifications WHERE poiId = :poiId")
  fun selectAllOfPoi(poiId: Long): Flow<List<Notification>>

  /**
   * Selects all notifications.
   *
   * @return
   */
  @Query("SELECT * FROM notifications")
  fun selectAll(): Flow<List<Notification>>

  /**
   * Deletes the specified [Notification].
   *
   * @param id
   */
  @Query("DELETE FROM notifications WHERE id = :id")
  fun delete(id: Long)

  /**
   * Deletes all notifications associated with the specified POI.
   *
   * @param poiId
   */
  @Query("DELETE FROM notifications WHERE poiId = :poiId")
  fun deleteAllOfPoi(poiId: Long)

  /**
   * Deletes all notifications.
   */
  @Query("DELETE FROM notifications")
  fun deleteAll()

  /**
   * Updates all notifications associated with the newly added POI to associate them with the given ID.
   *
   * @param newId
   */
  @Query("UPDATE notifications SET poiId = :newId WHERE poiId = 0")
  fun updateAllOfNewPoi(newId: Long)
}