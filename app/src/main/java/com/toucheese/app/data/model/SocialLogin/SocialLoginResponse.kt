package com.toucheese.app.data.model.SocialLogin

import com.google.gson.annotations.SerializedName

data class SocialLoginResponse(
    @SerializedName("deviceId")
    val deviceId: String,
    @SerializedName("isFirstLogin")
    val isFirstLogin: Boolean,
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)