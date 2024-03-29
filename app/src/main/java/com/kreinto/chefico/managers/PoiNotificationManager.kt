package com.kreinto.chefico.managers

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.kreinto.chefico.R
import java.util.*

/**
 * Manage creation and deletion of notifications.
 */
class PoiNotificationManager : BroadcastReceiver() {
  companion object {
    private const val channelId: String = "CheFicoChannel"
    private const val channelName: String = "Che Fico!"
    private const val channelDescription: String = "Default channel"
    private const val titleExtra = "titleExtra"
    private const val messageExtra = "messageExtra"
    private var notificationId = 0

    /**
     * Create [NotificationChannel] for the application.
     *
     * @param context
     */
    fun createNotificationChannel(context: Context) {
      val importance = NotificationManager.IMPORTANCE_DEFAULT

      val channel = NotificationChannel(channelId, channelName, importance).apply {
        description = channelDescription
      }

      with(context.getSystemService<NotificationManager>()) {
        this?.createNotificationChannel(channel)
      }
    }

    /**
     * Create a scheduled notification.
     *
     * @param context
     * @param time
     * @param title
     * @param message
     */
    fun scheduleNotification(context: Context, time: Long, title: String, message: String) {
      val intent = Intent(context, PoiNotificationManager::class.java)
      intent.putExtra(titleExtra, title)
      intent.putExtra(messageExtra, message)

      val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
      )

      with(context.getSystemService<AlarmManager>()) {
        this?.set(AlarmManager.RTC, time, pendingIntent)
      }
    }

    /**
     * Cancel a scheduled notification.
     *
     * @param context
     * @param notification
     */
    fun cancelNotification(context: Context, notification: com.kreinto.chefico.room.entities.Notification) {
      Intent(context.applicationContext, PoiNotificationManager::class.java).let { intent ->
        intent.putExtra(titleExtra, notification.text)
        intent.putExtra(messageExtra, notification.message)
        PendingIntent.getBroadcast(
          context.applicationContext,
          0,
          intent,
          PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
      }.cancel()
    }
  }

  /**
   * Override of [BroadcastReceiver.onReceive].
   *
   * @param context
   * @param intent
   */
  override fun onReceive(context: Context, intent: Intent) {
    val notification: Notification = NotificationCompat.Builder(context, channelId)
      .setSmallIcon(R.drawable.ic_notification)
      .setContentTitle(intent.getStringExtra(titleExtra))
      .setContentText(intent.getStringExtra(messageExtra))
      .build()

    with(context.getSystemService<NotificationManager>()) {
      this?.notify(notificationId++, notification)
    }

    with(context.getSystemService<AlarmManager>()) {
      Intent(context.applicationContext, PoiNotificationManager::class.java).let { intent1 ->
        intent1.putExtra(titleExtra, intent.getStringExtra(titleExtra))
        intent1.putExtra(messageExtra, intent.getStringExtra(messageExtra))
        PendingIntent.getBroadcast(
          context.applicationContext,
          0,
          intent1,
          PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
      }.let {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1)
        this?.set(AlarmManager.RTC, calendar.timeInMillis, it)
      }
    }
  }
}
