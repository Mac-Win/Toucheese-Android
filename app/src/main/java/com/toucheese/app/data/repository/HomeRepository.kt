package com.toucheese.app.data.repository

import com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponse
import com.toucheese.app.data.model.home.cart_order_pay.OrderPayResponse
import com.toucheese.app.data.model.home.carts_list.CartListResponse
import com.toucheese.app.data.model.home.carts_optionChange.ChangedCartItem
import com.toucheese.app.data.model.home.concept_studio.Studio
import com.toucheese.app.data.model.home.product_detail.ProductDetailResponse
import com.toucheese.app.data.model.home.review_studio.StudioReviewResponse
import com.toucheese.app.data.model.home.saveCartData.CartData
import com.toucheese.app.data.model.home.saveReservationData.SaveReservationRequest
import com.toucheese.app.data.model.home.search_studio.SearchResponseItem
import com.toucheese.app.data.model.home.specific_review.ReviewResponse
import com.toucheese.app.data.model.home.studio_detail.StudioDetailResponse
import com.toucheese.app.data.network.HomeService
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: HomeService) {

    // -------- 해당 컨셉 스튜디오 API -------

    // 컨셉 스튜디오 목록 조회
    suspend fun getConceptStudios(conceptId: Int, page: Int = 0): List<com.toucheese.app.data.model.home.concept_studio.Studio> = apiService.getStudios(conceptId, page).studioList

    // 필터 적용 후 스튜디오 목록 조회
    suspend fun filterStudios(conceptId: Int, page: Int = 0, price: Int?, rating: Double?, locations: List<String>?): List<com.toucheese.app.data.model.home.concept_studio.Studio> = apiService.filterStudio(conceptId, page, price, rating, locations).filterStudio

    // -------- 스튜디오 API --------

    // 스튜디오 검색
    suspend fun searchStudios(keyword: String): List<com.toucheese.app.data.model.home.search_studio.SearchResponseItem> = apiService.searchStudios(keyword)

    // 스튜디오 상세 조회
    suspend fun loadStudioDetail(studioId: Int): com.toucheese.app.data.model.home.studio_detail.StudioDetailResponse = apiService.loadStudioDetail(studioId)

    // 캘린더 휴무일 및 예약 희망 시간
    suspend fun loadCalendarTime(studioId: Int, yearMonth: String): com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponse = apiService.loadCalendarTime(studioId, yearMonth)
    // -------- 리뷰 API --------

    // 스튜디오 리뷰 목록 조회
    suspend fun loadStudioReviewList(studioId: Int): com.toucheese.app.data.model.home.review_studio.StudioReviewResponse = apiService.loadStudioReviewList(studioId)

    // 특정 리뷰 상세 조회
    suspend fun loadStudioSpecificReview(studioId: Int, reviewId: Int): com.toucheese.app.data.model.home.specific_review.ReviewResponse = apiService.loadStudioSpecificReview(studioId, reviewId)

    // 특정 상품 리뷰 목록 조회
    suspend fun loadProductReview(studioId: Int, productId: Int): com.toucheese.app.data.model.home.review_studio.StudioReviewResponse = apiService.loadProductReview(studioId, productId)

    // -------- 상품 API --------

    // 상품 상세 조회
    suspend fun loadProductDetail(productId: Int): com.toucheese.app.data.model.home.product_detail.ProductDetailResponse = apiService.loadProductDetail(productId)

    // -------- 예약 API --------

    // 예약 정보 저장 기능
    suspend fun saveCartData(token: String?, reservation: com.toucheese.app.data.model.home.saveCartData.CartData) = apiService.saveCartData(token, reservation)

    // 장바구니 저장 기능
    suspend fun saveReservationData(token: String?, saveReservationRequest: com.toucheese.app.data.model.home.saveReservationData.SaveReservationRequest) = apiService.saveReservationData(token, saveReservationRequest)

    // 장바구니 목록 조회
    suspend fun loadCartList(token: String?): com.toucheese.app.data.model.home.carts_list.CartListResponse = apiService.loadCartList(token)

    // 장바구니 옵션 및 인원 변경
    suspend fun updateCartItem(token: String?, cartId: Int, changedCartItem: com.toucheese.app.data.model.home.carts_optionChange.ChangedCartItem) = apiService.updateCartItem(token, cartId, changedCartItem)


    // 해당  장바구니 삭제
    suspend fun deleteCartItem(token:String?, cartId: Int) = apiService.deleteCartItem(token, cartId)

    // 장바구니 결제 조회
    suspend fun loadOrderPayData(token: String?, cartIds: String): com.toucheese.app.data.model.home.cart_order_pay.OrderPayResponse = apiService.loadOrderPayData(token, cartIds)
}