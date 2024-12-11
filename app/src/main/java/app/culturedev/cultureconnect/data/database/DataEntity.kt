package app.culturedev.cultureconnect.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cafe_table")
data class DataEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: String,

    @field:ColumnInfo(name = "image")
    val image: String? = null,

    @field:ColumnInfo(name = "name")
    val name: String? = null,

    @field:ColumnInfo(name = "rating")
    val rating: String? = null,

    @field:ColumnInfo(name = "category")
    val category:  String? = null,

    @field:ColumnInfo(name = "address")
    val address: String? = null,

    @field:ColumnInfo(name = "phoneNumber")
    val phoneNumber: String? = null,

    @field:ColumnInfo(name = "price")
    val price: String? = null,

    @field:ColumnInfo(name = "schedule")
    val schedule: String? = null,

    @field:ColumnInfo(name = "menu")
    val menu: String? = null,

    @field:ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,

    @field:ColumnInfo(name = "status")
    val status: Boolean = false,
)