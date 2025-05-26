package com.mess.messcartoon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class HomeData(
    @SerialName("topics") val topics: PagedTopics,
    @SerialName("topicsList") val topicsList: PagedTopics,
    @SerialName("recComics") val recComics: PagedList<ComicItem>,
    @SerialName("rankDayComics") val rankDayComics: PagedList<RankComic>,
    @SerialName("rankWeekComics") val rankWeekComics: PagedList<RankComic>,
    @SerialName("rankMonthComics") val rankMonthComics: PagedList<RankComic>,
    @SerialName("hotComics") val hotComics: List<HotComic>,
    @SerialName("newComics") val newComics: List<NewComic>,
    @SerialName("finishComics") val finishComics: FinishComics,
    @SerialName("banners") val banners: List<Banner>
)

// 分页基础结构
@Serializable
data class PagedList<T>(
    @SerialName("list") val list: List<T>,
    @SerialName("total") val total: Int,
    @SerialName("limit") val limit: Int,
    @SerialName("offset") val offset: Int
)

// Topics 专用分页
@Serializable
data class PagedTopics(
    @SerialName("list") val list: List<Topic>,
    @SerialName("total") val total: Int,
    @SerialName("limit") val limit: Int,
    @SerialName("offset") val offset: Int
)

@Serializable
data class Topic(
    @SerialName("title") val title: String,
    @SerialName("series") val series: String? = null,
    @SerialName("journal") val journal: String,
    @SerialName("cover") val cover: String,
    @SerialName("period") val period: String,
    @SerialName("type") val type: Int,
    @SerialName("brief") val brief: String,
    @SerialName("path_word") val pathWord: String,
    @SerialName("datetime_created") val datetimeCreated: String
)

@Serializable
data class ComicItem(
    @SerialName("type") val type: Int,
    @SerialName("comic") val comic: Comic
)


@Serializable
data class Comic(
    @SerialName("name") val name: String,
    @SerialName("females") val females: List<Females>,
    @SerialName("males") val males: List<Males>,
    @SerialName("theme") val themes: List<Theme>,
    @SerialName("path_word") val pathWord: String,
    @SerialName("author") val authors: List<Author>,
    @SerialName("img_type") val imgType: Int,
    @SerialName("cover") val cover: String,
    @SerialName("popular") val popular: Long,
    @SerialName("datetime_updated") val datetimeUpdated: String? = null,
    @SerialName("last_chapter_name") val lastChapterName: String? = null
)

@Serializable
data class RankComic(
    @SerialName("sort") val sort: Int,
    @SerialName("sort_last") val sortLast: Int,
    @SerialName("rise_sort") val riseSort: Int,
    @SerialName("rise_num") val riseNum: Long,
    @SerialName("date_type") val dateType: Int,
    @SerialName("popular") val popular: Long,
    @SerialName("comic") val comic: Comic
)

@Serializable
data class HotComic(
    @SerialName("name") val name: String,
    @SerialName("datetime_created") val datetimeCreated: String,
    @SerialName("comic") val comic: Comic
)

@Serializable
data class NewComic(
    @SerialName("name") val name: String,
    @SerialName("datetime_created") val datetimeCreated: String,
    @SerialName("comic") val comic: Comic
)

@Serializable
data class FinishComics(
    @SerialName("list") val list: List<Comic>,
    @SerialName("total") val total: Int,
    @SerialName("limit") val limit: Int,
    @SerialName("offset") val offset: Int,
    @SerialName("path_word") val pathWord: String,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String
)

@Serializable
data class Banner(
    @SerialName("type") val type: Int,
    @SerialName("cover") val cover: String,
    @SerialName("brief") val brief: String,
    @SerialName("out_uuid") val outUuid: String,
    @SerialName("comic") val comic: BannerComic? = null
)

@Serializable
data class BannerComic(
    @SerialName("name") val name: String,
    @SerialName("path_word") val pathWord: String
)

// 嵌套对象
@Serializable
data class Theme(
    @SerialName("name") val name: String,
    @SerialName("path_word") val pathWord: String
)

@Serializable
data class Females(
    @SerialName("name") val name: String,
    @SerialName("path_word") val pathWord: String,
    @SerialName("gender") val gender: Int
)

@Serializable
data class Males(
    @SerialName("name") val name: String,
    @SerialName("path_word") val pathWord: String,
    @SerialName("gender") val gender: Int
)

@Serializable
data class Author(
    @SerialName("name") val name: String,
    @SerialName("path_word") val pathWord: String
)

@Serializable
data class FreeType(
    @SerialName("display") val display: String,
    @SerialName("value") val value: Int
)