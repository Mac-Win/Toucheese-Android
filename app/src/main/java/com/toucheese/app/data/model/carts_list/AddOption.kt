package com.toucheese.app.data.model.carts_list


import com.google.gson.annotations.SerializedName

data class AddOption(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int
)