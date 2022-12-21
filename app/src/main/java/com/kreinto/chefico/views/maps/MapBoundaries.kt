package com.kreinto.chefico.views.maps

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

class MapBoundaries(val top: Double, val right: Double, val bottom: Double, val left: Double) {
  companion object {
    fun compute(center: LatLng, radius: Double): MapBoundaries {
      val vertices: Array<LatLng> = Array(4) { LatLng(0.0, 0.0) }
      for (i in 0..3) {
        vertices[i] = SphericalUtil.computeOffset(center, radius, 90.0 * i)
      }
      return MapBoundaries(
        vertices[0].latitude,
        vertices[1].longitude,
        vertices[2].latitude,
        vertices[3].longitude
      )
    }
  }
}