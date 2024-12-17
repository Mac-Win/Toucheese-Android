package com.toucheese.app.data.model.search_studio


import com.google.gson.annotations.SerializedName

data class SearchResponseItem(
    @SerializedName("address")
    val address: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImage")
    val profileImage: String
)