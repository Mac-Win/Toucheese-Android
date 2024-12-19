package com.toucheese.app.data.model.home.filter_studio


import com.toucheese.app.data.model.home.concept_studio.Studio
import com.google.gson.annotations.SerializedName

data class FilterResponse(
    @SerializedName("content")
    val filterStudio: List<com.toucheese.app.data.model.home.concept_studio.Studio>,
    @SerializedName("empty")
    val empty: Boolean,
    @SerializedName("first")
    val first: Boolean,
    @SerializedName("last")
    val last: Boolean,
    @SerializedName("number")
    val number: Int,
    @SerializedName("numberOfElements")
    val numberOfElements: Int,
    @SerializedName("pageable")
    val pageable: com.toucheese.app.data.model.home.filter_studio.Pageable,
    @SerializedName("size")
    val size: Int,
    @SerializedName("sort")
    val sort: com.toucheese.app.data.model.home.filter_studio.Sort,
    @SerializedName("totalElements")
    val totalElements: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)