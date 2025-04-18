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
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM Item")
    fun getAllFlow(): Flow<List<Item>>

    @Query("SELECT * FROM Item")
    fun getAll(): List<Item>

    @Query("SELECT * FROM Item WHERE idServer = 0")
    fun getAllDrafts(): List<Item>
}
