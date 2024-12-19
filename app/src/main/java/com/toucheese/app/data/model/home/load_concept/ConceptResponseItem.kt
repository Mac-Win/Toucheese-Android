package com.toucheese.app.data.model.home.load_concept


import com.google.gson.annotations.SerializedName

data class ConceptResponseItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)