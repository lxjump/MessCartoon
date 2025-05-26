package com.mess.messcartoon.network;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/* loaded from: classes8.dex */
public final class NetworkModule_ProvideRetrofitFactory implements Factory<Retrofit> {
    private final Provider<OkHttpClient> clientProvider;

    public NetworkModule_ProvideRetrofitFactory(Provider<OkHttpClient> clientProvider) {
        this.clientProvider = clientProvider;
    }

    @Override // javax.inject.Provider, jakarta.inject.Provider
    public Retrofit get() {
        return provideRetrofit(this.clientProvider.get());
    }

    public static NetworkModule_ProvideRetrofitFactory create(Provider<OkHttpClient> clientProvider) {
        return new NetworkModule_ProvideRetrofitFactory(clientProvider);
    }

    public static Retrofit provideRetrofit(OkHttpClient client) {
        return (Retrofit) Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideRetrofit(client));
    }
}