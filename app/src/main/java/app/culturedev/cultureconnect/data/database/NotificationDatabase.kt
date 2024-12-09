package app.culturedev.cultureconnect.data.database

import androidx.room.Database
import app.culturedev.cultureconnect.data.remote.api.ApiService
import app.culturedev.cultureconnect.data.response.NotificationRes

@Database(entities = [NotificationRes::class], version = 1)
abstract class NotificationDatabase {
    abstract fun notificationDao(): ApiService
}