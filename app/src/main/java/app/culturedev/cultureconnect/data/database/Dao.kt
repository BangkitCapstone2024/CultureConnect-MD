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
    suspend fun isFavorite(cafeId: String): Boolean

    @Query("SELECT * FROM cafe_table WHERE name LIKE '%' || :query || '%' AND isFavorite = 1")
    fun searchFavorites(query: String): LiveData<List<DataEntity>>

    @Query("SELECT * FROM cafe_table WHERE name LIKE '%' || :query || '%' AND isHistory = 1")
    fun searchHistory(query: String): LiveData<List<DataEntity>>
}
