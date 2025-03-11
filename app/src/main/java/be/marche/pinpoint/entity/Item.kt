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
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String?,
    val category_id: Int,
)