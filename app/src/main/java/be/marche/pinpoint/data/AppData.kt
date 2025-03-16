package be.marche.pinpoint.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import be.marche.pinpoint.entity.Item

//import kotlinx.serialization.Serializable

@Immutable
data class Account(
    val name: String,
    val number: Int,
    val balance: Float,
    val color: Color
)

/**
 * This data class defines a Mars photo which includes an ID, and the image URL.
 */
//@Serializable
data class MarsPhoto(
    val id: Int,
    val name: String,
    val imgSrc: String
)

data class RemoteNewsResponse(
    val articles: List<Item>,
    val status: String,
    val totalResults: Int
)