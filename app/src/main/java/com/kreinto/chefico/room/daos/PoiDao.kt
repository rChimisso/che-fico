package com.kreinto.chefico.room.daos

import androidx.room.*
import com.kreinto.chefico.room.entities.Poi
import kotlinx.coroutines.flow.Flow

/**
 * POI Data Access Object.
 */
@Dao
interface PoiDao {
  /**
   * Inserts the given [Poi].
   *
   * @param poi
   * @return ID of the newly added row.
   */
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(poi: Poi): Long

  /**
   * Selects the specified [Poi].
   *
   * @param id
   * @return
   */
  @Query("SELECT * FROM pois WHERE id = :id")
  fun select(id: Int): Flow<Poi>

  /**
   * Selects all POIs.
   *
   * @return
   */
  @Query("SELECT * FROM pois")
  fun selectAll(): Flow<List<Poi>>

  /**
   * Deletes the specified [Poi].
   *
   * @param id
   */
  @Query("DELETE FROM pois WHERE id = :id")
  fun delete(id: Int)

  /**
   * Deletes all POIs.
   */
  @Query("DELETE FROM pois")
  fun deleteAll()

  /**
   * Updates the given [Poi].
   *
   * @param poi
   */
  @Update
  fun update(poi: Poi)
}