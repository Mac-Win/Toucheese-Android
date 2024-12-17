package com.toucheese.app.data.model.cart_order_pay


import com.google.gson.annotations.SerializedName

data class OrderPayResponse(
    @SerializedName("CheckoutCartItems")
    val cartPaymentList: List<CartPayment>,
    @SerializedName("memberContactInfo")
    val memberContactInfo: MemberContactInfo
)