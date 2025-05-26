package com.mess.messcartoon.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.model.Category
import com.mess.messcartoon.model.CategoryComic
import com.mess.messcartoon.model.CategoryList
import com.mess.messcartoon.model.CategoryOrdering
import com.mess.messcartoon.model.CategoryTheme
import com.mess.messcartoon.model.CategoryTop
import com.mess.messcartoon.model.ComicChapterDetail
import com.mess.messcartoon.model.ComicDetail
import com.mess.messcartoon.repository.ComicRepository
import com.mess.messcartoon.viewmodel.ComicDetailViewModel.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: ComicRepository) : ViewModel() {


    sealed class UiState {
        data object Loading : UiState()
        data class Success(val data: CategoryList) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _categoryTheme = mutableStateListOf<CategoryTheme>()
    val categoryTheme: SnapshotStateList<CategoryTheme> = _categoryTheme

    private val _categoryTop = mutableStateListOf<CategoryTop>()
    val categoryTop: SnapshotStateList<CategoryTop> = _categoryTop

    private val _categoryOrder = mutableStateListOf<CategoryOrdering>()
    val categoryOrder: SnapshotStateList<CategoryOrdering> = _categoryOrder

    private val _categoryList = mutableStateListOf<CategoryComic>()
    val categoryList: SnapshotStateList<CategoryComic> = _categoryList

    private var _isEndReached = MutableStateFlow(false)
    var isEndReached = _isEndReached.asStateFlow()
    private var _isLoading = MutableStateFlow(false)
    var isLoading = _isLoading.asStateFlow()
    private var _isLoadMoreError = MutableStateFlow(false)
    var isLoadMoreError = _isLoadMoreError.asStateFlow()

    private var _orderIndex = MutableStateFlow(0)
    var orderIndex = _orderIndex.asStateFlow()
    private var _topIndex = MutableStateFlow(0)
    var topIndex = _topIndex.asStateFlow()
    private var _themeIndex = MutableStateFlow(0)
    var themeIndex = _themeIndex.asStateFlow()

    private var category: Category? = null;

    private val allTheme = CategoryTheme(
        name = "全部",
        initials = 0,
        colorH5 = "",
        count = 0,
        logo = "",
        pathWord = ""
    )
    private val allTop = CategoryTop(name = "全部", pathWord = "")

//    private var orderIndex = 0
//    private var topIndex = 0
//    private var themeIndex = 0

    private var currentPage = 0

    fun loadData() {
        if (_categoryTheme.isNotEmpty() || _categoryTop.isNotEmpty() || _categoryOrder.isNotEmpty()) {
            return
        }
        if (_categoryTheme.isEmpty()) {
            _categoryTheme.add(allTheme)
        }
        if (_categoryTop.isEmpty()) {
            _categoryTop.add(allTop)
        }
        viewModelScope.launch {
            fetchComicCategories()
        }
    }

    private suspend fun fetchComicCategories() {
        LogUtils.d("fetchComicCategories")
        val params = mapOf(
            "type" to 1,
            "platform" to 3
        )
        repository.fetchComicCategories(params)
            .onSuccess { value: Category ->
                LogUtils.d(value)
                _categoryTheme.addAll(value.theme)
                _categoryTop.addAll(value.top)

                value.ordering.forEachIndexed { index, item ->
                    if (index == 0) {
                        item.order = true
                    }
                }
                category = value
                _categoryOrder.addAll(value.ordering)
                fetchComicCategoryList(0, "-datetime_updated", "", "")
            }
            .onFailure { exception ->
                LogUtils.d("fetchComicCategories error")
                _uiState.value = UiState.Error(exception.message ?: "未知错误")
            }
    }

    private suspend fun fetchComicCategoryList(offset: Int, ordering: String, theme: String, top: String, loadMore: Boolean = false, reload: Boolean = false) {
        if (!loadMore && !reload) {
            currentPage = 0
            _uiState.value = UiState.Loading
        }

        _isLoading.value = true
        val params = mapOf(
            "limit" to 18,
            "offset" to currentPage * 18,
            "free_type" to 1,
            "ordering" to ordering,
            "theme" to theme,
            "top" to top,
            "platform" to 3
        )
        repository.fetchComicCategoryList(params)
            .onSuccess { value: CategoryList ->
                LogUtils.d(value)
                if (value.list.isEmpty()) {
                    _isEndReached.value = true // 无更多数据
                } else {
                    if (loadMore) {
                        _categoryList.addAll(value.list)
                    } else {
                        _categoryList.clear()
                        _categoryList.addAll(value.list)
                    }
                    currentPage++
                }
                _uiState.value = UiState.Success(value)
                _isLoading.value = false
            }
            .onFailure { exception ->
                LogUtils.d("fetchComicCategories error")
                if (loadMore) {
                    _isLoadMoreError.value = true
                } else {
                    _uiState.value = UiState.Error(exception.message ?: "未知错误")
                }
                _isLoading.value = false
            }
    }

    fun changeTheme(index: Int) {
        LogUtils.d("changeTheme $index")
        viewModelScope.launch {
            _themeIndex.value = index
            val currentOrder = _categoryOrder[_orderIndex.value]
            LogUtils.d("currentOrder : $currentOrder")
            var currentOrderString = currentOrder.pathWord
            val currentTop = _categoryTop[_topIndex.value]
            val currentTheme = _categoryTheme[_themeIndex.value]
            if (currentOrder.order) {
                currentOrderString = "-$currentOrderString"
            }
            LogUtils.d("theme: $currentTheme, currentTop: $currentTop, currentOrder: $currentOrderString")
            fetchComicCategoryList(0, currentOrderString, currentTheme.pathWord, currentTop.pathWord)
        }
    }

    fun changeTop(index: Int) {
        viewModelScope.launch {
            _topIndex.value = index
            val currentOrder = _categoryOrder[_orderIndex.value]
            var currentOrderString = currentOrder.pathWord
            val currentTop = _categoryTop[_topIndex.value]
            val currentTheme = _categoryTheme[_themeIndex.value]
            if (currentOrder.order) {
                currentOrderString = "-$currentOrderString"
            }
            fetchComicCategoryList(0, currentOrderString, currentTheme.pathWord, currentTop.pathWord)
        }
    }

    fun changeOrder(index: Int) {
        LogUtils.d("changeOrder _orderIndex: ${_orderIndex.value}, topIndex ${_topIndex.value}, themeIndex ${_themeIndex.value}")
        LogUtils.d("_categoryOrder : ${_categoryOrder.toList()}")
        viewModelScope.launch {
            val currentOrder = _categoryOrder[index]
            if (_orderIndex.value == index) {
                currentOrder.order = !currentOrder.order
            } else {
                _orderIndex.value = index
            }
            var currentOrderString = currentOrder.pathWord
            val currentTop = _categoryTop[_topIndex.value]
            val currentTheme = _categoryTheme[_themeIndex.value]
            if (currentOrder.order) {
                currentOrderString = "-$currentOrderString"
            }
            LogUtils.d("theme: $currentTheme, currentTop: $currentTop, currentOrder: $currentOrderString")
            fetchComicCategoryList(0, currentOrderString, currentTheme.pathWord, currentTop.pathWord)
        }
    }

    fun loadMore() {
        _isLoadMoreError.value = false
        viewModelScope.launch {
            val currentOrder = _categoryOrder[_orderIndex.value]
            var currentOrderString = currentOrder.pathWord
            val currentTop = _categoryTop[_topIndex.value]
            val currentTheme = _categoryTheme[_themeIndex.value]
            if (currentOrder.order) {
                currentOrderString = "-$currentOrderString"
            }
            fetchComicCategoryList(0, currentOrderString, currentTheme.pathWord, currentTop.pathWord, loadMore = true)
        }
    }

    fun reload() {
        LogUtils.d("reload _orderIndex: ${_orderIndex.value}, topIndex ${_topIndex.value}, themeIndex ${_themeIndex.value}")
        if (_categoryOrder.isEmpty() || _categoryTheme.isEmpty() || _categoryTop.isEmpty()) {
            if (_categoryTheme.isEmpty()) {
                _categoryTheme.add(allTheme)
            }
            if (_categoryTop.isEmpty()) {
                _categoryTop.add(allTop)
            }
            viewModelScope.launch {
                fetchComicCategories()
            }
            return
        }
        viewModelScope.launch {
            val currentOrder = _categoryOrder[_orderIndex.value]
            var currentOrderString = currentOrder.pathWord
            LogUtils.d("currentOrderString : $currentOrderString")
            val currentTop = _categoryTop[_topIndex.value]
            val currentTheme = _categoryTheme[_themeIndex.value]
            if (currentOrder.order) {
                currentOrderString = "-$currentOrderString"
            }
            LogUtils.d("theme: $currentTheme, currentTop: $currentTop, currentOrder: $currentOrderString")
            val reload = category != null
            fetchComicCategoryList(0, currentOrderString, currentTheme.pathWord, currentTop.pathWord, reload = reload)
        }
    }

}