package com.mess.messcartoon.network

import android.content.Context
import com.mess.messcartoon.R
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

        val headers = mutableMapOf<String, String>()
        headers["host"] = "www.copy-manga.com"
        headers["user-agent"] = userAgent
        headers["source"] = "copyApp"
        headers["region"] = "1"
        headers["webp"] = "1"
        headers["version"] = version
        headers["platform"] = "3"
        headers["referer"] = "com.copymanaga.app-$version"
        if (token?.isNotEmpty() == true) {
            headers["authorization"] = "Token $token"
        } else {
            headers["authorization"] = "Token "
        }

        return headers
    }
}

