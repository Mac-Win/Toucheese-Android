package com.example.toucheeseapp.data.network

import com.example.toucheeseapp.data.model.StudioResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ToucheeseServer {
    @GET("v1/concepts/{id}/studios") // baseUrl 뒤 상대경로
    suspend fun getStudios(
        @Path("id") conceptId: Int, // 동적 경로 파라미터
        @Query("page") page: Int // 쿼리 파라미터
    ): StudioResponse

}