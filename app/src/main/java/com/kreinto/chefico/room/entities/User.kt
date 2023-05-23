package com.kreinto.chefico.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User (
  var language: String = "it"
  ){
  @PrimaryKey(autoGenerate = true)
  var id: Int = 0
  companion object {
    const val defaultUser: Int = 0
    val NullUser = User()
  }
}