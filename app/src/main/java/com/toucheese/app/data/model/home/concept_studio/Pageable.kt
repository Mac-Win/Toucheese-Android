package com.toucheese.app.data.model.home.concept_studio


import com.google.gson.annotations.SerializedName

data class Pageable(
    @SerializedName("pageNumber")
    val pageNumber: Int,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("sort")
    val sort: com.toucheese.app.data.model.home.concept_studio.Sort,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("unpaged")
    val unpaged: Boolean,
    @SerializedName("paged")
    val paged: Boolean
)