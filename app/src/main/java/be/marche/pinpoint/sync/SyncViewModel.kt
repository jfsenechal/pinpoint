package be.marche.pinpoint.sync

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.pinpoint.data.Coordinates
import be.marche.pinpoint.data.DataResponse
import be.marche.pinpoint.data.MarsUiState
import be.marche.pinpoint.database.CategoryDao
import be.marche.pinpoint.database.ItemDao
import be.marche.pinpoint.entity.Category
import be.marche.pinpoint.entity.Item
import be.marche.pinpoint.helper.FileHelper
import be.marche.pinpoint.network.ItemApi
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.annotation.KoinViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
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

        Log.d("ZEZE", "start")
        syncUiState = MarsUiState.Loading
        val fileHelper = FileHelper()
        items.forEach { item ->

            Log.d("ZEZE", "item ${item.id}")
            val imgFile: File?
            try {
                Log.d("ZEZE", "try uri file ${item.imageUrl}")
                imgFile = File(item.imageUrl)
            } catch (e: Exception) {
                syncUiState = MarsUiState.Error("${e.message}")
                Log.d("ZEZE", "item error file ${e.message}")
                //  results.add(NotificationState.Error("${e.message}"))
                return@forEach
            }

            val requestBody = fileHelper.createRequestBody(imgFile)
            val part = fileHelper.createPart(imgFile, requestBody)
            val coordinates = Coordinates(item.latitude, item.longitude)
            val response =
                ItemApi.retrofitService.insertItemNotSuspend(coordinates, part, requestBody)

            Log.d("ZEZE", "item response finish ${response.toString()}")
            syncUiState = MarsUiState.Success("Success: insert items")
        }
        syncUiState = MarsUiState.Success("Success: insert items")

    }

    fun treatment(item: Item) {
        val gson = Gson()

        val coordinates = Coordinates(item.latitude, item.longitude)
        val coordinatesJson = gson.toJson(coordinates)
        val coordinatesPart =
            coordinatesJson.toRequestBody("application/json; charset=utf-8".toMediaType())

        val file = File(item.imageUrl)
        val requestFile = file.asRequestBody("image/*".toMediaType())
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

        // imageUrl (or whatever string you need)
        val imageUrlPart = item.imageUrl.toRequestBody("text/plain".toMediaType())

        val call = ItemApi.retrofitService.insertItemNotSuspend(
            coordinatesPart,
            filePart,
            imageUrlPart
        )

        call.enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                if (response.isSuccessful) {
                    // Handle success
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                // Handle failure
            }
        })

    }
}