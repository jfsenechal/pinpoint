package be.marche.pinpoint.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.pinpoint.data.CategoryUiState
import be.marche.pinpoint.database.CategoryDao
import be.marche.pinpoint.entity.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
open class CategoryViewModel(private val categoryDao: CategoryDao) : ViewModel() {

    val categoriesFlow: Flow<List<Category>> = categoryDao.getAllFlow()

    var categoryUiState: CategoryUiState by mutableStateOf(CategoryUiState.Pending)
        private set

    var categories: List<Category> by mutableStateOf(emptyList())

    var category: Category? by mutableStateOf(null)

    fun loadCategories() {
        categoryUiState = CategoryUiState.Loading
        viewModelScope.launch {
            categoryUiState = try {
                categories = withContext(Dispatchers.IO) {
                    categoryDao.getAll()
                }
                CategoryUiState.Success(categories)
            } catch (e: Exception) {
                CategoryUiState.Error(e.toString())
            }
        }
    }

    fun findCategoryById(categoryId: Int) {
        viewModelScope.launch {
            category = withContext(Dispatchers.IO) {
                categoryDao.findCategoryById(categoryId)
            }
        }
    }

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