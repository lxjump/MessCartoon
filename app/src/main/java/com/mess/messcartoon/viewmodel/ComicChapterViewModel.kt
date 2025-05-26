package com.mess.messcartoon.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.model.Chapter
import com.mess.messcartoon.model.ComicChapterDetail
import com.mess.messcartoon.model.ComicDetail
import com.mess.messcartoon.model.ComicReader
import com.mess.messcartoon.repository.ComicReaderRepository
import com.mess.messcartoon.repository.ComicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicChapterViewModel @Inject constructor(
    private val repository: ComicRepository,
    private val comicReaderRepository: ComicReaderRepository
) : ViewModel() {


    private var pathWord = ""
    private var uuid = ""

    // UI 状态
    sealed class UiState {
        data object Loading : UiState()
        data class Success(val data: ComicChapterDetail) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(
        UiState.Loading
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var _chapterDetail = MutableStateFlow<Chapter?>(null)
    var chapterDetail = _chapterDetail.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage

    fun showToast(message: String) {
        _toastMessage.value = message
    }

    private var _comicReader = MutableStateFlow<ComicReader?>(null)
    var comicReader = _comicReader.asStateFlow()

    fun loadData(path: String, uuid: String) {
        viewModelScope.launch {
            fetchComicChapterDetail(path, uuid)
        }
    }

    private suspend fun fetchComicChapterDetail(path: String, uuid: String) {
        LogUtils.d("fetchComicChapterDetail")
        _uiState.value = UiState.Loading
        val params = mapOf(
            "platform" to 3
        )
        repository.fetchComicChapterDetail(path, uuid, params)
            .onSuccess { value: ComicChapterDetail ->
                LogUtils.d(value)
                _chapterDetail.value = value.chapter
                _uiState.value = UiState.Success(value)
                // 获取数据成功，先更新数据库该章节的阅读进度,index = 0 第一页
                updateComicReader(path, uuid, 1)
            }
            .onFailure { exception ->
                _uiState.value = UiState.Error(exception.message ?: "未知错误")
            }
    }

    fun updateComicReader(path: String, uuid: String, index: Int) {
        LogUtils.d("updateComicReader $path, $uuid, $index")
        viewModelScope.launch {
            comicReaderRepository.updateProgress(path, uuid, index)
        }
    }

}