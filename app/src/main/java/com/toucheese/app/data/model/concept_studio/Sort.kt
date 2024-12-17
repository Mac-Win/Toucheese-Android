package com.toucheese.app.data.model.concept_studio


import com.google.gson.annotations.SerializedName

data class Sort(
    @SerializedName("empty")
    val empty: Boolean,
    @SerializedName("unsorted")
    val unsorted: Boolean,
    @SerializedName("sorted")
    val sorted: Boolean
)