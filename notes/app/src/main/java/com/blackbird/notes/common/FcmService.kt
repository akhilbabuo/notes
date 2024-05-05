package com.blackbird.notes.common

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.blackbird.notes.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.time.LocalDateTime
import java.time.ZoneOffset


enum class MessageTypes {
    DEEPLINK,
    WEBLINK,
    EXTERNAL_LINK,
    EXECUTE
}

class FcmService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("message", "onMessageReceived: ${message.data}")

        message.data.let {
            if (it["redirectionType"] == MessageTypes.EXECUTE.name) {
                Log.d("execute", "onMessageReceived: ")
            } else {
                handleNotification(message)
            }
        }
    }

    private fun handleNotification(message: RemoteMessage) {
        val channelId = "alert"
        val channel =
            NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification = createNotificationObj(message, channelId)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        NotificationManagerCompat.from(this)
            .notify(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toInt(), notification)

    }

    private fun createNotificationObj(message: RemoteMessage, channelId: String): Notification {
        val title = message.data["title"]
        val body = message.data["body"]
        val notificationBuilder = Notification.Builder(applicationContext, channelId)
        notificationBuilder.setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
        return notificationBuilder.build()
    }
}