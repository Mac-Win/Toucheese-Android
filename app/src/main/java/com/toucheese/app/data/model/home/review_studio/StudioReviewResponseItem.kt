package com.toucheese.app.data.model.home.review_studio


import com.google.gson.annotations.SerializedName

data class StudioReviewResponseItem(
    @SerializedName("firstImage")
    val firstImage: String,
    @SerializedName("id")
    val id: Int
)