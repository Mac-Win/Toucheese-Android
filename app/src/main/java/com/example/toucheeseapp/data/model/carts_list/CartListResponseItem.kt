package com.example.toucheeseapp.data.model.carts_list


import com.google.gson.annotations.SerializedName

data class CartListResponseItem(
    @SerializedName("addOptions")
    val addOptions: List<AddOption>,
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
    @SerializedName("productStandard")
    val productStandard: Int,
    @SerializedName("reservationDate")
    val reservationDate: String,
    @SerializedName("reservationTime")
    val reservationTime: ReservationTime,
    @SerializedName("selectAddOptions")
    val selectAddOptions: List<SelectAddOption>,
    @SerializedName("studioImage")
    val studioImage: String,
    @SerializedName("studioName")
    val studioName: String,
    @SerializedName("totalPrice")
    val totalPrice: Int
)