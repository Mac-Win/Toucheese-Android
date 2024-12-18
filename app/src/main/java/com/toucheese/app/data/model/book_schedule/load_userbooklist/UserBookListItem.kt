package com.toucheese.app.data.model.book_schedule.load_userbooklist


import com.google.gson.annotations.SerializedName

data class UserBookListItem(
    @SerializedName("createDate")
    val createDate: String,
    @SerializedName("createTime")
    val createTime: String,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("reservationId")
    val reservationId: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("studioId")
    val studioId: Int,
    @SerializedName("studioImage")
    val studioImage: String,
    @SerializedName("studioName")
    val studioName: String
)