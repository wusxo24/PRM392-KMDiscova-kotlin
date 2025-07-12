package com.example.kmd.di.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.kmd.MainActivity
import com.example.kmd.R
import javax.inject.Inject

class NotificationManager @Inject constructor(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val CART_CHANNEL_ID = "cart_notifications"
        const val CART_NOTIFICATION_ID = 1001
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CART_CHANNEL_ID,
                "Cart Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for cart items"
            }
            notificationManager.createNotificationChannel(channel)
            Log.d("NotificationManager", "Notification channel created: $CART_CHANNEL_ID")
        }
    }

    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // For older versions, notifications are enabled by default
        }
    }

    fun showCartNotification(itemCount: Int) {
        try {
            Log.d("NotificationManager", "Creating cart notification for $itemCount items")
            
            if (!hasNotificationPermission()) {
                Log.w("NotificationManager", "Notification permission not granted")
                return
            }
            
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("navigate_to_cart", true)
            }

            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(context, CART_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Cart Items Available")
                .setContentText("You have $itemCount item(s) in your cart")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(CART_NOTIFICATION_ID, notification)
            Log.d("NotificationManager", "Cart notification sent successfully")
        } catch (e: Exception) {
            Log.e("NotificationManager", "Error showing cart notification", e)
        }
    }

    fun cancelCartNotification() {
        try {
            notificationManager.cancel(CART_NOTIFICATION_ID)
            Log.d("NotificationManager", "Cart notification cancelled")
        } catch (e: Exception) {
            Log.e("NotificationManager", "Error cancelling cart notification", e)
        }
    }
}

