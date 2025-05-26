package com.mess.messcartoon.network;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/* loaded from: classes8.dex */
public final class NetworkModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
    private final Provider<Interceptor> interceptorProvider;

    public NetworkModule_ProvideOkHttpClientFactory(Provider<Interceptor> interceptorProvider) {
        this.interceptorProvider = interceptorProvider;
    }

    @Override // javax.inject.Provider, jakarta.inject.Provider
    public OkHttpClient get() {
        return provideOkHttpClient(this.interceptorProvider.get());
    }

    public static NetworkModule_ProvideOkHttpClientFactory create(Provider<Interceptor> interceptorProvider) {
        return new NetworkModule_ProvideOkHttpClientFactory(interceptorProvider);
    }

    public static OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        return (OkHttpClient) Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOkHttpClient(interceptor));
    }
}