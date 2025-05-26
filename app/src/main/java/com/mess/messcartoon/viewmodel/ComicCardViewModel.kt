package com.mess.messcartoon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ComicCardViewModel @Inject constructor() : ViewModel() {

//    private val _navigateToComicDetail = MutableSharedFlow<Pair<String, String>>()
//    val navigateToComicDetail = _navigateToComicDetail.asSharedFlow()
//
//    fun comicDetailClick(name: String, pathWord: String) {
//        viewModelScope.launch {
//            _navigateToComicDetail.emit(name to pathWord)
//        }
//    }
}