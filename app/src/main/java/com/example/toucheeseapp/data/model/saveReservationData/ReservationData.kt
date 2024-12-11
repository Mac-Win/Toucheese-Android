package com.example.toucheeseapp.data.model.saveReservationData

data class ReservationData(
    val addOptions: List<Int>,
    val createDate: String,
    val createTime: CreateTime,
    val memberId: Int,
    val personnel: Int,
    val productId: Int,
    val studioId: Int,
    val totalPrice: Int
)