package com.toucheese.app.data.network

import com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponse
import com.toucheese.app.data.model.home.cart_order_pay.OrderPayResponse
import com.toucheese.app.data.model.home.carts_list.CartListResponse
import com.toucheese.app.data.model.home.carts_optionChange.ChangedCartItem
import com.toucheese.app.data.model.home.concept_studio.StudioResponse
import com.toucheese.app.data.model.home.filter_studio.FilterResponse
import com.toucheese.app.data.model.home.load_concept.ConceptResponse
import com.toucheese.app.data.model.home.product_detail.ProductDetailResponse
import com.toucheese.app.data.model.home.review_studio.StudioReviewResponse
import com.toucheese.app.data.model.home.saveCartData.CartData
import com.toucheese.app.data.model.home.saveReservationData.SaveReservationRequest
import com.toucheese.app.data.model.home.search_studio.SearchResponse
import com.toucheese.app.data.model.home.specific_review.ReviewResponse
import com.toucheese.app.data.model.home.studio_detail.StudioDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {
    // -------- 해당 컨셉 스튜디오 API --------

    // 컨셉 스튜디오 목록 조회
    @GET("v1/concepts/{conceptId}/studios") // baseUrl 뒤 상대경로
    suspend fun getStudios(
        @Path("conceptId") conceptId: Int, // 동적 경로 파라미터
        @Query("page") page: Int // 쿼리 파라미터
    ): com.toucheese.app.data.model.home.concept_studio.StudioResponse

    // 필터 적용 후 스튜디오 목록 조회
    @GET("v1/concepts/{conceptId}/studios/filters")
    suspend fun filterStudio(
        @Path("conceptId") conceptId: Int,
        @Query("page") page: Int,
        @Query("price") price: Int?,
        @Query("rating") rating: Double?,
        @Query("locations") locations: List<String>?,
    ): com.toucheese.app.data.model.home.filter_studio.FilterResponse

    // -------- 리뷰 API --------

    // 스튜디오 리뷰 목록 조회
    @GET("v1/studios/{studioId}/reviews")
    suspend fun loadStudioReviewList(
        @Path("studioId") studioId: Int,
    ): com.toucheese.app.data.model.home.review_studio.StudioReviewResponse

    // 특정 리뷰 상세 조회
    @GET("v1/studios/{studioId}/reviews/{reviewId}")
    suspend fun loadStudioSpecificReview(
        @Path("studioId") studioId: Int,
        @Path("reviewId") reviewId: Int,
    ): com.toucheese.app.data.model.home.specific_review.ReviewResponse

    // 특정 상품 리뷰 목록 조회
    @GET("v1/studios/{studioId}/products/{productId}/reviews")
    suspend fun loadProductReview(
        @Path("studioId") studioId: Int,
        @Path("productId") productId: Int,
    ): com.toucheese.app.data.model.home.review_studio.StudioReviewResponse

    // -------- 스튜디오 API --------

    // 스튜디오 검색
    @GET("v1/studios")
    suspend fun searchStudios(
        @Query("keyword") keyword: String
    ): com.toucheese.app.data.model.home.search_studio.SearchResponse

    // 스튜디오 상세 조회
    @GET("v1/studios/{studioId}")
    suspend fun loadStudioDetail(
        @Path("studioId") studioId: Int
    ): com.toucheese.app.data.model.home.studio_detail.StudioDetailResponse

    // 캘린더 휴무일 및 예약 희망 시간
    @GET("v1/studios/{studioId}/calendars")
    suspend fun loadCalendarTime(
        @Path("studioId") studioId: Int,
        @Query("yearMonth") yearMonth: String,
    ): com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponse

    // -------- 컨셉 API --------

    // 컨셉 조회
    @GET("v1/concepts")
    suspend fun loadConcept(): ConceptResponse

    // -------- 상품 API --------

    // 상품 상세 조회
    @GET("v1/products/{productId}")
    suspend fun loadProductDetail(
        @Path("productId") productId: Int
    ): com.toucheese.app.data.model.home.product_detail.ProductDetailResponse

    // -------- 메세지 API --------

    // 문자 메시지 발송 관련 솔라피 API

    // -------- 예약 API --------

    // 장바구니 저장 기능
    @POST("v1/members/carts")
    suspend fun saveCartData(
        @Header("Authorization") token: String?,
        @Body reservation: com.toucheese.app.data.model.home.saveCartData.CartData
    )

    // 예약 정보 저장 기능
    @POST("v1/members/reservations")
    suspend fun saveReservationData(
        @Header("Authorization") token: String?,
        @Body cartIds: com.toucheese.app.data.model.home.saveReservationData.SaveReservationRequest
    )

    // 장바구니 목록 조회
    @GET("v1/members/carts/list")
    suspend fun loadCartList(
        @Header("Authorization") token: String?,
    ): com.toucheese.app.data.model.home.carts_list.CartListResponse

    // 장바구니 옵션 및 인원 변경
    @PUT("v1/members/carts/{cartId}")
    suspend fun updateCartItem(
        @Header("Authorization") token: String?,
        @Path("cartId") cartId: Int,
        @Body changedCartItem: com.toucheese.app.data.model.home.carts_optionChange.ChangedCartItem,
    )

    // 해당 장바구니 삭제
    @DELETE("v1/members/carts/{cartId}")
    suspend fun deleteCartItem(
        @Header("Authorization") token: String?,
        @Path("cartId") cartId: Int
    )

    // 장바구니 결제 조회
    @GET("v1/members/carts/checkout-items")
    suspend fun loadOrderPayData(
        @Header("Authorization") token: String?,
        @Query("cartIds") cartIds: String,
    ): com.toucheese.app.data.model.home.cart_order_pay.OrderPayResponse


}