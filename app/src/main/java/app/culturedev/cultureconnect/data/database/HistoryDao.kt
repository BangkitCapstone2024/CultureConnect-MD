package app.culturedev.cultureconnect.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history_items")
    fun getAllHistory(): LiveData<List<HistoryData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryData)

    @Query("SELECT * FROM history_items WHERE title LIKE '%' || :query || '%'")
    fun searchHistory(query: String): LiveData<List<HistoryData>>
}
