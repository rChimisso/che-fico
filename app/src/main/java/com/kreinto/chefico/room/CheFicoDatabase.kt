package com.kreinto.chefico.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kreinto.chefico.room.daos.NotificationDao
import com.kreinto.chefico.room.daos.PoiDao
import com.kreinto.chefico.room.entities.Notification
import com.kreinto.chefico.room.entities.Poi

/**
 * Che Fico! Database.
 */
@Database(entities = [(Notification::class), (Poi::class)], version = 6)
abstract class CheFicoDatabase : RoomDatabase() {
  /**
   * Gets the [NotificationDao].
   *
   * @return
   */
  abstract fun notificationDao(): NotificationDao

  /**
   * Gets the [PoiDao].
   *
   * @return
   */
  abstract fun poiDao(): PoiDao

  companion object {
    /**
     * Database singleton instance.
     */
    private var INSTANCE: CheFicoDatabase? = null

    /**
     * Returns the singleton Database instance.
     *
     * @param context
     * @return
     */
    @Synchronized()
    fun getInstance(context: Context): CheFicoDatabase {
      var instance = INSTANCE
      if (instance == null) {
        instance = Room
          .databaseBuilder(
            context,
            CheFicoDatabase::class.java,
            "cheFicoDatabase.db"
          )
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
      }
      return instance
    }
  }
}