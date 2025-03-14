package be.marche.pinpoint.database

import androidx.room.Database
import androidx.room.RoomDatabase
import be.marche.pinpoint.entity.Category
import be.marche.pinpoint.entity.Item

@Database(entities = [Item::class, Category::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun categoryDao(): CategoryDao
}