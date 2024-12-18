package com.toucheese.app.data.model.home.carts_list


import com.google.gson.annotations.SerializedName

data class CartListResponseItem(
    @SerializedName("addOptions")
    val addOptions: List<com.toucheese.app.data.model.home.carts_list.AddOption>,
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
    val reservationTime: String,
    @SerializedName("selectAddOptions")
    val selectAddOptions: List<com.toucheese.app.data.model.home.carts_list.SelectAddOption>,
    @SerializedName("studioImage")
    val studioImage: String,
    @SerializedName("studioName")
    val studioName: String,
    @SerializedName("totalPrice")
    val totalPrice: Int
)