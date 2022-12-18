package com.kreinto.chefico.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kreinto.chefico.room.daos.NotificationDao
import com.kreinto.chefico.room.daos.PoiDao
import com.kreinto.chefico.room.entities.Notification
import com.kreinto.chefico.room.entities.Poi

@Database(entities = [(Notification::class), (Poi::class)], version = 1)
abstract class CheFicoDatabase : RoomDatabase() {
  abstract fun notificationDao(): NotificationDao
  abstract fun poiDao(): PoiDao

  companion object {
    private var INSTANCE: CheFicoDatabase? = null

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