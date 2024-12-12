package com.example.toucheeseapp.data.model.saveReservationData


import com.google.gson.annotations.SerializedName

data class SaveReservationRequest(
    @SerializedName("cartIds")
    val cartIds: String
)