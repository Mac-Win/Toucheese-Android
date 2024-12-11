package com.example.toucheeseapp.data.model.saveCartData

data class TimeReservation(
    val hour: Int,
    val minute: Int,
    val second: Int = 0,
    val nano: Int = 0,
){
    override fun toString(): String {
        return "${hour}:${minute}"
    }
}
