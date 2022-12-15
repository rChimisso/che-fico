package com.kreinto.chefico.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

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
            context.applicationContext,
            CheFicoDatabase::class.java,
            "cheFicoDatabase"
          )
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
      }
      return instance
    }
  }
}