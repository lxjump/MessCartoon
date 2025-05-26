package com.mess.messcartoon.repository;

import com.mess.messcartoon.network.ApiService;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* loaded from: classes5.dex */
public final class ComicRepository_Factory implements Factory<ComicRepository> {
    private final Provider<ApiService> apiServiceProvider;

    public ComicRepository_Factory(Provider<ApiService> apiServiceProvider) {
        this.apiServiceProvider = apiServiceProvider;
    }

    @Override // javax.inject.Provider, jakarta.inject.Provider
    public ComicRepository get() {
        return newInstance(this.apiServiceProvider.get());
    }

    public static ComicRepository_Factory create(Provider<ApiService> apiServiceProvider) {
        return new ComicRepository_Factory(apiServiceProvider);
    }

    public static ComicRepository newInstance(ApiService apiService) {
        return new ComicRepository(apiService);
    }
}