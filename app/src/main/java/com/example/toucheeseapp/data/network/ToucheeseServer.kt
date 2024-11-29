package com.example.toucheeseapp.data.network

import com.example.toucheeseapp.data.model.ReviewResponse
import com.example.toucheeseapp.data.model.concept_studio.StudioResponse
import com.example.toucheeseapp.data.model.filter_studio.FilterResponse
import com.example.toucheeseapp.data.model.review_studio.StudioReviewResponse
import com.example.toucheeseapp.data.model.search_studio.SearchResponse
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
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

    // 필터 스튜디오 데이터 조회
    @GET("v1/studios/{id}/filters")
    suspend fun filterStudio(
        @Path("id") conceptId: Int,
        @Query("page") page: Int,
        @Query("price") price: Int?,
        @Query("rating") rating: Double?,
        @Query("locations") locations: List<String>?,
    ): FilterResponse

    // 스튜디오 리뷰 목록 조회
    @GET("v1/studios/{studioId}/reviews")
    suspend fun loadStudioReviewList(
        @Path("studioId") studioId: Int,
    ): StudioReviewResponse
}