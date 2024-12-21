package com.toucheese.app.data.model.home.product_detail


import com.google.gson.annotations.SerializedName

data class AddOption(
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int
)