package com.mess.messcartoon.network;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import okhttp3.Interceptor;

/* loaded from: classes8.dex */
public final class NetworkModule_ProvideHeaderInterceptorFactory implements Factory<Interceptor> {
    private final Provider<HeaderProvider> headerProvider;

    public NetworkModule_ProvideHeaderInterceptorFactory(Provider<HeaderProvider> headerProvider) {
        this.headerProvider = headerProvider;
    }

    @Override // javax.inject.Provider, jakarta.inject.Provider
    public Interceptor get() {
        return provideHeaderInterceptor(this.headerProvider.get());
    }

    public static NetworkModule_ProvideHeaderInterceptorFactory create(Provider<HeaderProvider> headerProvider) {
        return new NetworkModule_ProvideHeaderInterceptorFactory(headerProvider);
    }

    public static Interceptor provideHeaderInterceptor(HeaderProvider headerProvider) {
        return (Interceptor) Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideHeaderInterceptor(headerProvider));
    }
}