package com.example.toucheeseapp.data.repository

import com.example.toucheeseapp.data.model.concept_studio.Studio
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

    // -------- 리뷰 API --------

    // 스튜디오 리뷰 목록 조회
    suspend fun loadStudioReviewList(studioId: Int): StudioReviewResponse = apiService.loadStudioReviewList(studioId)

    // 특정 리뷰 상세 조회
    suspend fun loadStudioSpecificReview(studioId: Int, reviewId: Int): ReviewResponse = apiService.loadStudioSpecificReview(studioId, reviewId)

    // 특정 상품 리뷰 목록 조회
    suspend fun loadProductReview(studioId: Int, productId: Int):StudioReviewResponse = apiService.loadProductReview(studioId, productId)
}