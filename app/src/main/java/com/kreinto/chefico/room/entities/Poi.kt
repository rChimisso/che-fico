package com.kreinto.chefico.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * POI Database Entity.
 *
 * @property id Row ID.
 * @property name Name.
 * @property description Description.
 * @property image Image path.
 * @property latitude Latitude coordinate.
 * @property longitude Longitude coordinate.
 */
@Entity(tableName = "pois")
class Poi(
  var name: String,
  var description: String = "",
  var image: String = "",
  var latitude: Double = 0.0,
  var longitude: Double = 0.0,
  @PrimaryKey(true)
  var id: Long = 0
) {
  companion object {
    /**
     * Static reference to the empty POI.
     */
    val NullPoi = Poi(name = "")
  }
}
