package com.kreinto.chefico.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
class Notification(
  @PrimaryKey(true)
  var id: Int = 0,
  var icon: String,
  var text: String,
  var message: String,
  var poiId: Int
)





