package be.marche.pinpoint.sync

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.pinpoint.database.CategoryDao
import be.marche.pinpoint.entity.Category
import be.marche.pinpoint.network.ItemApi
import be.marche.pinpoint.viewModel.MarsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel
import retrofit2.HttpException
import java.io.IOException

@KoinViewModel
class SyncViewModel(private val categoryDao: CategoryDao) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var syncUiState: MarsUiState by mutableStateOf(MarsUiState.Pending)
        private set

    var categories: List<Category> by mutableStateOf(emptyList())

    fun init() {

    }

    fun sync() {
        viewModelScope.launch {
            syncUiState = MarsUiState.Loading

            val categoriesFetched = ItemApi.retrofitService.fetchCategories()

            syncUiState = try {
                withContext(Dispatchers.IO) {
                    categoryDao.insertCategories(categoriesFetched)
                }
                categories = withContext(Dispatchers.IO) {
                    categoryDao.getAll()
                }
                MarsUiState.Success("Success: insert categories")
            } catch (e: IOException) {
                MarsUiState.Error(e.toString())
            } catch (e: HttpException) {
                MarsUiState.Error(e.toString())
            }
        }
    }
}