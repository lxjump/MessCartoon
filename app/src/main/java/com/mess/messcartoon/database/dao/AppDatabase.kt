package com.mess.messcartoon.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mess.messcartoon.database.ListTypeConverters
import com.mess.messcartoon.model.ComicReader


@Database(entities = [ComicReader::class], version = 1)
@TypeConverters(ListTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun comicReaderDao(): ComicReaderDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "comic_reader_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}