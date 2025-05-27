package com.mess.messcartoon.network

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        ignoreUnknownKeys = true  // 忽略 JSON 中多余的字段
        explicitNulls = false     // 允许字段为 null
        coerceInputValues = true  // 自动处理默认值
        // 如果字段名是下划线，但类属性是驼峰式，可以启用此选项（但你的情况不需要）
        namingStrategy = JsonNamingStrategy.SnakeCase
    }
    /**
     * "host" to "www.copy-manga.com",
    *  "user-agent" to userAgent,
    *  "source" to "copyApp",
    *  "region" to "1",
    *  "webp" to "1",
    *  "version" to version,
    *  "authorization" to "Token $token",
    *  "platform" to "3",
     */
    @Provides
    @Singleton
    fun provideHeaderInterceptor(headerProvider: HeaderProvider): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val builder = original.newBuilder()

        headerProvider.getHeaders().forEach { (key, value) ->
            builder.addHeader(key, value)
        }

        chain.proceed(builder.build())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
