package com.mess.messcartoon.database

import android.content.Context
import com.mess.messcartoon.database.dao.AppDatabase
import com.mess.messcartoon.database.dao.ComicReaderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)

    @Provides
    fun provideComicReaderDao(db: AppDatabase): ComicReaderDao =
        db.comicReaderDao()
}