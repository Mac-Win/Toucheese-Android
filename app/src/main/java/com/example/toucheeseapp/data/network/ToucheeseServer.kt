package com.example.toucheeseapp.data.network

import com.example.toucheeseapp.data.model.calendar_studio.CalendarTimeResponse
import com.example.toucheeseapp.data.model.specific_review.ReviewResponse
import com.example.toucheeseapp.data.model.concept_studio.StudioResponse
import com.example.toucheeseapp.data.model.filter_studio.FilterResponse
import com.example.toucheeseapp.data.model.load_concept.ConceptResponse
import com.example.toucheeseapp.data.model.login.Login
import com.example.toucheeseapp.data.model.product_detail.ProductDetailResponse
import com.example.toucheeseapp.data.model.reservation.ProductReservation
import com.example.toucheeseapp.data.model.review_studio.StudioReviewResponse
import com.example.toucheeseapp.data.model.search_studio.SearchResponse
import com.example.toucheeseapp.data.model.studio_detail.StudioDetailResponse
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ToucheeseServer {
    // -------- 해당 컨셉 스튜디오 API --------

    // 컨셉 스튜디오 목록 조회
    @GET("v1/concepts/{conceptId}/studios") // baseUrl 뒤 상대경로
    suspend fun getStudios(
        @Path("conceptId") conceptId: Int, // 동적 경로 파라미터
        @Query("page") page: Int // 쿼리 파라미터
    ): StudioResponse

    // 필터 적용 후 스튜디오 목록 조회
    @GET("v1/concepts/{conceptId}/studios/filters")
    suspend fun filterStudio(
        @Path("conceptId") conceptId: Int,
        @Query("page") page: Int,
        @Query("price") price: Int?,
        @Query("rating") rating: Double?,
        @Query("locations") locations: List<String>?,
    ): FilterResponse

    // -------- 리뷰 API --------

    // 스튜디오 리뷰 목록 조회
    @GET("v1/studios/{studioId}/reviews")
    suspend fun loadStudioReviewList(
        @Path("studioId") studioId: Int,
    ): StudioReviewResponse

    // 특정 리뷰 상세 조회
    @GET("v1/studios/{studioId}/reviews/{reviewId}")
    suspend fun loadStudioSpecificReview(
        @Path("studioId") studioId: Int,
        @Path("reviewId") reviewId: Int,
    ): ReviewResponse

    // 특정 상품 리뷰 목록 조회
    @GET("v1/studios/{studioId}/products/{productId}/reviews")
    suspend fun loadProductReview(
        @Path("studioId") studioId: Int,
        @Path("productId") productId: Int,
    ): StudioReviewResponse

    // -------- 스튜디오 API --------

    // 스튜디오 검색
    @GET("v1/studios")
    suspend fun searchStudios(
        @Query("keyword") keyword: String
    ): SearchResponse

    // 스튜디오 상세 조회
    @GET("v1/studios/{studioId}")
    suspend fun loadStudioDetail(
        @Path("studioId") studioId: Int
    ): StudioDetailResponse

    // 캘린더 휴무일 및 예약 희망 시간
    @GET("v1/studios/{studioId}/calendars")
    suspend fun loadCalendarTime(
        @Path("studioId") studioId: Int,
        @Query("yearMonth") yearMonth: String,
    ): CalendarTimeResponse

    // -------- 컨셉 API --------

    // 컨셉 조회
    @GET("v1/concepts")
    suspend fun loadConcept(): ConceptResponse

    // -------- 상품 API --------

    // 상품 상세 조회
    @GET("v1/products/{productId}")
    suspend fun loadProductDetail(
        @Path("productId") productId: Int
    ): ProductDetailResponse

    // -------- 메세지 API --------

    // 문자 메시지 발송 관련 솔라피 API

    // -------- 예약 API --------

    // 기능: 예약 정보 저장
    @POST("v1/reservations")
    suspend fun setReservationData(@Body reservation: ProductReservation)

}