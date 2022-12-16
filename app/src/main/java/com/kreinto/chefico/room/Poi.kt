package com.kreinto.chefico.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pois")
class Poi(name: String, description: String = "", image: String = "", type: String = "") {
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "poiId")
  var id: Int = 0

  @ColumnInfo(name = "poiName")
  var name: String = name

  @ColumnInfo(name = "poiDescription")
  var description: String = description

  @ColumnInfo(name = "poiType")
  var type: String = type

  @ColumnInfo(name = "poiImage")
  var image: String = image
}
