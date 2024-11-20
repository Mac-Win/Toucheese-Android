package com.example.toucheeseapp.data.model

data class Studio(
    val studioName: String,
    val profileImage: String, // url
    val price: Int,
    val rating: Double,
    val address: String,
    val location: String,
    val photoList: List<String>,
)
