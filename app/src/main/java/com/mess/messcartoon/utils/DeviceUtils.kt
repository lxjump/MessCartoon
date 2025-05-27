package com.mess.messcartoon.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresPermission
import java.util.Locale

/**
 * Android 设备信息工具类
 * 功能：获取设备名称（优先用户自定义名称，其次系统默认名称）
 */
object DeviceUtils {

    /**
     * 获取设备名称（优先用户设置的名称，其次系统默认名称）
     * 注意：需要权限 <uses-permission android:name="android.permission.BLUETOOTH"/>
     */
    @SuppressLint("HardwareIds", "MissingPermission")
    @RequiresPermission(android.Manifest.permission.BLUETOOTH)
    fun getDeviceName(context: Context): String {
        return when {
            // 1. 优先尝试获取用户设置的设备名称（需要BLUETOOTH权限）
            Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2 -> {
                Settings.Secure.getString(
                    context.contentResolver,
                    "bluetooth_name"
                ) ?: getSystemDeviceName(context)
            }

            // 2. Android 4.3+ 通过BluetoothAdapter获取
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 -> {
                try {
                    val bluetoothAdapter = Class.forName("android.bluetooth.BluetoothAdapter")
                        .getMethod("getDefaultAdapter").invoke(null) as? android.bluetooth.BluetoothAdapter
                    bluetoothAdapter?.name?.takeIf { it.isNotEmpty() }
                        ?: getSystemDeviceName(context)
                } catch (e: Exception) {
                    getSystemDeviceName(context)
                }
            }

            // 3. 其他情况回退到系统属性
            else -> getSystemDeviceName(context)
        }
    }

    /**
     * 获取系统默认设备名称（无需权限）
     * 格式：品牌 + 型号（例如：Google Pixel 6）
     */
    fun getSystemDeviceName(context: Context): String {
        return try {
            // 从Build类组合信息
            val manufacturer = Build.MANUFACTURER.trim().capitalize()
            val model = Build.MODEL.trim().removePrefix(manufacturer, ignoreCase = true).trim()

            when {
                model.isEmpty() -> manufacturer
                model.startsWith(manufacturer, ignoreCase = true) -> model
                else -> "$manufacturer $model"
            }
        } catch (e: Exception) {
            // 异常时返回通用名称
            "Android Device"
        }
    }

    /**
     * 获取设备唯一标识（非硬件序列号，根据Android版本适配）
     */
    @SuppressLint("HardwareIds")
    fun getDeviceUniqueId(context: Context): String {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            }
            else -> @Suppress("DEPRECATION") {
                Build.SERIAL
            }
        }
    }

    // 扩展函数：忽略大小写移除前缀
    private fun String.removePrefix(prefix: String, ignoreCase: Boolean): String {
        return if (startsWith(prefix, ignoreCase)) substring(prefix.length) else this
    }

    // 扩展函数：首字母大写（兼容多语言）
    private fun String.capitalize() = replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }

    /**
    * 获取设备 Build 号（例如 AP4A.250250.002）
    * 对应系统属性：ro.build.display.id
    */
    fun getBuildNumber(): String {
        return Build.DISPLAY
    }

    /**
     * 获取完整 Build 指纹（包含更多信息）
     * 示例：google/panther/panther:14/AP4A.250250.002/12345678:user/release-keys
     */
    fun getBuildFingerprint(): String {
        return Build.FINGERPRINT
    }

    /**
     * 获取基础 Build ID（短格式）
     * 示例：AP4A.250250.002
     */
    fun getBaseBuildNumber(): String {
        return Build.DISPLAY.split("/").firstOrNull() ?: Build.DISPLAY
    }
}