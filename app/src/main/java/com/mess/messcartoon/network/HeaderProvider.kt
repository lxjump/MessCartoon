package com.mess.messcartoon.network

import android.content.Context
import com.mess.messcartoon.R
import com.mess.messcartoon.utils.DeviceUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class HeaderProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @Volatile
    private var token: String? = null

    fun updateToken(newToken: String) {
        token = newToken
    }

    fun clearToken() {
        token = null
    }

    fun getHeaders(): Map<String, String> {
        val userAgent = context.getString(R.string.pc_ua)
        val version = context.getString(R.string.copy_version)
        val deviceName = DeviceUtils.getSystemDeviceName(context)
        val device = DeviceUtils.getBuildNumber()
        val headers = mutableMapOf<String, String>()
        headers["user-agent"] = userAgent
        headers["source"] = "copyApp"
        headers["region"] = "1"
        headers["webp"] = "1"
        headers["version"] = version
        headers["platform"] = "3"
        headers["referer"] = "com.copymanga.app-$version"
        headers["device"] = device
        headers["deviceinfo"] = deviceName
        headers["umstring"] = "b4c89ca4104ea9a97750314d791520ac"
        if (token?.isNotEmpty() == true) {
            headers["authorization"] = "Token $token"
        } else {
            headers["authorization"] = "Token "
        }

        return headers
    }
}

