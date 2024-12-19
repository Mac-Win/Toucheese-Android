package com.toucheese.app.data.model.home.specific_review


import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("content")
    val reviewContent: String,
    @SerializedName("id")
    val reviewId: Int,
    @SerializedName("rating")
    val rating: Float,
    @SerializedName("reviewImages")
    val reviewImages: List<String>
)