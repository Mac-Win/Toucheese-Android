package com.toucheese.app.data.model.home.filter_studio


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
    val sort: com.toucheese.app.data.model.home.filter_studio.Sort,
    @SerializedName("unpaged")
    val unpaged: Boolean
)