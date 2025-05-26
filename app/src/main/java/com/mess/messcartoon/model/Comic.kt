package com.mess.messcartoon.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    @SerialName(value = "title")
    val title: String, // 假设这里属性名与 JSON 字段名不同
    @SerialName(value = "series")
    val series: String?,
    @SerialName(value = "journal")
    val journal: String,
    @SerialName(value = "cover")
    val cover: String,
    @SerialName(value = "period")
    val period: String,
    @SerialName(value = "type")
    val type: Int,
    @SerialName(value = "brief")
    val brief: String,
    @SerialName(value = "path_word")
    @SerializedName(value = "path_word")
    val pathWord: String,
    @SerialName(value = "datetime_created")
    @SerializedName(value = "datetime_created")
    val datetimeCreated: String
)

@Serializable
data class Topics(
    @SerialName(value = "list")
    val list: List<Topic>,
    @SerialName(value = "total")
    val total: Int,
    @SerialName(value = "limit")
    val limit: Int,
    @SerialName(value = "offset")
    val offset: Int
)

@Serializable
data class TopicList(
    @SerialName(value = "list")
    val list: List<Topic>,
    @SerialName(value = "total")
    val total: Int,
    @SerialName(value = "limit")
    val limit: Int,
    @SerialName(value = "offset")
    val offset: Int
)

@Serializable
data class RecComic(
    @SerialName(value = "type")
    val type: Int,
    @SerialName(value = "comic")
    val comic: Comic
)

@Serializable
data class Comic(
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "females")
    val females: List<Females> = emptyList(),
    @SerialName(value = "males")
    val males: List<Males> = emptyList(),
    @SerialName(value = "theme")
    val theme: List<Theme> = emptyList(),
    @SerialName(value = "path_word")
    @SerializedName(value = "path_word")
    val pathWord: String,
    @SerialName(value = "author")
    val author: List<Author>,
    @SerialName(value = "img_type")
    @SerializedName(value = "img_type")
    val imgType: Int,
    @SerialName(value = "cover")
    val cover: String,
    @SerialName(value = "popular")
    val popular: Int,
    @SerialName(value = "datetime_updated")
    @SerializedName(value = "datetime_updated")
    val datetimeUpdated: String?,
    @SerialName(value = "free_type")
    val freeType: FreeType?,
)

@Serializable
data class Females(
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "path_word")
    @SerializedName(value = "path_word")
    val pathWord: String,
    @SerialName(value = "gender")
    val gender: Int
)

@Serializable
data class Males(
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "path_word")
    @SerializedName(value = "path_word")
    val pathWord: String,
    @SerialName(value = "gender")
    val gender: Int
)


@Serializable
data class Theme(
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "path_word")
    @SerializedName(value = "path_word")
    val pathWord: String
)

@Serializable
data class Author(
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "path_word")
    @SerializedName(value = "path_word")
    val pathWord: String
)

@Serializable
data class RankComic(
    @SerialName(value = "sort")
    val sort: Int,
    @SerialName(value = "sort_last")
    @SerializedName(value = "sort_last")
    val sortLast: Int,
    @SerialName(value = "rise_sort")
    @SerializedName(value = "rise_sort")
    val riseSort: Int,
    @SerialName(value = "rise_num")
    @SerializedName(value = "rise_num")
    val riseNum: Int,
    @SerialName(value = "date_type")
    @SerializedName(value = "date_type")
    val dateType: Int,
    @SerialName(value = "popular")
    val popular: Int,
    @SerialName(value = "comic")
    val comic: Comic
)

@Serializable
data class RankComics(
    @SerialName(value = "list")
    val list: List<RankComic>,
    @SerialName(value = "total")
    val total: Int,
    @SerialName(value = "limit")
    val limit: Int,
    @SerialName(value = "offset")
    val offset: Int
)

@Serializable
data class HotComic(
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "datetime_created")
    @SerializedName(value = "datetime_created")
    val datetimeCreated: String,
    @SerialName(value = "comic")
    val comic: Comic
)

@Serializable
data class HotComics(
    @SerialName(value = "list")
    val list: List<Comic>,
    @SerialName(value = "total")
    val total: Int,
    @SerialName(value = "limit")
    val limit: Int,
    @SerialName(value = "offset")
    val offset: Int,
)

@Serializable
data class NewComic(
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "datetime_created")
    @SerializedName(value = "datetime_created")
    val datetimeCreated: String,
    @SerialName(value = "comic")
    val comic: Comic
)


@Serializable
data class NewComics(
    @SerialName(value = "list")
    val list: List<NewComic>,
    @SerialName(value = "total")
    val total: Int,
    @SerialName(value = "limit")
    val limit: Int,
    @SerialName(value = "offset")
    val offset: Int,
)

@Serializable
data class FinishComics(
    @SerialName(value = "list")
    val list: List<Comic>,
    @SerialName(value = "total")
    val total: Int,
    @SerialName(value = "limit")
    val limit: Int,
    @SerialName(value = "offset")
    val offset: Int,
    @SerialName(value = "path_word")
    @SerializedName(value = "path_word")
    val pathWord: String,
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "type")
    val type: String
)

@Serializable
data class FreeType(
    @SerialName(value = "display")
    val display: String,
    @SerialName(value = "value")
    val value: Int
)

@Serializable
data class Banner(
    @SerialName(value = "type")
    val type: Int,
    @SerialName(value = "cover")
    val cover: String,
    @SerialName(value = "brief")
    val brief: String,
    @SerialName(value = "out_uuid")
    @SerializedName(value = "out_uuid")
    val outUuid: String,
    @SerialName(value = "comic")
    val comic: Comic?
)

@Serializable
data class HomeData(
    @SerialName(value = "topics")
    val topics: Topics,
    @SerialName(value = "topicsList")
    val topicsList: TopicList,
    @SerialName(value = "recComics")
    val recComics: RecComics,
    @SerialName(value = "rankDayComics")
    val rankDayComics: RankComics,
    @SerialName(value = "rankWeekComics")
    val rankWeekComics: RankComics,
    @SerialName(value = "rankMonthComics")
    val rankMonthComics: RankComics,
    @SerialName(value = "hotComics")
    val hotComics: List<HotComic>,
    @SerialName(value = "newComics")
    val newComics: List<NewComic>,
    @SerialName(value = "finishComics")
    val finishComics: FinishComics,
    @SerialName(value = "banners")
    val banners: List<Banner>
)

@Serializable
data class RecComics(
    @SerialName(value = "list")
    val list: List<RecComic>,
    @SerialName(value = "total")
    val total: Int,
    @SerialName(value = "limit")
    val limit: Int,
    @SerialName(value = "offset")
    val offset: Int
)