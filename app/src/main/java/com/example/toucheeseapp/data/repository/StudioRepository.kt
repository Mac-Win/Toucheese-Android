package com.example.toucheeseapp.data.repository

import com.example.toucheeseapp.data.model.calendar_studio.CalendarTimeResponse
import com.example.toucheeseapp.data.model.carts_list.CartList
import com.example.toucheeseapp.data.model.carts_list.CartListItem
import com.example.toucheeseapp.data.model.carts_optionChange.CartOptionChange
import com.example.toucheeseapp.data.model.carts_request.SaveCartsRequest
import com.example.toucheeseapp.data.model.concept_studio.Studio
import com.example.toucheeseapp.data.model.product_detail.ProductDetailResponse
import com.example.toucheeseapp.data.model.reservation.ProductReservation
import com.example.toucheeseapp.data.model.review_studio.StudioReviewResponse
import com.example.toucheeseapp.data.model.search_studio.SearchResponseItem
import com.example.toucheeseapp.data.model.specific_review.ReviewResponse
import com.example.toucheeseapp.data.model.studio_detail.StudioDetailResponse
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

    // 기능: 예약 정보 저장
    suspend fun setReservationData(token: String?, reservation: ProductReservation) = apiService.setReservationData(token, reservation)

    // -------- 장바구니 API --------

    // 기능 : 장바구니 저장 (회원)
    suspend fun saveCartsRequest(saveRequest: SaveCartsRequest) = apiService.saveCartsRequest(saveRequest)

    // 장바구니 목록 조회
    suspend fun cartList(memberId: Int): CartList {
        return apiService.cartList(memberId)
    }

    // 장바구니 옵션 및 인원 변경
    suspend fun updateCartOption(cartId: Int): CartOptionChange {
        return apiService.cartOptionChange(cartId)
    }

    // 기능 : 장바구니 삭제
    suspend fun cartDelete(cartId: Int) : Unit = apiService.cartDelete(cartId)
}