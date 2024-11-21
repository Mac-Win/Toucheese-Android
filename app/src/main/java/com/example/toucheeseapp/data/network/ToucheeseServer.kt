package com.example.toucheeseapp.data.network

import com.example.toucheeseapp.data.model.concept_studio.StudioResponse
import com.example.toucheeseapp.data.model.search_studio.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ToucheeseServer {
    // 스튜디오 컨셉별 데이터 조회
    @GET("v1/concepts/{id}/studios") // baseUrl 뒤 상대경로
    suspend fun getStudios(
        @Path("id") conceptId: Int, // 동적 경로 파라미터
        @Query("page") page: Int // 쿼리 파라미터
    ): StudioResponse

    // 검색 스튜디오 데이터 조회
    @GET("v1/studios/search")
    suspend fun searchStudios(
        @Query("keyword") keyword: String
    ): SearchResponse

}