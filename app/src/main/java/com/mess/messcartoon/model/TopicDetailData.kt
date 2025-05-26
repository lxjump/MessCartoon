package com.mess.messcartoon.model
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class TopicDetailData(
    @SerialName("limit")
    val limit: Int,
    @SerialName("list")
    val list: List<TopicData>,
    @SerialName("offset")
    val offset: Int,
    @SerialName("total")
    val total: Int
)

@Serializable
data class TopicData(
    @SerialName("author")
    val author: List<Author>,
    @SerialName("c_type")
    @SerializedName("c_type")
    val cType: Int,
    @SerialName("cover")
    val cover: String,
    @SerialName("females")
    val females: List<Females?>,
    @SerialName("img_type")
    @SerializedName("img_type")
    val imgType: Int,
    @SerialName("males")
    val males: List<Males?>,
    @SerialName("name")
    val name: String,
    @SerialName("parodies")
    val parodies: List<String>,
    @SerialName("path_word")
    @SerializedName("path_word")
    val pathWord: String,
    @SerialName("popular")
    val popular: Int,
    @SerialName("theme")
    val theme: List<Theme>,
    @SerialName("type")
    val type: Int
)


