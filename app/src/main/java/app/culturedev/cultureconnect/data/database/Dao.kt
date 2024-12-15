package app.culturedev.cultureconnect.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface Dao {

    @Query("UPDATE cafe_table SET isFavorite = :isFavorite WHERE id = :cafeId")
    suspend fun updateFavoriteStatus(cafeId: String, isFavorite: Boolean)

    @Query("SELECT EXISTS(SELECT * FROM cafe_table WHERE id = :cafeId AND isFavorite = 1)")
    fun isFavorite(cafeId: String): Boolean

    @Query("SELECT * FROM cafe_table WHERE isFavorite = 1")
    fun getAllFavorites(): LiveData<List<DataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: DataEntity)

    @Query("SELECT * FROM cafe_table WHERE isHistory = 1")
    fun getAllHistory(): LiveData<List<DataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: DataEntity)
}
