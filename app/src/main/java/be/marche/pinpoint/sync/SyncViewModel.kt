package be.marche.pinpoint.sync

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.pinpoint.data.Coordinates
import be.marche.pinpoint.data.MarsUiState
import be.marche.pinpoint.database.CategoryDao
import be.marche.pinpoint.database.ItemDao
import be.marche.pinpoint.entity.Category
import be.marche.pinpoint.entity.Item
import be.marche.pinpoint.helper.FileHelper
import be.marche.pinpoint.network.ItemApi
import be.marche.pinpoint.network.ItemApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel
import retrofit2.HttpException
import java.io.File
import java.io.IOException

@KoinViewModel
class SyncViewModel(
    private val categoryDao: CategoryDao,
    private val itemDao: ItemDao,
) :
    ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var syncUiState: MarsUiState by mutableStateOf(MarsUiState.Pending)
        private set

    var categories: List<Category> by mutableStateOf(emptyList())
    var items: List<Item> by mutableStateOf(emptyList())

    fun loadItems() {
        viewModelScope.launch {
            items = withContext(Dispatchers.IO) {
                itemDao.getAllDrafts()
            }
        }
    }

    fun sync2() {
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

    fun sync() {

        syncUiState = MarsUiState.Loading
        val fileHelper = FileHelper()
        items.forEach { item ->

            val imgFile: File?
            try {
                imgFile = File(item.imageUrl)
            } catch (e: Exception) {
                MarsUiState.Error("${e.message}")
                //  results.add(NotificationState.Error("${e.message}"))
                return@forEach
            }

            val requestBody = fileHelper.createRequestBody(imgFile)
            val part = fileHelper.createPart(imgFile, requestBody)
            val coordinates = Coordinates(item.latitude, item.longitude)
            val response = ItemApi.retrofitService.insertItemNotSuspend(coordinates, part, requestBody)
        }
        MarsUiState.Success("Success: insert items")

    }
}