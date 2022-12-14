package com.kreinto.chefico.room

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Entity(tableName = "notifications")
class Notification(icon: String, text: String, poi: Int) {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "notificationId")
  var id: Int = 0

  @ColumnInfo(name = "notificationIcon")
  var icon: String = icon

  @ColumnInfo(name = "notificationText")
  var text: String = text

  @ColumnInfo(name = "notificationPoi")
  var poi: Int = poi
}

@Entity(tableName = "pois")
class Poi(name: String, description: String) {
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "poiId")
  var id: Int = 0

  @ColumnInfo(name = "poiName")
  var name: String = name

  @ColumnInfo(name = "poiDescription")
  var description: String = description
}

@Dao
interface CheFicoDatabaseDao {

  @Insert
  fun insertNotification(notification: Notification)

  @Query("SELECT * FROM notifications WHERE notificationId = :id")
  fun findNotification(id: Int): Notification

  @Delete
  fun deleteNotification(notification: Notification)

  @Query("SELECT * FROM notifications")
  fun selectAllNotifications(): Flow<List<Notification>>

  @Insert
  fun insertPoi(poi: Poi)

  @Query("DELETE FROM pois WHERE poiId = :id")
  fun deletePoi(id: Int)

  @Query("DELETE FROM pois")
  fun deleteAllPoi()

  @Query("SELECT * FROM pois WHERE poiId = :id")
  fun findPoi(id: Int): Flow<Poi>

  @Query("SELECT * FROM pois")
  fun selectAllPois(): Flow<List<Poi>>
}

@Database(entities = [(Notification::class), (Poi::class)], version = 1)
abstract class CheFicoDatabase : RoomDatabase() {
  abstract fun cheFicoDatabaseDao(): CheFicoDatabaseDao

  companion object {
    private var INSTANCE: CheFicoDatabase? = null
    fun getInstance(context: Context): CheFicoDatabase {
      synchronized(this) {
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
}

class CheFicoRepository(private val cheFicoDatabaseDao: CheFicoDatabaseDao) {

  private val coroutineScope = CoroutineScope(Dispatchers.Main)

  fun insertPoi(poi: Poi) {
    coroutineScope.launch(Dispatchers.IO) {
      cheFicoDatabaseDao.insertPoi(poi)
    }
  }

  fun insertNotification(notification: Notification) {
    coroutineScope.launch(Dispatchers.IO) {
      cheFicoDatabaseDao.insertNotification(notification)
    }
  }

  fun selectAllPois(): Flow<List<Poi>> {
    return cheFicoDatabaseDao.selectAllPois()
  }

  fun selectAllNotifications(): Flow<List<Notification>> {
    return cheFicoDatabaseDao.selectAllNotifications()
  }

  fun deletePoi(id: Int) {
    coroutineScope.launch(Dispatchers.IO) {
      cheFicoDatabaseDao.deletePoi(id)
    }
  }

  fun deleteAllPois() {
    coroutineScope.launch(Dispatchers.IO) {
      cheFicoDatabaseDao.deleteAllPoi()
    }
  }

}

class CheFicoViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: CheFicoRepository
  private var pois: Flow<List<Poi>>
  private val notifications: Flow<List<Notification>>

  init {
    repository = CheFicoRepository(CheFicoDatabase.getInstance(application).cheFicoDatabaseDao())
    pois = repository.selectAllPois()
    notifications = repository.selectAllNotifications()
  }

  fun addPoi(poi: Poi) {
    viewModelScope.launch(Dispatchers.IO) {
      repository.insertPoi(poi)
    }
  }

  fun addNotification(notification: Notification) {
    viewModelScope.launch(Dispatchers.IO) {
      repository.insertNotification(notification)
    }
  }

  fun getPois(): Flow<List<Poi>> {
    return pois
  }

  fun getNotifications(): Flow<List<Notification>> {
    return notifications
  }

  fun deletePoi(id: Int) {
    viewModelScope.launch(Dispatchers.IO) {
      repository.deletePoi(id)
    }
  }

  fun deleteAllPois() {
    viewModelScope.launch(Dispatchers.IO) {
      repository.deleteAllPois()
    }
  }
}
