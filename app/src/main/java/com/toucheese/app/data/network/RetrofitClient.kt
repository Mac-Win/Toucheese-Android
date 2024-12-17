package com.toucheese.app.data.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // 애플리케이션 전역 사용
object RetrofitClient {

    private const val BASE_URL = "https://api.toucheese-macwin.store/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    @Singleton
    @Provides
    fun provideToucheeseServer(retrofit: Retrofit): ToucheeseServer = retrofit.create(ToucheeseServer::class.java)

    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService = retrofit.create(LoginService::class.java)
}