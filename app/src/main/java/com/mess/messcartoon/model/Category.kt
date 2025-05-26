package com.mess.messcartoon.model
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName
@Serializable
data class Category(
    @SerialName("ordering")
    val ordering: List<CategoryOrdering>,
    @SerialName("theme")
    val theme: List<CategoryTheme>,
    @SerialName("top")
    val top: List<CategoryTop>
)

@Serializable
data class CategoryOrdering(
    @SerialName("name")
    val name: String,
    @SerialName("path_word")
    @SerializedName("path_word")
    val pathWord: String,
    var order: Boolean = false
)

@Serializable
data class CategoryTheme(
    @SerialName("color_h5")
    @SerializedName("color_h5")
    val colorH5: String?,
    @SerialName("count")
    val count: Int,
    @SerialName("initials")
    val initials: Int,
    @SerialName("logo")
    val logo: String?,
    @SerialName("name")
    val name: String,
    @SerialName("path_word")
    @SerializedName("path_word")
    val pathWord: String
)

@Serializable
data class CategoryTop(
    @SerialName("name")
    val name: String,
    @SerialName("path_word")
    @SerializedName("path_word")
    val pathWord: String
)


