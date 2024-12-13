package app.culturedev.cultureconnect.firebase

import app.culturedev.cultureconnect.data.database.NotificationData
import app.culturedev.cultureconnect.ui.notification.NotificationActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle FCM messages when the app is in the foreground
        remoteMessage.notification?.let {
            val title = it.title
            val body = it.body
            val imageUrl = remoteMessage.data["imageUrl"]  // Extract image URL from the data payload
            val timestamp = remoteMessage.data["timestamp"]?.toLong() ?: 0L  // Extract timestamp

            // Send the notification to the NotificationActivity
            sendNotification(title, body, imageUrl, timestamp)
        }
    }

    private fun sendNotification(title: String?, body: String?, imageUrl: String?, timestamp: Long) {
        val notificationData = NotificationData(title ?: "", body ?: "", imageUrl, timestamp)
        val activity = applicationContext as? NotificationActivity
        activity?.addNotification(notificationData)
    }
}
