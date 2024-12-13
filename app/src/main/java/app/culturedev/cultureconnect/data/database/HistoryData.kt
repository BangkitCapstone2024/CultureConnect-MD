package app.culturedev.cultureconnect.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_items")
data class HistoryData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val rating: String,
    val price: String,
    val image: String,
)

