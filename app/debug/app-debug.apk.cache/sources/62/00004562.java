package com.mess.messcartoon.database;

import com.mess.messcartoon.database.dao.AppDatabase;
import com.mess.messcartoon.database.dao.ComicReaderDao;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* loaded from: classes9.dex */
public final class DatabaseModule_ProvideComicReaderDaoFactory implements Factory<ComicReaderDao> {
    private final Provider<AppDatabase> dbProvider;

    public DatabaseModule_ProvideComicReaderDaoFactory(Provider<AppDatabase> dbProvider) {
        this.dbProvider = dbProvider;
    }

    @Override // javax.inject.Provider, jakarta.inject.Provider
    public ComicReaderDao get() {
        return provideComicReaderDao(this.dbProvider.get());
    }

    public static DatabaseModule_ProvideComicReaderDaoFactory create(Provider<AppDatabase> dbProvider) {
        return new DatabaseModule_ProvideComicReaderDaoFactory(dbProvider);
    }

    public static ComicReaderDao provideComicReaderDao(AppDatabase db) {
        return (ComicReaderDao) Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideComicReaderDao(db));
    }
}