package com.toucheese.app.data.model.SocialLogin.Kakao

import com.google.gson.annotations.SerializedName

data class KakaoAuthCallbackResponse(
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