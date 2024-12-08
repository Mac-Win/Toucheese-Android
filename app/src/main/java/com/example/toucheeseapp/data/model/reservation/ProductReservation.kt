package com.example.toucheeseapp.data.model.reservation

data class ProductReservation(
    val productId: Int,
    val studioId: Int,
    val totalPrice: Int,
    val memberId: Int,
    val createDate: String,
    val createTime: TimeReservation,
    val personnel: Int,
    val addOptions: List<Int>
)
