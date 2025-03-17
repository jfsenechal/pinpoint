package be.marche.pinpoint.item

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.pinpoint.database.ItemDao
import be.marche.pinpoint.entity.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
open class ItemViewModel(private val itemDao: ItemDao) : ViewModel() {

    val fileUri = mutableStateOf<Uri>(Uri.EMPTY)

    val items: Flow<List<Item>> = itemDao.getAll()

    fun addItem(item: Item) {
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