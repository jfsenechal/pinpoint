package be.marche.pinpoint.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.pinpoint.data.MarsUiState
import be.marche.pinpoint.network.ItemApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
class MarsViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        fetchCategories()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    fun fetchCategories() {
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading
            marsUiState = try {
                val listResult = ItemApi.retrofitService.fetchCategories()
                MarsUiState.Success(
                    "Success: ${listResult.size} categories retrieved"
                )
            } catch (e: IOException) {
                MarsUiState.Error(e.toString())
            } catch (e: HttpException) {
                MarsUiState.Error(e.toString())
            } catch (e: Exception) {
                MarsUiState.Error(e.toString())
            }
        }
    }
}