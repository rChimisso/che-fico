package com.kreinto.chefico.room.viewmodels

import android.app.Application
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.firebase.Timestamp
import com.kreinto.chefico.managers.PoiNotificationManager
import com.kreinto.chefico.room.entities.Notification
import com.kreinto.chefico.room.entities.Poi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

/**
 * Extends [CheFicoViewModel], handles local operations.
 *
 * @param application
 */
class LocalViewModel(application: Application) : CheFicoViewModel(application) {
  private val mapBoundariesFlow = MutableStateFlow(
    LatLngBounds(
      LatLng(0.0, 0.0),
      LatLng(0.0, 0.0)
    )
  )
  private val creatingPoiFlow = MutableStateFlow(Poi.NullPoi)

  @ExperimentalCoroutinesApi
  val poisWithin = mapBoundariesFlow.flatMapLatest { selectPoisWithin(it) }

  /**
   * Add [poi].
   *
   * @param poi
   */
  fun addPoi(poi: Poi) = launch {
    if (poi != Poi.NullPoi) {
      repository.updateAllNewPoiNotifications(repository.insertPoi(poi))
      updateTime()
    }
  }

  /**
   * Add [notification].
   *
   * @param notification
   */
  fun addNotification(notification: Notification) = launch {
    repository.insertNotification(notification)
    updateTime()
  }

  /**
   * Update the gievn [poi].
   *
   * @param poi
   */
  fun updatePoi(poi: Poi) = launch {
    repository.updatePoi(poi)
    updateTime()
  }

  /**
   * Get [Poi] gievn [id].
   *
   * @param id
   * @return
   */
  fun getPoi(id: Long): Flow<Poi> {
    return repository.selectPoi(id)
  }

  /**
   * Get all Pois.
   *
   * @return
   */
  fun getPois(): Flow<List<Poi>> {
    return repository.selectPois()
  }

  /**
   * Get all Poi notifications.
   *
   * @param poiId
   * @return
   */
  fun getPoiNotifications(poiId: Long): Flow<List<Notification>> {
    return repository.selectPoiNotifications(poiId)
  }

  /**
   * Get all notifications.
   *
   * @return
   */
  fun getNotifications(): Flow<List<Notification>> {
    return repository.selectNotifications()
  }

  /**
   * Delete [Poi] with given [id].
   *
   * @param id
   */
  fun deletePoi(id: Long) = launch {
    repository.deletePoi(id)
    repository.deletePoiNotifications(id)
    updateTime()
  }

  /**
   * Delete [Notification] with given [id].
   *
   * @param id
   */
  fun deleteNotification(id: Long) = launch {
    repository.selectNotification(id).first {
      PoiNotificationManager.cancelNotification(getApplication<Application>().applicationContext, it)
      repository.deleteNotification(id)
      return@first true
    }
    updateTime()
  }

  /**
   * Delete all notifications.
   */
  fun deleteNotifications() = launch {
    repository.deleteNotifications()
    updateTime()
  }

  /**
   * Delete all [Poi] notifications.
   *
   * @param id
   */
  fun deleteAllPoiNotifications(id: Long) = launch {
    repository.selectPoiNotifications(id).first().forEach {
      PoiNotificationManager.cancelNotification(getApplication<Application>().applicationContext, it)
    }
    repository.deletePoiNotifications(id)
    updateTime()
  }

  /**
   * Get creating [Poi].
   *
   * @return
   */
  fun getCreatingPoi(): Poi {
    return creatingPoiFlow.value
  }

  /**
   * Set creating [Poi].
   *
   * @param poi
   */
  fun setCreatingPoi(poi: Poi) {
    creatingPoiFlow.value = poi
  }

  /**
   * Remove creating [Poi].
   */
  fun removeCreatingPoi() {
    creatingPoiFlow.value = Poi.NullPoi
  }

  /**
   * Set latitude longitude bounds.
   *
   * @param latLngBounds
   */
  fun setLatLngBounds(latLngBounds: LatLngBounds) {
    mapBoundariesFlow.value = latLngBounds
  }

  /**
   * Select all pois within given [latLngBounds].
   *
   * @param latLngBounds
   * @return
   */
  @ExperimentalCoroutinesApi
  private fun selectPoisWithin(latLngBounds: LatLngBounds): Flow<List<Poi>> {
    return getPois().mapLatest { it.filter { poi -> latLngBounds.contains(LatLng(poi.latitude, poi.longitude)) } }
  }

  /**
   * Update last update locally.
   */
  private fun updateTime() {
    settings.lastUpdate = Timestamp.now().seconds
  }
}
