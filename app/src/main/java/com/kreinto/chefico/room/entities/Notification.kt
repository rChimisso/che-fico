package com.kreinto.chefico.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
class Notification(icon: String, text: String, poiId: Int) {

  @PrimaryKey(autoGenerate = true)
  var id: Int = 0

  var icon: String = icon

  var text: String = text

  var poiId: Int = poiId
}





