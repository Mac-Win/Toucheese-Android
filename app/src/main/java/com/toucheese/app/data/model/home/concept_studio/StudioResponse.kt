package com.toucheese.app.data.model.home.concept_studio


import com.google.gson.annotations.SerializedName

data class StudioResponse(
    @SerializedName("content")
    val studioList: List<com.toucheese.app.data.model.home.concept_studio.Studio>,
    @SerializedName("pageable")
    val pageable: com.toucheese.app.data.model.home.concept_studio.Pageable,
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
    val sort: com.toucheese.app.data.model.home.concept_studio.Sort,
    @SerializedName("numberOfElements")
    val numberOfElements: Int,
    @SerializedName("empty")
    val empty: Boolean
){
    override fun toString(): String {
        return "studioList = $studioList"
    }
}