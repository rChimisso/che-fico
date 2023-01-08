package com.kreinto.chefico

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService

class PoiNotificationManager : BroadcastReceiver() {
  companion object {
    private const val channelId: String = "CheFicoChannel"
    private const val notificationId: Int = 1
    private const val channelName: String = "Che Fico!"
    private const val channelDescription: String = "Default channel"
    private const val titleExtra = "titleExtra"
    private const val messageExtra = "messageExtra"

    fun createNotificationChannel(context: Context) {
      val importance = NotificationManager.IMPORTANCE_DEFAULT

      val channel = NotificationChannel(channelId, channelName, importance)
      channel.description = channelDescription

      with(context.getSystemService<NotificationManager>()) {
        this?.createNotificationChannel(channel)
      }
    }

    fun scheduleNotification(context: Context, time: Long, title: String, message: String) {
      val intent = Intent(context, PoiNotificationManager::class.java)
      intent.putExtra(titleExtra, title)
      intent.putExtra(messageExtra, message)

      val pendingIntent = PendingIntent.getBroadcast(
        context,
        notificationId,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
      )

      with(context.getSystemService<AlarmManager>()) {
        this?.set(
          AlarmManager.RTC,
          time,
          pendingIntent
        )
      }
    }

    fun showNotification(context: Context, title: String, message: String) {
      val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

      with(context.getSystemService<NotificationManager>()) {
        this?.notify(1, notification)
      }
    }
  }

  override fun onReceive(context: Context, intent: Intent) {
    val notification: Notification = NotificationCompat.Builder(context, channelId)
      .setSmallIcon(R.drawable.ic_launcher_foreground)
      .setContentTitle(intent.getStringExtra(titleExtra))
      .setContentText(intent.getStringExtra(messageExtra))
      .build()

    with(context.getSystemService<NotificationManager>()) {
      this?.notify(notificationId, notification)
    }
  }
}
