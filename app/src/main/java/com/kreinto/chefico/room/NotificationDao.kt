package com.kreinto.chefico.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
  @Insert
  fun insertNotification(notification: Notification)

  @Query("SELECT * FROM notifications WHERE notificationId = :id")
  fun selectNotification(id: Int): Flow<Notification>

  @Query("DELETE FROM notifications")
  fun deleteNotifications()

  @Query("DELETE FROM notifications WHERE notificationId = :id")
  fun deleteNotification(id: Int)

  @Query("SELECT * FROM notifications")
  fun selectNotifications(): Flow<List<Notification>>
}