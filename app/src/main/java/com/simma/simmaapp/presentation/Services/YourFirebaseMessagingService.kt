package com.simma.simmaapp.presentation.Services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.simma.simmaapp.R

class YourFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e("Ayham", "Message Received")
        showNotification(title = remoteMessage.notification?.title?:"Ayham",
            message = remoteMessage.notification?.body?:"hammad")

    }

    override fun onDeletedMessages() {
        Log.e("Ayham", "Message deleted")
        super.onDeletedMessages()
    }

    @SuppressLint("MissingPermission", "RemoteViewLayout")
    private fun showNotification(
        title: String,
        message: String
    ) {
        val notificationManager = NotificationManagerCompat.from(this)

        val channel_id = "notification_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channel_id,
                "Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        val notificationLayout = RemoteViews(packageName, R.layout.notification_layout)
        notificationLayout.setTextViewText(R.id.title, title)
        notificationLayout.setTextViewText(R.id.message, message)
        val notification = NotificationCompat.Builder(
            this,
            channel_id
        )
            .setVibrate(longArrayOf(
                1000, 1000, 1000,
                1000, 1000
            ))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .build()

        notificationManager.notify(1, notification)
    }
}