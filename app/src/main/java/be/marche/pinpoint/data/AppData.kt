package be.marche.pinpoint.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
@Serializable
data class MarsPhoto(
    val id: String,
    @SerialName(value = "img_src")
    val imgSrc: String
)