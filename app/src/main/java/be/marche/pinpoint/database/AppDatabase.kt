package be.marche.pinpoint.database

import androidx.room.Database
import androidx.room.RoomDatabase
import be.marche.pinpoint.entity.Category
import be.marche.pinpoint.entity.Comment
import be.marche.pinpoint.entity.Item

@Database(
    entities = [Item::class, Category::class, Comment::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun categoryDao(): CategoryDao
}