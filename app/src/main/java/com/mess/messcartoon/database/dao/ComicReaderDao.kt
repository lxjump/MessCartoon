package com.mess.messcartoon.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mess.messcartoon.model.ComicReader

@Dao
interface ComicReaderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: ComicReader)

    // 获取浏览记录
    @Query("SELECT * FROM comic_reader ORDER BY lastBrowserTime DESC")
    suspend fun getAll(): List<ComicReader>

    @Query("SELECT * FROM comic_reader WHERE pathWord = :pathWord")
    suspend fun getBook(pathWord: String): ComicReader?

    // 删除浏览记录
    @Query("DELETE FROM comic_reader WHERE pathWord = :pathWord")
    suspend fun delete(pathWord: String)

    @Query("UPDATE comic_reader SET lastReadTime = :lastReadTime WHERE pathWord = :pathWord")
    suspend fun update(pathWord: String, lastReadTime: Long = System.currentTimeMillis())

    // 更新阅读进度
    @Query("UPDATE comic_reader SET lastReadChapter = :chapter, lastReadPage = :page, lastReadTime = :lastReadTime WHERE pathWord = :pathWord")
    suspend fun updateProgress(pathWord: String, chapter: String, page: Int, lastReadTime: Long)

    // 更新书架状态
    @Query("UPDATE comic_reader SET isInShelf = :isInShelf, addToShelfTime = :addToShelfTime WHERE pathWord = :pathWord")
    suspend fun updateShelfStatus(pathWord: String, isInShelf: Boolean, addToShelfTime: Long)

    // 获取书架列表
    @Query("SELECT * FROM comic_reader WHERE isInShelf = 1 ORDER BY addToShelfTime DESC")
    suspend fun getShelfBooks(): List<ComicReader>

    // 获取阅读记录
    @Query("SELECT * FROM comic_reader WHERE lastReadTime != 0 ORDER BY lastReadTime DESC")
    suspend fun getReadingHistory(): List<ComicReader>

}