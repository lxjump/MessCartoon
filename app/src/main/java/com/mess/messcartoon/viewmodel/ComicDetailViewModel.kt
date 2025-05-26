package com.mess.messcartoon.viewmodel

import ChapterDefault
import ComicChapter
import ComicChapterDefault
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.model.Comic
import com.mess.messcartoon.model.ComicChapterTankobon
import com.mess.messcartoon.model.ComicDetail
import com.mess.messcartoon.model.ComicReader
import com.mess.messcartoon.model.HomeListItem
import com.mess.messcartoon.repository.ComicReaderRepository
import com.mess.messcartoon.repository.ComicRepository
import com.mess.messcartoon.ui.views.FetchStatus
import com.mess.messcartoon.viewmodel.HomeViewModel.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ComicDetailViewModel @Inject constructor(
    private val repository: ComicRepository,
    private val comicReaderRepository: ComicReaderRepository
) :
    ViewModel() {

    private var _comicDetail = MutableStateFlow<ComicDetail?>(null)
    var comicDetail = _comicDetail.asStateFlow()

    //
    private val _tabsToShow = MutableStateFlow<List<String>>(emptyList())
    val tabsToShow: StateFlow<List<String>> = _tabsToShow

    // 是否加载
    private var _chapterListLoaded = MutableStateFlow(FetchStatus.Loading)
    var chapterListLoaded = _chapterListLoaded.asStateFlow()

    private var _loadedSuccess = MutableStateFlow(false)
    var loadedSuccess = _loadedSuccess.asStateFlow()

    private var chapterDefaultLoaded = FetchStatus.Loading
    private var chapterTankobonLoaded = FetchStatus.Loading

    private val _comicChapterDefaultList = mutableStateListOf<ComicChapter>()
    val comicChapterDefaultList: SnapshotStateList<ComicChapter> = _comicChapterDefaultList

    private var currentChapterDefaultOffset = 0;

    private val _comicChapterTankobonList = mutableStateListOf<ComicChapter>()
    val comicChapterTankobonList: SnapshotStateList<ComicChapter> = _comicChapterTankobonList
    private var currentChapterTankobonOffset = 0;

    private var limit = 100;

    private var pathWord = ""


    private var _comicReader = MutableStateFlow<ComicReader?>(null)
    var comicReader = _comicReader.asStateFlow()


    // UI 状态
    sealed class UiState {
        object Loading : UiState()
        data class Success(val data: ComicDetail) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    fun loadData(pathWord: String) {
        LogUtils.d("loadData")
        this.pathWord = pathWord
        viewModelScope.launch {
            _comicReader.value = comicReaderRepository.getBook(pathWord)
            loadComicDetailData(pathWord)
        }
    }

    private suspend fun loadComicDetailData(pathWord: String) {
        _uiState.value = UiState.Loading
        val params = mapOf(
            "platform" to 3
        )
        repository.fetchComicDetail(pathWord, params)
            .onSuccess { result ->
                _comicDetail.value = result.comic
                _uiState.value = UiState.Success(result.comic)
                val tabs = mutableListOf<String>()
                if (result.groups.default != null) {
                    loadChapterDefault(pathWord)
                    tabs.add(result.groups.default.name)
                    _loadedSuccess.value = true
                } else {
                    chapterDefaultLoaded = FetchStatus.FetchSuccess
                }
                if (result.groups.tankobon != null) {
                    loadChapterTankobon(pathWord)
                    tabs.add(result.groups.tankobon.name)
                } else {
                    chapterTankobonLoaded = FetchStatus.FetchSuccess
                }
                _tabsToShow.value = tabs
//                _chapterListLoaded.value = chapterDefaultLoaded.status && chapterTankobonLoaded.status
                if (chapterDefaultLoaded == FetchStatus.FetchSuccess && chapterTankobonLoaded == FetchStatus.FetchSuccess) {
                    _chapterListLoaded.value = FetchStatus.FetchSuccess
                }

                if (_comicReader.value == null) {
                    _comicReader.value = ComicReader(
                        pathWord = result.comic.pathWord,
                        name = result.comic.name,
                        cover = result.comic.cover,
                        datetimeUpdated = result.comic.datetimeUpdated ?: "未知",
                        updateState = result.comic.status.value,
                        lastReadChapterTitle = if (result.comic.lastChapter != null) result.comic.lastChapter.name else "未知",
                        themes = result.comic.theme,
                        authors = result.comic.author,
                        lastBrowserTime = System.currentTimeMillis()
                    ).also {
                        comicReaderRepository.insert(it)
                    }
                } else {
                    comicReaderRepository.update(pathWord)
                }
            }
            .onFailure { exception ->
                _uiState.value = UiState.Error("${exception.message}")
                _loadedSuccess.value = false
            }
    }

    fun retryFetChapters() {
        viewModelScope.launch {
            loadChapterDefault(pathWord)
            loadChapterTankobon(pathWord)
        }
    }

    private suspend fun loadChapterDefault(pathWord: String) {
        val params = mapOf(
            "limit" to limit,
            "offset" to currentChapterDefaultOffset,
            "platform" to 3
        )
        repository.fetchComicChapterDefault(pathWord, params)
            .onSuccess { result ->
                _comicChapterDefaultList.addAll(result.list)
                currentChapterDefaultOffset += result.list.size
                // 判断是否需要继续加载
                if (currentChapterDefaultOffset < result.total) {
                    loadChapterDefault(pathWord) // 递归加载下一页
                } else {
                    chapterDefaultLoaded = FetchStatus.FetchSuccess
                    if (chapterDefaultLoaded == FetchStatus.FetchSuccess && chapterTankobonLoaded == FetchStatus.FetchSuccess) {
                        _chapterListLoaded.value = FetchStatus.FetchSuccess
                    }
                }
            }
            .onFailure { exception ->
                LogUtils.e("章节加载异常 ${exception.message}")
                _chapterListLoaded.value = FetchStatus.FetchFailed
            }
    }


    private suspend fun loadChapterTankobon(pathWord: String) {
        val params = mapOf(
            "limit" to limit,
            "offset" to currentChapterTankobonOffset,
            "platform" to 3
        )
        repository.fetchComicChapterTankobon(pathWord, params)
            .onSuccess { result ->
                _comicChapterTankobonList.addAll(result.list)
                currentChapterTankobonOffset += result.list.size
                // 判断是否需要继续加载
                if (currentChapterTankobonOffset < result.total) {
                    loadChapterTankobon(pathWord) // 递归加载下一页
                } else {
                    chapterTankobonLoaded = FetchStatus.FetchSuccess
                    if (chapterDefaultLoaded == FetchStatus.FetchSuccess && chapterTankobonLoaded == FetchStatus.FetchSuccess) {
                        _chapterListLoaded.value = FetchStatus.FetchSuccess
                    }
                }
            }
            .onFailure { exception ->
                LogUtils.e("单行本加载异常 ${exception.message}")
                _chapterListLoaded.value = FetchStatus.FetchFailed
            }
    }

    fun addToShelf() {
        viewModelScope.launch {
            comicReaderRepository.updateShelfStatus(pathWord, true)
            _comicReader.value = comicReaderRepository.getBook(pathWord)
        }
    }

    fun removeFromShelf() {
        viewModelScope.launch {
            comicReaderRepository.updateShelfStatus(pathWord, false)
            _comicReader.value = comicReaderRepository.getBook(pathWord)
        }
    }

    fun isReading(chapter: ComicChapter): Boolean {
        return if (_comicReader.value?.lastReadChapter == chapter.uuid) {
            true
        } else if (_comicReader.value?.lastReadChapter == "" && chapter.index == 0){
            true
        } else {
            false
        }
    }

    fun updateComicReader() {
        LogUtils.d("updateComicReader")
        viewModelScope.launch {
            comicReaderRepository.update(pathWord)
        }
    }
}