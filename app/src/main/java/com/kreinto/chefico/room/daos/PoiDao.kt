package com.kreinto.chefico.room.daos

import androidx.room.*
import com.kreinto.chefico.room.entities.Poi
import kotlinx.coroutines.flow.Flow

@Dao
interface PoiDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(poi: Poi)

  @Query("DELETE FROM pois WHERE id = :id")
  fun delete(id: Int)

  @Query("DELETE FROM pois")
  fun deleteAll()

  @Query("SELECT * FROM pois WHERE id = :id")
  fun select(id: Int): Flow<Poi>

  @Query("SELECT * FROM pois")
  fun selectAll(): Flow<List<Poi>>

  @Update
  fun update(poi: Poi)
}