package com.kreinto.chefico.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pois")
class Poi(
  name: String,
  description: String = "",
  image: String = "",
  type: String = "",
  latitude: Double = 0.0,
  longitude: Double = 0.0,
) {
  @PrimaryKey(autoGenerate = true)
  var id: Int = 0

  var name: String = name

  var description: String = description

  var type: String = type

  var image: String = image

  var latitude: Double = latitude

  var longitude: Double = longitude

  companion object {
    val NullPoi = Poi("")
  }
}
