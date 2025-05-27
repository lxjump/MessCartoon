package com.mess.messcartoon.network

import ChapterDefault
import com.blankj.utilcode.util.LogUtils
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mess.messcartoon.model.ApiResponse
import com.mess.messcartoon.model.Category
import com.mess.messcartoon.model.CategoryList
import com.mess.messcartoon.model.ChapterTankobon
import com.mess.messcartoon.model.Comic
import com.mess.messcartoon.model.ComicChapterDetail
import com.mess.messcartoon.model.ComicDetail
import com.mess.messcartoon.model.ComicDetailResponse
import com.mess.messcartoon.model.FinishComics
import com.mess.messcartoon.model.HomeData
import com.mess.messcartoon.model.HotComic
import com.mess.messcartoon.model.HotComics
import com.mess.messcartoon.model.NewComic
import com.mess.messcartoon.model.NewComics
import com.mess.messcartoon.model.RankComic
import com.mess.messcartoon.model.RankComics
import com.mess.messcartoon.model.RecComics
import com.mess.messcartoon.model.TopicDetailData
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {
    @GET("comics")
    suspend fun getComics(): List<Comic>

    @GET("api/v3/h5/homeIndex2?platform=3")
    suspend fun fetchHome(): ApiResponse<HomeData>

    @GET("api/v3/recs")
    suspend fun fetchRecComicList(
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<RecComics>

    @GET("api/v3/update/newest")
    suspend fun fetchNewestComicList(
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<NewComics>

    @GET("api/v3/comics")
    suspend fun fetchFinishedComicList(
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<FinishComics>

    @GET("api/v3/comics")
    suspend fun fetchHotComicList(
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<HotComics>

    @GET("api/v3/ranks")
    suspend fun fetchRankComicList(
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<RankComics>


    @GET("api/v3/comic2/{pathWord}")
    suspend fun fetchComicDetail(
        @Path("pathWord") pathWord: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<ComicDetailResponse>


    @GET("api/v3/topic/{pathWord}/contents")
    suspend fun fetchComicTopicList(
        @Path("pathWord") pathWord: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<TopicDetailData>

    @GET("api/v3/comic/{pathWord}/group/default/chapters")
    suspend fun fetchComicChapterDefault(
        @Path("pathWord") pathWord: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<ChapterDefault>


    @GET("api/v3/comic/{pathWord}/group/tankobon/chapters")
    suspend fun fetchComicChapterTankobon(
        @Path("pathWord") pathWord: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<ChapterTankobon>

    @GET("api/v3/comic/{pathWord}/chapter/{uuid}")
    suspend fun fetchComicChapterDetail(
        @Path("pathWord") pathWord: String,
        @Path("uuid") uuid: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<ComicChapterDetail>

    @GET("api/v3/h5/filter/comic/tags")
    suspend fun fetchComicCategories(
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<Category>


    @GET("api/v3/comics")
    suspend fun fetchComicCategoryList(
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<CategoryList>



    companion object {

        // 创建拦截器实例
        // 创建 OkHttpClient


        @OptIn(ExperimentalSerializationApi::class)
        fun create(headerInterceptor: Interceptor): ApiService {
            LogUtils.d("ApiService create")

            val json = Json {
                ignoreUnknownKeys = true  // 忽略 JSON 中多余的字段
                explicitNulls = false     // 允许字段为 null
                coerceInputValues = true  // 自动处理默认值
                // 如果字段名是下划线，但类属性是驼峰式，可以启用此选项（但你的情况不需要）
                 namingStrategy = JsonNamingStrategy.SnakeCase
            }
            // 创建 OkHttpClient
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://www.copy-manga.com/")
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}