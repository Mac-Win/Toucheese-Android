package com.example.toucheeseapp.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponseBody(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("name")
    val name: String,
)
