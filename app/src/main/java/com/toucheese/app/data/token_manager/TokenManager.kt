package com.toucheese.app.data.token_manager

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

    fun saveRefreshToken(token: String) {
        prefs.edit().putString("refreshToken", token).apply()
    }

    fun getRefreshToken(): String?{
        return prefs.getString("refreshToken", null)
    }

    fun saveDeviceId(deviceId: String){
        prefs.edit().putString("deviceId", deviceId).apply()
    }

    fun getDeviceId(): String?{
        return prefs.getString("deviceId", null)
    }
}