package com.mess.messcartoon.ui.views

enum class DetailType(val typeName: String) {
    Recommend("recommend"),
    Category("category"),
    New("newest"),
    Finished("finished"),
    Hot("hottest");

    companion object {
        fun fromTypeName(typeName: String): DetailType {
            return entries.firstOrNull { it.typeName == typeName } ?: Recommend
        }
    }
}