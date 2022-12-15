package com.kreinto.chefico.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
class Notification(icon: String, text: String, poi: Int) {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "notificationId")
  var id: Int = 0

  @ColumnInfo(name = "notificationIcon")
  var icon: String = icon

  @ColumnInfo(name = "notificationText")
  var text: String = text

  @ColumnInfo(name = "notificationPoi")
  var poi: Int = poi
}





