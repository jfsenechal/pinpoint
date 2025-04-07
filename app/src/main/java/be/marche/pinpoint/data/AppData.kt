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

data class DataResponse(
    val error: Int,
    val message: String,
    val item: Item,
)

sealed class NotificationState {
    class Success(val message: String) : NotificationState()
    class Error(val message: String) : NotificationState()
}

data class Coordinates
    (
    val latitude: Double,
    val longitude: Double
)