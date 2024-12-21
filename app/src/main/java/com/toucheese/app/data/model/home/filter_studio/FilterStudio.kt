package com.toucheese.app.data.model.home.filter_studio


import com.google.gson.annotations.SerializedName

data class FilterStudio(
    @SerializedName("imageUrls")
    val images: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("rating")
    val rating: Double
)