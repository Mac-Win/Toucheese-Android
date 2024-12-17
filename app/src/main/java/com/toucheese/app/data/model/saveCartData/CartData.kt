package com.toucheese.app.data.model.saveCartData

data class CartData(
    val productId: Int,
    val studioId: Int,
    val totalPrice: Int,
    val memberId: Int,
    val createDate: String,
    val createTime: String,
    val personnel: Int,
    val addOptions: List<Int>
)
