package com.kreinto.chefico.room.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kreinto.chefico.room.CheFicoDatabase
import com.kreinto.chefico.room.CheFicoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Abstract Che Fico! View Model.
 *
 * @param application
 */
abstract class CheFicoViewModel(application: Application) : AndroidViewModel(application) {
  /**
   * Repository for local data access.
   */
  protected val repository: CheFicoRepository

  init {
    val database = CheFicoDatabase.getInstance(application)
    repository = CheFicoRepository(database.poiDao(), database.notificationDao())
  }

  /**
   * Launches a suspend block.
   *
   * @param block
   */
  protected fun launch(block: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) { block() }
  }
}