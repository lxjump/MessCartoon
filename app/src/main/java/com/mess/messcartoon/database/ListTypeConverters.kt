package com.mess.messcartoon.database
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.mess.messcartoon.model.Author
import com.mess.messcartoon.model.Theme
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlin.reflect.KClass

@ProvidedTypeConverter
class ListTypeConverters {

    inline fun <reified T> listToJson(value: List<T>): String {
        return Json.encodeToString(value)
    }

    inline fun <reified T> jsonToList(value: String): List<T> {
        return runCatching {
            Json.decodeFromString<List<T>>(value)
        }.getOrElse { emptyList() }
    }

    companion object {
        val instance = ListTypeConverters()

        // 用于 Room 自动识别的显式方法
        @TypeConverter
        @JvmStatic
        fun fromThemeList(themes: List<Theme>): String = instance.listToJson(themes)

        @TypeConverter
        @JvmStatic
        fun toThemeList(data: String): List<Theme> = instance.jsonToList(data)

        @TypeConverter
        @JvmStatic
        fun fromAuthorList(authors: List<Author>): String = instance.listToJson(authors)

        @TypeConverter
        @JvmStatic
        fun toAuthorList(data: String): List<Author> = instance.jsonToList(data)
    }
}
