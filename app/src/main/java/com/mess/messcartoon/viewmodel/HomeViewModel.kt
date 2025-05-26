package com.mess.messcartoon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.model.Banner
import com.mess.messcartoon.model.HomeData
import com.mess.messcartoon.model.HomeListItem
import com.mess.messcartoon.repository.ComicRepository
import com.mess.messcartoon.ui.views.DetailType
import com.mess.messcartoon.ui.views.RankType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ComicRepository
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    // UI 状态
    sealed class UiState {
        object Loading : UiState()
        data class Success(val data: List<HomeListItem>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            repository.fetchHome()
                .onSuccess {
                    value: HomeData ->
                    val list = convertHomeToItems(value)
                    _uiState.value = UiState.Success(list)
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "未知错误")
                }
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadData()
            _isRefreshing.value = false
        }
    }

    /**
     * 过滤类型 为 1 的banner
     */
    private fun filterBannerType(banners: List<Banner>): List<Banner> {
        return banners.filter { it.type == 1 }
    }

    private fun convertHomeToItems(home: HomeData): List<HomeListItem> {
        val items = mutableListOf<HomeListItem>()
        // 添加 Banner
//        items.add(HomeListItem.BannerItem(home.banners))
        items.add(HomeListItem.BannerItem(filterBannerType(home.banners)))
        // 添加推荐漫画
        LogUtils.d("home ${home.recComics}")
        items.add(HomeListItem.RecommendComicItem(home.recComics))
        // 添加话题漫画
        items.add(HomeListItem.TopicsComicItem(home.topicsList))
        // 热门更新
        items.add(HomeListItem.HotComicItem(home.hotComics))
        // 新版
        items.add(HomeListItem.NewComicItem(home.newComics))
        // 完结
        items.add(HomeListItem.FinishedComicItem(home.finishComics))
        // 添加日榜、周榜、月榜
        items.add(HomeListItem.RankDayComicItem(home.rankDayComics))
        items.add(HomeListItem.RankWeekComicItem(home.rankWeekComics))
        items.add(HomeListItem.RankMonthComicItem(home.rankMonthComics))
        return items
    }


}