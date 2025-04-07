package be.marche.pinpoint.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(tableName = "item", foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"]
        )
    ])
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var idServer: Int = 0,
    var latitude: Double,
    var longitude: Double,
    var imageUrl: String,
    val category_id: Int,
    val description: String? = null,
    val createdAt: String
)

@Entity
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val idServer: Int,
    val itemId: Int,
    val content: String,
    val createdAt: String
)