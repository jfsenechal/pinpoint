package be.marche.pinpoint.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.pinpoint.database.CategoryDao
import be.marche.pinpoint.entity.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

open class CategoryViewModel(private val categoryDao: CategoryDao) : ViewModel() {

    val categories: Flow<List<Category>> = categoryDao.getAllFlow()
    fun addCategory(category: Category) {
        viewModelScope.launch {
            categoryDao.insert(category)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryDao.delete(category)
        }
    }
}