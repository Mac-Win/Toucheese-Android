package com.example.toucheeseapp.data.model.cart_order_pay


import com.google.gson.annotations.SerializedName

data class CartPayment(
    @SerializedName("cartId")
    val cartId: Int,
    @SerializedName("personnel")
    val personnel: Int,
    @SerializedName("productImage")
    val productImage: String,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("productPrice")
    val productPrice: Int,
    @SerializedName("reservationDate")
    val reservationDate: String,
    @SerializedName("reservationTime")
    val reservationTime: String,
    @SerializedName("selectAddOptions")
    val selectAddOptions: List<SelectAddOption>,
    @SerializedName("studioName")
    val studioName: String,
    @SerializedName("totalPrice")
    val totalPrice: Int
)