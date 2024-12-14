package app.culturedev.cultureconnect.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_items")
    fun getAllFavorites(): LiveData<List<FavoriteData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteData)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteData)

    @Query("DELETE FROM favorite_items WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)

    @Query("SELECT * FROM favorite_items WHERE title LIKE '%' || :query || '%'")
    fun searchFavorites(query: String): LiveData<List<FavoriteData>>
}
