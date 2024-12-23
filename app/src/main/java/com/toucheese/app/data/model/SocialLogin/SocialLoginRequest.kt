package com.toucheese.app.data.model.SocialLogin

import com.google.gson.annotations.SerializedName

data class SocialLoginRequest(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("deviceId")
    val deviceId: String,
    @SerializedName("idToken")
    val idToken: String,
    @SerializedName("platform")
    val platform: String
)