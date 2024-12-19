package com.toucheese.app.data.model.home.carts_list


import com.google.gson.annotations.SerializedName

data class SelectAddOption(
    @SerializedName("selectOptionId")
    val selectOptionId: Int,
    @SerializedName("selectOptionName")
    val selectOptionName: String,
    @SerializedName("selectOptionPrice")
    val selectOptionPrice: Int
)