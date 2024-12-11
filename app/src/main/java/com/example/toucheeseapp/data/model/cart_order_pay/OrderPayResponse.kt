package com.example.toucheeseapp.data.model.cart_order_pay


import com.google.gson.annotations.SerializedName

data class OrderPayResponse(
    @SerializedName("cartPaymentList")
    val cartPaymentList: List<CartPayment>,
    @SerializedName("memberContactInfo")
    val memberContactInfo: MemberContactInfo
)