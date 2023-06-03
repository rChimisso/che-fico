package com.kreinto.chefico.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Notification Database Entity.
 *
 * @property id Row ID.
 * @property icon Display icon.
 * @property text Title.
 * @property message Message.
 * @property poiId Associated POI.
 */
@Entity(tableName = "notifications")
class Notification(
  @PrimaryKey(true)
  var id: Int = 0,
  var icon: String,
  var text: String,
  var message: String,
  var poiId: Int
)





