package be.marche.pinpoint.data

import androidx.compose.runtime.Immutable

import androidx.compose.ui.graphics.Color

@Immutable
data class Account(
    val name: String,
    val number: Int,
    val balance: Float,
    val color: Color
)