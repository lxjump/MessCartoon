package com.mess.messcartoon.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComicDetailResponse(
    @SerialName("is_banned")
    @SerializedName("is_banned")
    val isBanned: Boolean,
    @SerialName("is_lock")
    @SerializedName("is_lock")
    val isLock: Boolean,
    @SerialName("is_login")
    @SerializedName("is_login")
    val isLogin: Boolean,
    @SerialName("is_mobile_bind")
    @SerializedName("is_mobile_bind")
    val isMobileBind: Boolean,
    @SerialName("is_vip")
    @SerializedName("is_vip")
    val isVip: Boolean,
    @SerialName("comic")
    val comic: ComicDetail,
    @SerialName("popular")
    val popular: Int,
    @SerialName("groups")
    val groups: Groups
)

@Serializable
data class ComicDetail(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("b_404")
    @SerializedName("b_404")
    val b404: Boolean,
    @SerialName("b_hidden")
    @SerializedName("b_hidden")
    val bHidden: Boolean,
    @SerialName("ban")
    val ban: Int,
    @SerialName("name")
    val name: String,
    @SerialName("alias")
    val alias: String,
    @SerialName("path_word")
    @SerializedName("path_word")
    val pathWord: String,
    @SerialName("close_comment")
    @SerializedName("close_comment")
    val closeComment: Boolean,
    @SerialName("close_roast")
    @SerializedName("close_roast")
    val closeRoast: Boolean,
    @SerialName("free_type")
    @SerializedName("free_type")
    val freeType: FreeType,
    @SerialName("restrict")
    val restrict: Restrict,
    @SerialName("reclass")
    @SerializedName("reclass")
    val reclass: Reclass,
    @SerialName("females")
    val females: List<String>,
    @SerialName("males")
    val males: List<String>,
    @SerialName("clubs")
    val clubs: List<String>,
    @SerialName("img_type")
    @SerializedName("img_type")
    val imgType: Int,
    @SerialName("seo_baidu")
    @SerializedName("seo_baidu")
    val seoBaidu: String,
    @SerialName("region")
    val region: Region,
    @SerialName("status")
    val status: Status,
    @SerialName("author")
    val author: List<Author>,
    @SerialName("theme")
    val theme: List<Theme>,
    @SerialName("parodies")
    val parodies: List<String>,
    @SerialName("brief")
    val brief: String,
    @SerialName("datetime_updated")
    @SerializedName("datetime_updated")
    val datetimeUpdated: String?,
    @SerialName("cover")
    val cover: String,
    @SerialName("last_chapter")
    @SerializedName("last_chapter")
    val lastChapter: LastChapter?,
    @SerialName("popular")
    val popular: Int
)


@Serializable
data class Restrict(
    @SerialName("value")
    val value: Int,
    @SerialName("display")
    val display: String
)

@Serializable
data class Reclass(
    @SerialName("value")
    val value: Int,
    @SerialName("display")
    val display: String
)

@Serializable
data class Region(
    @SerialName("value")
    val value: Int,
    @SerialName("display")
    val display: String
)

@Serializable
data class Status(
    @SerialName("value")
    val value: Int,
    @SerialName("display")
    val display: String
)

@Serializable
data class LastChapter(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("name")
    val name: String
)

@Serializable
data class Groups(
    @SerialName("default")
    val default: Group? = null,
    @SerialName("tankobon")
    val tankobon: Group? = null
)

@Serializable
data class Group(
    @SerialName("path_word")
    @SerializedName("path_word")
    val pathWord: String,
    @SerialName("count")
    val count: Int,
    @SerialName("name")
    val name: String
)
