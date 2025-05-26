package com.mess.messcartoon.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.R
import com.mess.messcartoon.model.Comic
import com.mess.messcartoon.model.FinishComics


import com.mess.messcartoon.model.RankComic
import com.mess.messcartoon.model.RankComics
import com.mess.messcartoon.repository.ComicRepository
import com.mess.messcartoon.ui.views.DetailType
import com.mess.messcartoon.ui.views.RankType
import com.mess.messcartoon.viewmodel.HomeViewModel.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RankViewModel @Inject constructor(
    private val repository: ComicRepository
): ViewModel() {

    private val pageSize = 21
    private var loadMore = false

    var loadMoreErrorState = mutableStateOf<String?>(null)

    // 各榜单独立状态容器
    private val states = mutableMapOf<RankType, RankState>().apply {
        RankType.entries.forEach { type ->
            put(type, RankState())
        }
    }

    // 当前选中类型
//    var selectedRankType by mutableStateOf(RankType.RankDay)
//        private set
    private val _selectedRankType = MutableStateFlow(RankType.RankDay)
    val selectedRankType: StateFlow<RankType> = _selectedRankType.asStateFlow()

    // 当前状态快捷访问
    val currentItems: List<Comic>
        get() = states[selectedRankType.value]?.items ?: emptyList()

    val currentLoading: Boolean
        get() = states[selectedRankType.value]?.isLoading ?: false

    val currentEndReached: Boolean
        get() = states[selectedRankType.value]?.isEndReached ?: true

    val currentError: String?
        get() = states[selectedRankType.value]?.error

    // 获取指定类型状态
    fun getCurrentState(type: RankType): RankState {
        return states[type] ?: RankState()
    }

    // 切换榜单类型
    fun selectRankType(type: RankType) {
        _selectedRankType.value = type
        // 首次切换时自动加载
        if (currentItems.isEmpty()) {
            loadData(type, false)
        }
    }

    // UI 状态
    sealed class UiState {
        data object Loading : UiState()
        data class Success(val data: List<Comic>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadData(type: RankType, loadMore: Boolean = false) {
        LogUtils.d("loadData $loadMore")
        val curState = states[type] ?: return
        LogUtils.d("1111111111111111111111111111111111111")
        if (curState.isLoading || curState.isEndReached) return
        LogUtils.d("2222222222222222222222222222222222222 $type")
        this.loadMore = loadMore
        curState.isLoading = true
        viewModelScope.launch {

            if (!loadMore) {
                _uiState.value = UiState.Loading
            }
            when (type) {                    // 2 如果颜色等于枚举常量，返回对应的字符串
                RankType.RankDay -> fetchRankComicData(RankType.RankDay)
                RankType.RankWeek -> fetchRankComicData(RankType.RankWeek)
                RankType.RankMonth -> fetchRankComicData(RankType.RankMonth)
                RankType.RankTotal -> fetchRankComicData(RankType.RankTotal)
            }
        }
    }

    private suspend fun fetchRankComicData(type: RankType) {
        LogUtils.d("fetchRankComicData ${type.typeName}")
        val state = states[type] ?: return
        LogUtils.d("fetchRankComicData ${state.isLoading}，${state.isEndReached}")
        val params = mapOf(
            "limit" to pageSize,
            "offset" to state.currentPage * pageSize,
            "date_type" to type.typeName,
            "audience_type" to "",
            "platform" to 3
        )
        state.isLoading= true
        repository.fetchRankComicList(params)
            .onSuccess { list ->
                LogUtils.d("list = $list")
                val newItems = list.toRankComicList()
                if (newItems.isEmpty()) {
                    LogUtils.d("newItems is null,到底了")
                    state.isEndReached = true
                } else {
                    if (loadMore) {
                        state.items.addAll(newItems)
                    } else {
                        state.items.clear()
                        state.items.addAll(newItems)
                    }
                    state.currentPage++
                }
                state.isLoading = false
                LogUtils.d("new size = ${state.items.size}")
                if (!loadMore) {
                    _uiState.value = UiState.Success(state.items)
                }
            }
            .onFailure { exception ->
                // 加载更多，失败状态
                LogUtils.d("onFailure")
                state.isLoading = false
                if (loadMore) {
                    loadMoreErrorState.value = exception.message
                } else {
                    // 首次加载异常
                    _uiState.value = UiState.Error(exception.message ?: "未知错误")
                }
            }
    }

    private fun RankComics.toRankComicList(): List<Comic> {
        return list.map { it.comic }
    }

    // 状态容器类（所有字段使用 mutableState）
    inner class RankState {
        val items = mutableStateListOf<Comic>()
        var isLoading by mutableStateOf(false)
        var currentPage by mutableStateOf(0)
        var isEndReached by mutableStateOf(false)
        var error by mutableStateOf<String?>(null)
            private set
    }

}

// 状态类
//data class RankState(
//    val items: MutableList<Comic> = mutableStateListOf(),
//    var currentPage: Int = 0,
//    var isLoading: Boolean = false,
//    var isEndReached: Boolean = false,
//    var error: String? = null
//
//)