package com.toucheese.app.data.model.SocialLogin.Kakao

import com.google.gson.annotations.SerializedName

data class KakaoAuthCallbackResponse(
    @SerializedName("isFirstLogin")
    val isFirstLogin: Boolean,
    @SerializedName("nickname")
    val nickname: String
)