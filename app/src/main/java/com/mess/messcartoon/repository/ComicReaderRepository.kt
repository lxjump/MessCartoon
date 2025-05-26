package com.mess.messcartoon.repository

import com.mess.messcartoon.model.ComicReader
import com.mess.messcartoon.database.dao.ComicReaderDao
import java.util.Date
import javax.inject.Inject

class ComicReaderRepository @Inject constructor(private val comicReaderDao: ComicReaderDao) {
    suspend fun insert(book: ComicReader) = comicReaderDao.insert(book)
    suspend fun getAll() = comicReaderDao.getAll()
    suspend fun getBook(pathWord: String) = comicReaderDao.getBook(pathWord)
    suspend fun delete(pathWord: String) = comicReaderDao.delete(pathWord)
    suspend fun update(pathWord: String) = comicReaderDao.update(pathWord)
    suspend fun updateProgress(
        pathWord: String,
        chapter: String,
        page: Int,
        timestamp: Long = System.currentTimeMillis()
    ) =
        comicReaderDao.updateProgress(pathWord, chapter, page, timestamp)

    suspend fun updateShelfStatus(
        pathWord: String,
        isInShelf: Boolean,
        addToShelfTime: Long = System.currentTimeMillis()
    ) =
        comicReaderDao.updateShelfStatus(pathWord, isInShelf, addToShelfTime)

    suspend fun getShelfBooks() = comicReaderDao.getShelfBooks()

    suspend fun getReadingHistory() = comicReaderDao.getReadingHistory()
}
