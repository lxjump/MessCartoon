package com.mess.messcartoon.utils

import android.annotation.SuppressLint
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object Formatter {

    @SuppressLint("ConstantLocale")
    private val defaultFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    @SuppressLint("ConstantLocale")
    private val colonFormat = SimpleDateFormat("yyyy:MM:dd", Locale.getDefault())

    /**
    * 格式化数字为万单位
    * @param number 原始数字
    * @param decimalDigits 保留小数位数 (默认 1 位)
    * @param keepZero 是否保留小数位的零 (默认自动省略)
    */
    @JvmStatic
    @JvmOverloads
    fun formatToTenThousand(
        number: Number,
        decimalDigits: Int = 1,
        keepZero: Boolean = false
    ): String {
        val num = number.toDouble()
        return when {
            // 超过 1 万时转换
            num >= 10_000 -> {
                val formatPattern = buildFormatPattern(decimalDigits, keepZero)
                val df = DecimalFormat(formatPattern).apply {
                    roundingMode = RoundingMode.HALF_UP
                }
                "${df.format(num / 10_000)}万"
            }
            // 处理 1 万以下保持原样
            else -> num.toInt().toString()
        }
    }

    /** 构建小数格式化模板 */
    private fun buildFormatPattern(decimalDigits: Int, keepZero: Boolean): String {
        require(decimalDigits in 0..3) { "小数位数需在 0-3 之间" }
        return when {
            decimalDigits == 0 -> "#"
            keepZero -> "0.${"0".repeat(decimalDigits)}"
            else -> "#.${"#".repeat(decimalDigits)}"
        }
    }

    fun formatDate(timestamp: Long): String {
        return if (timestamp <= 0L) "--" else defaultFormat.format(Date(timestamp))
    }

    // 相对时间（如“2 天前”，“刚刚”）
    fun formatRelativeTime(timestamp: Long): String {
        if (timestamp <= 0L) return "--"
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        return when {
            diff < TimeUnit.MINUTES.toMillis(1) -> "刚刚"
            diff < TimeUnit.HOURS.toMillis(1) -> "${diff / TimeUnit.MINUTES.toMillis(1)} 分钟前"
            diff < TimeUnit.DAYS.toMillis(1) -> "${diff / TimeUnit.HOURS.toMillis(1)} 小时前"
            diff < TimeUnit.DAYS.toMillis(7) -> "${diff / TimeUnit.DAYS.toMillis(1)} 天前"
            else -> defaultFormat.format(Date(timestamp))
        }
    }

    fun format(timestamp: Long, pattern: String = "yyyy-MM-dd HH:mm"): String {
        return try {
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            sdf.format(Date(timestamp))
        } catch (e: Exception) {
            ""
        }
    }

}