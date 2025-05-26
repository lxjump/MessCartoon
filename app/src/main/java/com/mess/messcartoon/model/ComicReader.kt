package com.mess.messcartoon.model

import android.provider.Browser
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "comic_reader")
data class ComicReader(
    @PrimaryKey val pathWord: String,
    val name: String,
    val cover: String,
    var lastReadChapter: String = "",
    var lastReadChapterTitle: String = "",
    var updateState: Int =0,
    val lastReadPage: Int = 0,
    var isInShelf: Boolean = false,
    val popular: Int = 0,
    val themes: List<Theme>,
    val authors: List<Author>,
    val datetimeUpdated: String = "",
    val lastBrowserTime: Long = 0L,
    val lastReadTime: Long = 0L,
    val addToShelfTime: Long = 0L
)

