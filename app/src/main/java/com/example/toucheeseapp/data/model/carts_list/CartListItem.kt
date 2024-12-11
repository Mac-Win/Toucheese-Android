package com.example.toucheeseapp.data.model.carts_list

import com.example.toucheeseapp.data.model.product_detail.AddOption

data class CartListItem(
    val addOptions: List<AddOption>,
    val personnel: Int,
    val productName: String,
    val reservationDate: String,
    val reservationTime: ReservationTime,
    val studioName: String,
    val totalPrice: Int,
    val cartId:Int, // 추가
    val productImageUrl: String,
    val studioImageUrl: String
) {

}