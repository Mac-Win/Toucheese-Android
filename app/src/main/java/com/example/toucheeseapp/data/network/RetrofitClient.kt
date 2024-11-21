package com.example.toucheeseapp.data.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private var instance: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    fun getInstance(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder().baseUrl("https://c0f9-210-102-61-178.ngrok-free.app/api/")
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return instance!!
    }
}