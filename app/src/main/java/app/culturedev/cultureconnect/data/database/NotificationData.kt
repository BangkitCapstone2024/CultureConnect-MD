package app.culturedev.cultureconnect.data.database

import androidx.room.Entity

@Entity(tableName = "notification_table")
data class NotificationData(
    val title: String,
    val body: String,
    val imageUrl: String? = null,
    val timestamp: Long
)
