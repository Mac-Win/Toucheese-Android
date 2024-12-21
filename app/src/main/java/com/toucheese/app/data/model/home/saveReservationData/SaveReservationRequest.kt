package com.toucheese.app.data.model.home.saveReservationData


import com.google.gson.annotations.SerializedName

data class SaveReservationRequest(
    @SerializedName("cartIds")
    val cartIds: String
)