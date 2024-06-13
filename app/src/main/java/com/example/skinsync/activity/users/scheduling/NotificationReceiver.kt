package com.example.skinsync.activity.users.scheduling

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import com.example.skinsync.R
import java.util.*

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel
        val channelId = "reminder_channel"
        val channelName = "Reminder Channel"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Determine if it's morning or night
        val currentTime = Calendar.getInstance()
        val hourOfDay = currentTime.get(Calendar.HOUR_OF_DAY)
        val isMorning = hourOfDay in 6..11 // Adjust the time range for morning notification
        val isNight = hourOfDay in 19..23 || hourOfDay in 0..5 // Adjust the time range for night notification

        // Build notification based on time of day
        val notificationTitle: String
        val notificationContent: String
        if (isMorning) {
            notificationTitle = "Morning Routine Reminder"
            notificationContent = "Don't forget to complete your morning routine!"
        } else if (isNight) {
            notificationTitle = "Night Routine Reminder"
            notificationContent = "It's time for your night routine!"
        } else {
            // No notification to show
            return
        }

        // Create notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.bell)
            .setContentTitle(notificationTitle)
            .setContentText(notificationContent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        // Show notification
        notificationManager.notify(1, notification)
    }
}