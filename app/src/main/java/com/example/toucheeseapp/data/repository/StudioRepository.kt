package com.example.toucheeseapp.data.repository

import com.example.toucheeseapp.data.model.calendar_studio.CalendarTimeResponse
import com.example.toucheeseapp.data.model.cart_order_pay.OrderPayResponse
import com.example.toucheeseapp.data.model.carts_list.CartListResponse
import com.example.toucheeseapp.data.model.carts_optionChange.ChangedCartItem
import com.example.toucheeseapp.data.model.concept_studio.Studio
import com.example.toucheeseapp.data.model.product_detail.ProductDetailResponse
import com.example.toucheeseapp.data.model.review_studio.StudioReviewResponse
import com.example.toucheeseapp.data.model.saveCartData.CartData
import com.example.toucheeseapp.data.model.saveReservationData.ReservationData
import com.example.toucheeseapp.data.model.search_studio.SearchResponseItem
import com.example.toucheeseapp.data.model.specific_review.ReviewResponse
import com.example.toucheeseapp.data.model.studio_detail.StudioDetailResponse
import com.example.toucheeseapp.data.model.userInfo.UserInfoResponse
import com.example.toucheeseapp.data.network.ToucheeseServer
import javax.inject.Inject

class StudioRepository @Inject constructor(private val apiService: ToucheeseServer) {

    // -------- 해당 컨셉 스튜디오 API -------

    // 컨셉 스튜디오 목록 조회
    suspend fun getConceptStudios(conceptId: Int, page: Int = 0): List<Studio> = apiService.getStudios(conceptId, page).studioList

    // 필터 적용 후 스튜디오 목록 조회
    suspend fun filterStudios(conceptId: Int, page: Int = 0, price: Int?, rating: Double?, locations: List<String>?): List<Studio> = apiService.filterStudio(conceptId, page, price, rating, locations).filterStudio

    // -------- 스튜디오 API --------

    // 스튜디오 검색
    suspend fun searchStudios(keyword: String): List<SearchResponseItem> = apiService.searchStudios(keyword)

    // 스튜디오 상세 조회
    suspend fun loadStudioDetail(studioId: Int): StudioDetailResponse = apiService.loadStudioDetail(studioId)

    // 캘린더 휴무일 및 예약 희망 시간
    suspend fun loadCalendarTime(studioId: Int, yearMonth: String): CalendarTimeResponse = apiService.loadCalendarTime(studioId, yearMonth)
    // -------- 리뷰 API --------

    // 스튜디오 리뷰 목록 조회
    suspend fun loadStudioReviewList(studioId: Int): StudioReviewResponse = apiService.loadStudioReviewList(studioId)

    // 특정 리뷰 상세 조회
    suspend fun loadStudioSpecificReview(studioId: Int, reviewId: Int): ReviewResponse = apiService.loadStudioSpecificReview(studioId, reviewId)

    // 특정 상품 리뷰 목록 조회
    suspend fun loadProductReview(studioId: Int, productId: Int):StudioReviewResponse = apiService.loadProductReview(studioId, productId)

    // -------- 상품 API --------

    // 상품 상세 조회
    suspend fun loadProductDetail(productId: Int): ProductDetailResponse = apiService.loadProductDetail(productId)

    // -------- 예약 API --------

    // 예약 정보 저장 기능
    suspend fun saveCartData(token: String?, reservation: CartData) = apiService.saveCartData(token, reservation)

    // 장바구니 저장 기능
    suspend fun saveReservationData(token: String?, reservationData: ReservationData) = apiService.saveReservationData(token, reservationData)

    // 장바구니 목록 조회
    suspend fun loadCartList(token: String?): CartListResponse = apiService.loadCartList(token)

    // 장바구니 옵션 및 인원 변경
    suspend fun updateCartItem(token: String?, cartId: Int, changedCartItem: ChangedCartItem) = apiService.updateCartItem(token, cartId, changedCartItem)


    // 해당  장바구니 삭제
    suspend fun deleteCartItem(token:String?, cartId: Int) = apiService.deleteCartItem(token, cartId)

    // 장바구니 결제 조회
    suspend fun loadOrderPayData(token: String?, cartIds: String): OrderPayResponse = apiService.loadOrderPayData(token, cartIds)
}