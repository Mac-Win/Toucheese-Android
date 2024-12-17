package com.toucheese.app.data.model.cart_order_pay


import com.google.gson.annotations.SerializedName

data class MemberContactInfo(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String
)