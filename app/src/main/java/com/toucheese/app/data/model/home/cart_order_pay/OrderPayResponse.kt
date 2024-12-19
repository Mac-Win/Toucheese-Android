package com.toucheese.app.data.model.home.cart_order_pay


import com.google.gson.annotations.SerializedName

data class OrderPayResponse(
    @SerializedName("CheckoutCartItems")
    val cartPaymentList: List<com.toucheese.app.data.model.home.cart_order_pay.CartPayment>,
    @SerializedName("memberContactInfo")
    val memberContactInfo: com.toucheese.app.data.model.home.cart_order_pay.MemberContactInfo
)