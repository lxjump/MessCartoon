package com.mess.messcartoon.network

import android.content.Context

// Token 管理类
class TokenManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun getToken(): String {
        return sharedPreferences.getString("auth_token", "") ?: ""
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

    fun clearToken() {
        sharedPreferences.edit().remove("auth_token").apply()
    }
}
