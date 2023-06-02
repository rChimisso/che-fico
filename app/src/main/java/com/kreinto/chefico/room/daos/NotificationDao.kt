package com.kreinto.chefico.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kreinto.chefico.room.entities.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
  @Insert
  fun insert(notification: Notification): Long

  @Query("SELECT * FROM notifications WHERE id = :id")
  fun select(id: Int): Flow<Notification>

  @Query("DELETE FROM notifications")
  fun deleteAll()

  @Query("DELETE FROM notifications WHERE id = :id")
  fun delete(id: Int)

  @Query("SELECT * FROM notifications")
  fun selectAll(): Flow<List<Notification>>

  @Query("SELECT * FROM notifications WHERE poiId = :poiId")
  fun selectAllOfPoi(poiId: Int): Flow<List<Notification>>

  @Query("DELETE FROM notifications WHERE poiId = :poiId")
  fun deleteAllOfPoi(poiId: Int)

  @Query("UPDATE notifications SET poiId = :newId WHERE poiId = 0")
  fun updateAllOfNewPoi(newId: Long)
}