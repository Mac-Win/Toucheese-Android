package com.example.toucheeseapp.data.model.userInfo


import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String
)