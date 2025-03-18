package be.marche.pinpoint.data

import be.marche.pinpoint.entity.Category
import be.marche.pinpoint.entity.Item

sealed class ResultState<T : Any> {
    // Using X to show that the generic type is not linked to the sealed class
    class Loading<X : Any> : ResultState<X>()
    class Success<T : Any>(val body: T?) : ResultState<T>()
    class Error<T : Any>(val errorMessage: String) : ResultState<T>()
    class Exception<T : Any>(val exception: java.lang.Exception) : ResultState<T>()
}

data class NewsState(
    val articleList: List<Item> = emptyList(),
    val nextPage: String? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

sealed class NewsResult<T>(val data: T? = null) {
    class Success<T>(data: T?) : NewsResult<T>(data)
    class Error<T>() : NewsResult<T>(null)
}

sealed interface MarsUiState {
    data class Success(val message: String) : MarsUiState
    data class Error(val message: String) : MarsUiState
    object Pending : MarsUiState
    object Loading : MarsUiState
}

sealed interface ItemUiState {
    data class Success(val items: List<Item>) : ItemUiState
    data class Error(val message: String) : ItemUiState
    object Pending : ItemUiState
    object Loading : ItemUiState
}

sealed interface CategoryUiState {
    data class Success(val items: List<Category>) : CategoryUiState
    data class Error(val message: String) : CategoryUiState
    object Pending : CategoryUiState
    object Loading : CategoryUiState
}
