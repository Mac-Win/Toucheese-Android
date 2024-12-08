package com.example.toucheeseapp.data.token_manager

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun saveAccessToken(token: String) {
        prefs.edit().putString("accessToken", token).apply()
    }

    fun getAccessToken(): String?{
        return prefs.getString("accessToken", null)
    }

    fun clearAccessToken() {
        prefs.edit().remove("accessToken").apply()
    }
}