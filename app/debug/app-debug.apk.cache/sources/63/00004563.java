package com.mess.messcartoon.database;

import android.content.Context;
import com.mess.messcartoon.database.dao.AppDatabase;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* loaded from: classes9.dex */
public final class DatabaseModule_ProvideDatabaseFactory implements Factory<AppDatabase> {
    private final Provider<Context> contextProvider;

    public DatabaseModule_ProvideDatabaseFactory(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    @Override // javax.inject.Provider, jakarta.inject.Provider
    public AppDatabase get() {
        return provideDatabase(this.contextProvider.get());
    }

    public static DatabaseModule_ProvideDatabaseFactory create(Provider<Context> contextProvider) {
        return new DatabaseModule_ProvideDatabaseFactory(contextProvider);
    }

    public static AppDatabase provideDatabase(Context context) {
        return (AppDatabase) Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideDatabase(context));
    }
}