package com.kreinto.chefico.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
class Notification(var icon: String, var text: String, var message: String, var poiId: Int) {
  @PrimaryKey(autoGenerate = true)
  var id: Int = 0
}





