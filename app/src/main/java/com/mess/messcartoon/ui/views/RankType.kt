package com.mess.messcartoon.ui.views

import com.mess.messcartoon.ui.views.DetailType.Recommend

enum class RankType(val typeName: String) {

    RankDay("day"),
    RankWeek("week"),
    RankMonth("month"),
    RankTotal("total"), ;

    fun isRank(): Boolean {
        return this == RankType.RankDay ||
                this == RankType.RankWeek ||
                this == RankType.RankMonth ||
                this == RankType.RankTotal
    }

    companion object {
        // 根据 typeName 查找枚举
        fun fromTypeName(typeName: String): RankType {
            return entries.find { it.typeName == typeName }
                ?: throw IllegalArgumentException("未知的 RankType: $typeName")
        }
    }

}