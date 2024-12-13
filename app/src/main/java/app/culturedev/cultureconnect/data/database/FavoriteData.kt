package app.culturedev.cultureconnect.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_items")
data class FavoriteData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val rating: String,
    val price: String,
    val image: String
)

