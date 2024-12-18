package com.toucheese.app.data.model.book_schedule.load_userbooklist


import com.google.gson.annotations.SerializedName

data class Pageable(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("pageNumber")
    val pageNumber: Int,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("paged")
    val paged: Boolean,
    @SerializedName("sort")
    val sort: SortX,
    @SerializedName("unpaged")
    val unpaged: Boolean
)