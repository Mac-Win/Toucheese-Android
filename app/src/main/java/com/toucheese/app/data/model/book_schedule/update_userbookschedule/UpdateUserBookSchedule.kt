package com.toucheese.app.data.model.book_schedule.update_userbookschedule


import com.google.gson.annotations.SerializedName

data class UpdateUserBookSchedule(
    @SerializedName("createDate")
    val createDate: String,
    @SerializedName("createTime")
    val createTime: String, // 예시 = 19:30
)