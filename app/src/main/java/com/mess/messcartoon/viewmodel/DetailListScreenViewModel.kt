package com.mess.messcartoon.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.R
import com.mess.messcartoon.model.Comic
import com.mess.messcartoon.model.FinishComics

import com.mess.messcartoon.model.HomeData
import com.mess.messcartoon.model.HomeListItem
import com.mess.messcartoon.model.HotComics
import com.mess.messcartoon.model.NewComic
import com.mess.messcartoon.model.NewComics
import com.mess.messcartoon.model.RankComic
import com.mess.messcartoon.model.RecComics
import com.mess.messcartoon.repository.ComicRepository
import com.mess.messcartoon.ui.views.DetailType
import com.mess.messcartoon.viewmodel.HomeViewModel.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailListScreenViewModel @Inject constructor(
    private val repository: ComicRepository
): ViewModel() {

    private val _items = mutableStateListOf<Comic>()
    val items: List<Comic> = _items

    private var currentPage = 0
    private val pageSize = 21
    var isEndReached = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    private var loadMore = false

    var loadMoreErrorState = mutableStateOf<String?>(null)

    // UI 状态
    sealed class UiState {
        data object Loading : UiState()
        data class Success(val data: List<Comic>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

//    init {
//        loadData()
//    }

    fun loadData(type: DetailType, loadMore: Boolean = false) {
        if (isLoading.value || isEndReached.value) return
        this.loadMore = loadMore
        viewModelScope.launch {
            isLoading.value = true
            if (!loadMore) {
                _uiState.value = UiState.Loading
            }
            when (type) {                    // 2 如果颜色等于枚举常量，返回对应的字符串
                DetailType.Recommend -> fetchRecommendComicData()
                DetailType.Category -> TODO("fetch Category Data")
                DetailType.New -> fetchNewestComicData()
                DetailType.Finished -> fetchFinishedComicData()
                DetailType.Hot -> fetchHotComicData()
            }
        }
    }

    private suspend fun fetchRecommendComicData() {
        val params = mapOf(
            "pos" to 3200102,
            "limit" to 21,
            "offset" to currentPage * 21,
            "platform" to 3
        )
        repository.fetchRecComicList(params)
            .onSuccess { result ->
                val newItems = result.toRecommendComicList()

                if (newItems.isEmpty()) {
                    isEndReached.value = true // 无更多数据
                } else {
                    _items.addAll(newItems)
                    currentPage++
                }
                LogUtils.d("new size = ${_items.size}")
                isLoading.value = false
                if (!loadMore) {
                    _uiState.value = UiState.Success(_items)
                }
            }
            .onFailure { exception ->
                // 加载更多，失败状态
                if (loadMore) {
                    loadMoreErrorState.value = exception.message
                } else {
                    // 首次加载异常
                    _uiState.value = UiState.Error(exception.message ?: "未知错误")
                }
                isLoading.value = false
            }
    }

    private suspend fun fetchNewestComicData() {
        val params = mapOf(
            "limit" to 21,
            "offset" to currentPage * 21,
            "platform" to 3
        )
        repository.fetchNewestComicList(params)
            .onSuccess { result ->
                val newItems = result.toNewestComicList()

                if (newItems.isEmpty()) {
                    isEndReached.value = true // 无更多数据
                } else {
                    _items.addAll(newItems)
                    currentPage++
                }
                LogUtils.d("new size = ${_items.size}")
                if (!loadMore) {
                    _uiState.value = UiState.Success(_items)
                }
                isLoading.value = false
            }
            .onFailure { exception ->
                // 加载更多，失败状态
                if (loadMore) {
                    loadMoreErrorState.value = exception.message
                } else {
                    // 首次加载异常
                    _uiState.value = UiState.Error(exception.message ?: "未知错误")
                }
            }
    }

    private suspend fun fetchHotComicData() {
        val params = mapOf(
            "limit" to 21,
            "offset" to currentPage * 21,
            "platform" to 3
        )
        repository.fetchHotComicList(params)
            .onSuccess { result ->
                LogUtils.d(result)
                val newItems = result.toHottestComicList()

                if (newItems.isEmpty()) {
                    isEndReached.value = true // 无更多数据
                } else {
                    _items.addAll(newItems)
                    currentPage++
                }
                LogUtils.d("new size = ${_items.size}")
                if (!loadMore) {
                    _uiState.value = UiState.Success(_items)
                }
                isLoading.value = false
            }
            .onFailure { exception ->
                // 加载更多，失败状态
                if (loadMore) {
                    loadMoreErrorState.value = exception.message
                } else {
                    // 首次加载异常
                    _uiState.value = UiState.Error(exception.message ?: "未知错误")
                }
            }
    }

    private suspend fun fetchFinishedComicData() {
        val params = mapOf(
            "limit" to 21,
            "offset" to currentPage * 21,
            "ordering" to "-datetime-updated",
            "top" to "finish",
            "platform" to 3
        )
        repository.fetchFinishedComicList(params)
            .onSuccess { list ->
                val newItems = list.toComicList()

                if (newItems.isEmpty()) {
                    isEndReached.value = true // 无更多数据
                } else {
                    _items.addAll(newItems)
                    currentPage++
                }
                LogUtils.d("new size = ${_items.size}")
                if (!loadMore) {
                    _uiState.value = UiState.Success(_items)
                }
                isLoading.value = false
            }
            .onFailure { exception ->
                // 加载更多，失败状态
                isLoading.value = false
                if (loadMore) {
                    loadMoreErrorState.value = exception.message
                } else {
                    // 首次加载异常
                    _uiState.value = UiState.Error(exception.message ?: "未知错误")
                }
            }
    }



    private fun NewComics.toNewestComicList(): List<Comic> {
        return this.list.map { it.comic }
    }

    private fun HotComics.toHottestComicList(): List<Comic> {
        return this.list
    }

    private fun FinishComics.toComicList(): List<Comic> {
        return this.list
    }

    private fun RecComics.toRecommendComicList(): Collection<Comic> {
        return list.map { it.comic }
    }


}