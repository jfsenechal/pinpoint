package be.marche.pinpoint.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import be.marche.pinpoint.entity.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Update
    suspend fun update(category: Category) // TODO implementation

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * FROM Category")
    fun getAll(): Flow<List<Category>>
}
