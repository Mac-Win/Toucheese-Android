package com.example.toucheeseapp.data.model.calendar_studio


import com.google.gson.annotations.SerializedName

data class CalendarTimeResponseItem(
    @SerializedName("date")
    val date: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("times")
    val times: List<String>
)