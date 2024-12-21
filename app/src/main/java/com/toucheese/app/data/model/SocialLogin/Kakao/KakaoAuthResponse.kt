package com.toucheese.app.data.model.SocialLogin.Kakao

import com.google.gson.annotations.SerializedName

data class KakaoAuthResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("idToken")
    val idToken: String
)