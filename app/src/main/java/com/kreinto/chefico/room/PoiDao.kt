package com.kreinto.chefico.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PoiDao {
  @Insert
  fun insertPoi(poi: Poi)

  @Query("DELETE FROM pois WHERE poiId = :id")
  fun deletePoi(id: Int)

  @Query("DELETE FROM pois")
  fun deletePois()

  @Query("SELECT * FROM pois WHERE poiId = :id")
  fun selectPoi(id: Int): Flow<Poi>

  @Query("SELECT * FROM pois")
  fun selectPois(): Flow<List<Poi>>

  @Update
  fun updatePoi(poi: Poi)
}