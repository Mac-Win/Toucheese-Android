package com.toucheese.app.data.model.token

import com.google.gson.annotations.SerializedName

data class TokenRequest(
    @SerializedName("deviceId")
    val deviceId: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)