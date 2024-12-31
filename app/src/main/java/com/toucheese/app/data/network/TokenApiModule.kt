package com.toucheese.app.data.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenApiModule {

    @Provides
    @Singleton
    fun provideTokenApi(): TokenApi {
        val client = OkHttpClient.Builder()
            // Authenticator 붙이지 않음
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.toucheese-macwin.store/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(TokenApi::class.java)
    }
}