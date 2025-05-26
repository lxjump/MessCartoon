import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName
@Serializable
data class ChapterDefault(
    @SerialName("limit")
    val limit: Int,
    @SerialName("list")
    val list: List<ComicChapter>,
    @SerialName("offset")
    val offset: Int,
    @SerialName("total")
    val total: Int
)


@Serializable
data class ComicChapter(
    @SerialName("comic_id")
    @SerializedName("comic_id")
    val comicId: String,
    @SerialName("comic_path_word")
    @SerializedName("comic_path_word")
    val comicPathWord: String,
    @SerialName("count")
    val count: Int,
    @SerialName("datetime_created")
    @SerializedName("datetime_created")
    val datetimeCreated: String,
    @SerialName("group_id")
    @SerializedName("group_id")
    val groupId: String? = null,
    @SerialName("group_path_word")
    @SerializedName("group_path_word")
    val groupPathWord: String,
    @SerialName("img_type")
    @SerializedName("img_type")
    val imgType: Int,
    @SerialName("index")
    val index: Int,
    @SerialName("name")
    val name: String,
    @SerialName("news")
    val news: String,
    @SerialName("next")
    val next: String?,
    @SerialName("ordered")
    val ordered: Int,
    @SerialName("prev")
    val prev: String,
    @SerialName("size")
    val size: Int,
    @SerialName("type")
    val type: Int,
    @SerialName("uuid")
    val uuid: String
)

@Serializable
data class ComicChapterDefault(
    @SerialName("comic_id")
    @SerializedName("comic_id")
    val comicId: String,
    @SerialName("comic_path_word")
    @SerializedName("comic_path_word")
    val comicPathWord: String,
    @SerialName("count")
    val count: Int,
    @SerialName("datetime_created")
    @SerializedName("datetime_created")
    val datetimeCreated: String,
    @SerialName("group_id")
    @SerializedName("group_id")
    val groupId: String? = null,
    @SerialName("group_path_word")
    @SerializedName("group_path_word")
    val groupPathWord: String,
    @SerialName("img_type")
    @SerializedName("img_type")
    val imgType: Int,
    @SerialName("index")
    val index: Int,
    @SerialName("name")
    val name: String,
    @SerialName("news")
    val news: String,
    @SerialName("next")
    val next: String?,
    @SerialName("ordered")
    val ordered: Int,
    @SerialName("prev")
    val prev: String,
    @SerialName("size")
    val size: Int,
    @SerialName("type")
    val type: Int,
    @SerialName("uuid")
    val uuid: String
)

