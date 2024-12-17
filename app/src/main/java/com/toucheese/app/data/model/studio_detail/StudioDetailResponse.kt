package com.toucheese.app.data.model.studio_detail


import com.google.gson.annotations.SerializedName

data class StudioDetailResponse(
    @SerializedName("address")
    val address: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("facilityImageUrls")
    val facilityImageUrls: List<String>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("notice")
    val notice: String,
    @SerializedName("operatingHours")
    val operatingHours: List<OperatingHour>,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("reviewCount")
    val reviewCount: Int
)