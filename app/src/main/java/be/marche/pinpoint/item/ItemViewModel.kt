package be.marche.pinpoint.item

import android.location.Location
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.pinpoint.data.ItemUiState
import be.marche.pinpoint.database.ItemDao
import be.marche.pinpoint.entity.Item
import be.marche.pinpoint.helper.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
open class ItemViewModel(private val itemDao: ItemDao) : ViewModel() {

    val fileUri = mutableStateOf<Uri>(Uri.EMPTY)
    val location = mutableStateOf<Location?>(null)

    val itemsFlow: Flow<List<Item>> = itemDao.getAllFlow()

    var itemUiState: ItemUiState by mutableStateOf(ItemUiState.Loading)
        private set

    var items: List<Item> by mutableStateOf(emptyList())

    fun loadItems() {
        itemUiState = ItemUiState.Loading
        viewModelScope.launch {
            itemUiState = try {
                items = withContext(Dispatchers.IO) {
                    itemDao.getAll()
                }
                ItemUiState.Success(items)
            } catch (e: Exception) {
                ItemUiState.Error(e.toString())
            }
        }
    }

    fun addItem(location: Location, description: String?) {

        val created = DateUtils.dateToday(true);
        val item = Item(
            latitude = location.latitude,
            longitude = location.longitude,
            imageUrl = fileUri.value.toString(),
            description = description,
            category_id = 1,
            createdAt = created
        )
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }
}