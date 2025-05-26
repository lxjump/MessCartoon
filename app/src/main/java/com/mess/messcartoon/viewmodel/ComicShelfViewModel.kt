package com.mess.messcartoon.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.model.ComicReader
import com.mess.messcartoon.repository.ComicReaderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicShelfViewModel @Inject constructor(private val comicReaderRepository: ComicReaderRepository): ViewModel() {

    private val _comicShelfList = mutableStateListOf<ComicReader>()
    val comicShelfList: SnapshotStateList<ComicReader> = _comicShelfList

    fun loadData() {
        LogUtils.d("读取数据库")
        viewModelScope.launch {
            _comicShelfList.clear()
            val list:List<ComicReader> = comicReaderRepository.getShelfBooks()
            LogUtils.d("list -> $list")
            _comicShelfList.addAll(list)
            LogUtils.d("_comicShelfList -> $_comicShelfList")
        }
    }

    fun deleteItem(item: ComicReader) {
        viewModelScope.launch {
            LogUtils.d("删除 item $item")
            comicReaderRepository.delete(item.pathWord)
            _comicShelfList.remove(item)
        }
    }

}