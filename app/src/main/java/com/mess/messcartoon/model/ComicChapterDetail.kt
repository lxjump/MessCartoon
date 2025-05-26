package com.mess.messcartoon.model
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName
@Serializable
data class ComicChapterDetail(
    @SerialName("chapter")
    val chapter: Chapter,
    @SerialName("comic")
    val comic: Comic,
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
    @SerialName("show_app")
    @SerializedName("show_app")
    val showApp: Boolean
)

@Serializable
data class Chapter(
    @SerialName("comic_id")
    @SerializedName("comic_id")
    val comicId: String,
    @SerialName("comic_path_word")
    @SerializedName("comic_path_word")
    val comicPathWord: String,
    @SerialName("contents")
    val contents: List<Content>,
    @SerialName("count")
    val count: Int,
    @SerialName("datetime_created")
    @SerializedName("datetime_created")
    val datetimeCreated: String,
    @SerialName("group_id")
    @SerializedName("group_id")
    val groupId: String?,
    @SerialName("group_path_word")
    @SerializedName("group_path_word")
    val groupPathWord: String,
    @SerialName("img_type")
    @SerializedName("img_type")
    val imgType: Int,
    @SerialName("index")
    val index: Int,
    @SerialName("is_long")
    @SerializedName("is_long")
    val isLong: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("news")
    val news: String,
    @SerialName("next")
    val next: String?,
    @SerialName("ordered")
    val ordered: Int,
    @SerialName("prev")
    val prev: String?,
    @SerialName("size")
    val size: Int,
    @SerialName("type")
    val type: Int,
    @SerialName("uuid")
    val uuid: String
)

//@Serializable
//data class Comic(
//    @SerialName("name")
//    val name: String,
//    @SerialName("path_word")
//    @SerializedName("path_word")
//    val pathWord: String,
//    @SerialName("restrict")
//    val restrict: Restrict,
//    @SerialName("uuid")
//    val uuid: String
//)

@Serializable
data class Content(
    @SerialName("url")
    val url: String
)

//@Serializable
//data class Restrict(
//    @SerialName("display")
//    val display: String,
//    @SerialName("value")
//    val value: Int
//)


