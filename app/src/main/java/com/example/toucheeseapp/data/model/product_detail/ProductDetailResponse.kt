package com.example.toucheeseapp.data.model.product_detail


import com.example.toucheeseapp.data.model.carts_list.AddOption
import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("addOptions")
    val addOptions: List<AddOption>,
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