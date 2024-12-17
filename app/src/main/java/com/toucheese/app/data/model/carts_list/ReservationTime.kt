package com.toucheese.app.data.model.carts_list


import com.google.gson.annotations.SerializedName

data class ReservationTime(
    @SerializedName("hour")
    val hour: Int,
    @SerializedName("minute")
    val minute: Int,
    @SerializedName("nano")
    val nano: Int,
    @SerializedName("second")
    val second: Int
)