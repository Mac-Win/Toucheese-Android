package com.toucheese.app.data.model.home.concept_studio


import com.google.gson.annotations.SerializedName

data class Studio(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("price")
    val price: Int,
    @SerializedName("imageUrls")
    val images: List<String>
)