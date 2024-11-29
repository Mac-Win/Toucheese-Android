package com.example.toucheeseapp.data.model


import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("content")
    val reviewContent: String,
    @SerializedName("id")
    val reviewId: Int,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("reviewImages")
    val reviewImages: List<String>
)