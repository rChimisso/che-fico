package com.kreinto.chefico.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kreinto.chefico.room.entities.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
  @Insert
  fun insert(notification: Notification)

  @Query("SELECT * FROM notifications WHERE id = :id")
  fun select(id: Int): Flow<Notification>

  @Query("DELETE FROM notifications")
  fun deleteAll()

  @Query("DELETE FROM notifications WHERE id = :id")
  fun delete(id: Int)

  @Query("SELECT * FROM notifications")
  fun selectAll(): Flow<List<Notification>>
}