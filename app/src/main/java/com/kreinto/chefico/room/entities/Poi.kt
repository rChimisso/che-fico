package com.kreinto.chefico.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pois")
class Poi(
  @PrimaryKey(autoGenerate = true)
  var id: Int = 0,
  var name: String,
  var description: String = "",
  var image: String = "",
  var latitude: Double = 0.0,
  var longitude: Double = 0.0,
) {
  companion object {
    val NullPoi = Poi(name = "")
  }
}
