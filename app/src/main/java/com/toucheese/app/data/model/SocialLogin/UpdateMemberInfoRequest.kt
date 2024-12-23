package com.toucheese.app.data.model.SocialLogin

import com.google.gson.annotations.SerializedName

data class UpdateMemberInfoRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String
)