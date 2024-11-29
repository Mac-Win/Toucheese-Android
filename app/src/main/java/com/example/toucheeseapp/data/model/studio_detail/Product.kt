package com.example.toucheeseapp.data.model.studio_detail


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("productImage")
    val productImage: String,
    @SerializedName("reviewCount")
    val reviewCount: Int,
    @SerializedName("standard")
    val standard: Int
)