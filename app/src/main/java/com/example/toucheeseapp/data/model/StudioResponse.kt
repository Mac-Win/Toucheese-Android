package com.example.toucheeseapp.data.model


import com.google.gson.annotations.SerializedName

data class StudioResponse(
    @SerializedName("content")
    val studioList: List<Studio>,
    @SerializedName("pageable")
    val pageable: Pageable,
    @SerializedName("last")
    val last: Boolean,
    @SerializedName("totalPages")
    val totalPages: Int,
    @SerializedName("totalElements")
    val totalElements: Int,
    @SerializedName("first")
    val first: Boolean,
    @SerializedName("size")
    val size: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("sort")
    val sort: Sort,
    @SerializedName("numberOfElements")
    val numberOfElements: Int,
    @SerializedName("empty")
    val empty: Boolean
){
    override fun toString(): String {
        return "studioList = $studioList"
    }
}