package com.kreinto.chefico.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kreinto.chefico.room.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
  @Insert
  fun insert(user: User)

  @Query("SELECT * FROM user WHERE id = :id")
  fun select(id: Int): Flow<User>

  @Query("DELETE FROM user")
  fun deleteAll()

  @Query("DELETE FROM user WHERE id = :id")
  fun delete(id: Int)

  @Query("SELECT * FROM user")
  fun selectAll(): Flow<List<User>>
}