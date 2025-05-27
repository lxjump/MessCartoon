package com.mess.messcartoon.network

import android.content.Context
import com.mess.messcartoon.R
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(context: Context) : Interceptor {
    // 可变的 Token
    var token: String = ""

    private val userAgent = context.getString(R.string.pc_ua)

    private val version = context.getString(R.string.copy_version)

    // 不变的请求头
    private val staticHeaders = mapOf(
        "host" to "www.copy-manga.com",
        "user-agent" to userAgent,
        "source" to "copyApp",
        "region" to "1",
        "webp" to "1",
        "version" to version,
        "authorization" to "Token $token",
        "platform" to "3",
        "device" to "AP4A.250250.002",
        "deviceinfo" to "Pixel 6-oriole",
        "referer" to "com.copymanga.app-${version}",
        "umstring" to ""
        // 其他静态请求头...
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        // 添加所有静态请求头
        staticHeaders.forEach { (name, value) ->
            requestBuilder.header(name, value)
        }

        // 如果 Token 不为空，添加 Token 请求头
        if (token.isNotEmpty()) {
            requestBuilder.header("Authorization", "Token $token")
        }

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}