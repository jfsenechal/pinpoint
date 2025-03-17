package be.marche.pinpoint.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import be.marche.pinpoint.entity.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Item)

    @Update
    suspend fun update(note: Item)

    @Delete
    suspend fun delete(note: Item)

    @Query("SELECT * FROM Item")
    fun getAllFlow(): Flow<List<Item>>

    @Query("SELECT * FROM Item")
    fun getAll(): List<Item>
}
