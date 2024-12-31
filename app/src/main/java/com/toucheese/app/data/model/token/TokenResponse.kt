package com.toucheese.app.data.model.token

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("deviceId")
    val deviceId: String,
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)