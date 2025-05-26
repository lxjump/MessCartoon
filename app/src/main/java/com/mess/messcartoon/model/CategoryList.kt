package com.mess.messcartoon.model
import ComicChapter
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class CategoryList(
    @SerialName("limit")
    val limit: Int,
    @SerialName("list")
    val list: List<CategoryComic>,
    @SerialName("offset")
    val offset: Int,
    @SerialName("total")
    val total: Int
)

@Serializable
data class CategoryComic(
    @SerialName("author")
    val author: List<Author>,
    val cType: Int,
    @SerialName("cover")
    val cover: String,
    @SerialName("females")
    val females: List<Females?>,
    @SerialName("males")
    val males: List<Males?>,
    @SerialName("name")
    val name: String,
    @SerialName("path_word")
    @SerializedName("path_word")
    val pathWord: String,
    @SerialName("popular")
    val popular: Int,
    @SerialName("theme")
    val theme: List<Theme>,
    @SerialName(value = "datetime_updated")
    @SerializedName(value = "datetime_updated")
    val datetimeUpdated: String?,
    @SerialName("free_type")
    @SerializedName("free_type")
    val freeType: FreeType,
)


