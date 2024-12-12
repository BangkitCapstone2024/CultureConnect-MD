package app.culturedev.cultureconnect.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(cafe: List<DataEntity>)

    @Query("UPDATE cafe_table SET isFavorite = :isFavorite WHERE id = :cafeId")
    suspend fun updateFavoriteStatus(cafeId: String, isFavorite: Boolean)

    @Query("SELECT * FROM cafe_table ORDER BY id DESC")
    fun getAllFavorites(): LiveData<List<DataEntity>>

    @Query("SELECT * FROM cafe_table WHERE isFavorite = 1")
    fun getFavorite(): LiveData<List<DataEntity>>

    @Query("SELECT EXISTS(SELECT * FROM cafe_table WHERE id = :cafeId AND isFavorite = 1)")
    suspend fun isFavorite(cafeId: String): Boolean
}
