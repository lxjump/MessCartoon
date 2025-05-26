package com.mess.messcartoon.network;

import dagger.Module;
import dagger.Provides;
import java.util.Map;
import javax.inject.Singleton;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonBuilder;
import kotlinx.serialization.json.JsonKt;
import kotlinx.serialization.json.JsonNamingStrategy;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/* compiled from: NetworkModule.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nH\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000eH\u0007J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0011H\u0007R\u0017\u0010\u0004\u001a\u00020\u0005¢\u0006\u000e\n\u0000\u0012\u0004\b\u0006\u0010\u0003\u001a\u0004\b\u0007\u0010\b¨\u0006\u0016"}, d2 = {"Lcom/mess/messcartoon/network/NetworkModule;", "", "<init>", "()V", "json", "Lkotlinx/serialization/json/Json;", "getJson$annotations", "getJson", "()Lkotlinx/serialization/json/Json;", "provideHeaderInterceptor", "Lokhttp3/Interceptor;", "headerProvider", "Lcom/mess/messcartoon/network/HeaderProvider;", "provideOkHttpClient", "Lokhttp3/OkHttpClient;", "interceptor", "provideRetrofit", "Lretrofit2/Retrofit;", "client", "provideApiService", "Lcom/mess/messcartoon/network/ApiService;", "retrofit", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = 48)
@Module
/* loaded from: classes8.dex */
public final class NetworkModule {
    public static final NetworkModule INSTANCE = new NetworkModule();
    private static final Json json = JsonKt.Json$default(null, new Function1() { // from class: com.mess.messcartoon.network.NetworkModule$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            Unit json$lambda$0;
            json$lambda$0 = NetworkModule.json$lambda$0((JsonBuilder) obj);
            return json$lambda$0;
        }
    }, 1, null);
    public static final int $stable = 8;

    public static /* synthetic */ void getJson$annotations() {
    }

    private NetworkModule() {
    }

    public final Json getJson() {
        return json;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit json$lambda$0(JsonBuilder Json) {
        Intrinsics.checkNotNullParameter(Json, "$this$Json");
        Json.setIgnoreUnknownKeys(true);
        Json.setExplicitNulls(false);
        Json.setCoerceInputValues(true);
        Json.setNamingStrategy(JsonNamingStrategy.Builtins.getSnakeCase());
        return Unit.INSTANCE;
    }

    @Provides
    @Singleton
    public final Interceptor provideHeaderInterceptor(final HeaderProvider headerProvider) {
        Intrinsics.checkNotNullParameter(headerProvider, "headerProvider");
        return new Interceptor() { // from class: com.mess.messcartoon.network.NetworkModule$$ExternalSyntheticLambda0
            @Override // okhttp3.Interceptor
            public final Response intercept(Interceptor.Chain chain) {
                Response provideHeaderInterceptor$lambda$2;
                provideHeaderInterceptor$lambda$2 = NetworkModule.provideHeaderInterceptor$lambda$2(HeaderProvider.this, chain);
                return provideHeaderInterceptor$lambda$2;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Response provideHeaderInterceptor$lambda$2(HeaderProvider $headerProvider, Interceptor.Chain chain) {
        Intrinsics.checkNotNullParameter(chain, "chain");
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        Map $this$forEach$iv = $headerProvider.getHeaders();
        for (Map.Entry element$iv : $this$forEach$iv.entrySet()) {
            String key = element$iv.getKey();
            String value = element$iv.getValue();
            builder.addHeader(key, value);
        }
        return chain.proceed(builder.build());
    }

    @Provides
    @Singleton
    public final OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        Intrinsics.checkNotNullParameter(interceptor, "interceptor");
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    @Singleton
    public final Retrofit provideRetrofit(OkHttpClient client) {
        Intrinsics.checkNotNullParameter(client, "client");
        Retrofit build = new Retrofit.Builder().baseUrl(Config.INSTANCE.getBASE_URL()).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        return build;
    }

    @Provides
    @Singleton
    public final ApiService provideApiService(Retrofit retrofit) {
        Intrinsics.checkNotNullParameter(retrofit, "retrofit");
        Object create = retrofit.create(ApiService.class);
        Intrinsics.checkNotNullExpressionValue(create, "create(...)");
        return (ApiService) create;
    }
}