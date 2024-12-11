package com.example.toucheeseapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toucheeseapp.data.model.calendar_studio.CalendarTimeResponseItem
import com.example.toucheeseapp.data.model.carts_request.SaveCartsRequest
import com.example.toucheeseapp.data.model.concept_studio.Studio
import com.example.toucheeseapp.data.model.product_detail.ProductDetailResponse
import com.example.toucheeseapp.data.model.saveCartData.CartData
import com.example.toucheeseapp.data.model.review_studio.StudioReviewResponseItem
import com.example.toucheeseapp.data.model.search_studio.SearchResponseItem
import com.example.toucheeseapp.data.model.specific_review.ReviewResponse
import com.example.toucheeseapp.data.model.studio_detail.StudioDetailResponse
import com.example.toucheeseapp.data.repository.StudioRepository
import com.example.toucheeseapp.data.model.carts_list.CartListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudioViewModel @Inject constructor(
    private val repository: StudioRepository
) : ViewModel() {

    private val _studios = MutableStateFlow<List<Studio>>(emptyList())
    val studios: StateFlow<List<Studio>> = _studios

    private val _searchStudios = MutableStateFlow<List<SearchResponseItem>>(emptyList())
    val searchStudios: StateFlow<List<SearchResponseItem>> = _searchStudios

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching // 현재 검색중인지 여부를 확인

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked

    private val _studioDetail = MutableStateFlow<StudioDetailResponse?>(null)
    val studioDetail: StateFlow<StudioDetailResponse?> = _studioDetail

    private val _studioReviews = MutableStateFlow<List<StudioReviewResponseItem>>(emptyList())
    val studioReviews: StateFlow<List<StudioReviewResponseItem>> = _studioReviews

    private val _specificReview = MutableStateFlow<ReviewResponse?>(null)
    val specificReview: StateFlow<ReviewResponse?> = _specificReview

    private val _cartItems = MutableStateFlow<List<CartListItem>>(emptyList())
    val cartItems: StateFlow<List<CartListItem>> = _cartItems

    // 로딩 상태를 관리
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // -------- 스튜디오 API --------

    // 스튜디오 검색
    fun searchStudios(keyword: String) {
        viewModelScope.launch {
            if (keyword.isBlank()) {
                _isSearching.value = false
                _searchStudios.value = emptyList() // 검색어가 없을 경우 빈 리스트로 초기화
                return@launch
            }

            _isSearching.value = true // 검색 시작

            try {

                val result = repository.searchStudios(keyword = keyword)
                _searchStudios.value = result
                Log.d("Search", "Results: $result")
                _isSearching.value = true
            } catch (e: Exception) {
                Log.d("Search", "${e.message}")
                _searchStudios.value = emptyList() // 에러 발생 시 빈 리스트로 초기화
            }
        }
    }

    // 스튜디오 상세 조회
    fun loadStudioDetail(studioId: Int) {
        viewModelScope.launch {
            try {
                val result = repository.loadStudioDetail(studioId)
                _studioDetail.value = result
            } catch (error: Exception) {
                Log.d("StudioViewModel", "error = ${error.message}")
            }
        }
    }

    // 캘린더 휴무일 및 예약 시간
    suspend fun loadCalendarTime(studioId: Int, yearMonth: String): List<CalendarTimeResponseItem> {
        return try {
            repository.loadCalendarTime(studioId, yearMonth).toList()
        } catch (error: Exception) {
            emptyList()
        }
    }

    // -------- 해당 컨셉 스튜디오 API -------

    // 컨셉 스튜디오 목록 조회
    fun getConceptStudio(conceptId: Int) {
        // 추후에 Paging 추가
        viewModelScope.launch {
            try {
                _studios.value = repository.getConceptStudios(conceptId = conceptId, page = 0)
            } catch (error: Exception) {
                Log.e("StudioViewModel", "error =  ${error.message}")
            }
        }
    }

    // 필터 적용 후 스튜디오 목록 조회
    fun filterStudio(conceptId: Int, selectedFilters: Map<Int, Int>) {
        val price = when (selectedFilters[0]) {
            1 -> 99999
            2 -> 199999
            3 -> 200000
            else -> null
        }

        val rating = when (selectedFilters[1]) {
            1 -> 3.0
            2 -> 4.0
            3 -> 4.5
            else -> null
        }

        val location = when (selectedFilters[2]) {
            1 -> "강남"
            2 -> "서초"
            3 -> "송파"
            4 -> "강서"
            5 -> "마포"
            6 -> "영등포"
            7 -> "강북"
            8 -> "용산"
            9 -> "성동"
            else -> ""
        }

        viewModelScope.launch {
            try {
                val result = repository.filterStudios(
                    conceptId = conceptId,
                    price = price,
                    rating = rating,
                    locations = listOf(location)
                )
                _studios.value = result
                Log.d("Retrofit", "$result")
            } catch (e: Exception) {
                Log.d("Retrofit", "error = ${e.message}")
            }
        }
    }

    // -------- 리뷰 API --------

    // 스튜디오 리뷰 목록 조회
    fun loadStudioReviewList(studioId: Int) {
        viewModelScope.launch {
            try {
                _studioReviews.value = repository.loadStudioReviewList(studioId)
            } catch (error: Exception) {
                Log.d("StudioViewModel", "${error.message}")
            }
        }
    }

    // 특정 리뷰 상세 조회
    fun loadStudioSpecificReview(studioId: Int, reviewId: Int) {
        viewModelScope.launch {
            try {
                val review = repository.loadStudioSpecificReview(studioId, reviewId)
                _specificReview.value = review
                Log.d("StudioViewModel", "Loaded specific review: $review")
            } catch (error: Exception) {
                Log.d("StudioViewModel", "${error.message}")
            }
        }
    }

    // 특정 상품 리뷰 목록 조회
    suspend fun loadProductReview(studioId: Int, productId: Int): List<StudioReviewResponseItem>? {
        return try {
            repository.loadProductReview(studioId, productId).toList()
        } catch (error: Exception) {
            Log.d("StudioViewModel", "${error.message}")
            null
        }
    }

    // -------- 상품 API --------

    // 상품 상세 조회
    suspend fun loadProductDetail(productId: Int): ProductDetailResponse? {
        return try {
            repository.loadProductDetail(productId)
        } catch (error: Exception) {
            Log.d("StudioViewModel", "error = ${error.message}")
            null
        }
    }

    // -------- 예약 API --------

    // 장바구니 저장 기능
    suspend fun saveCartData(token: String?, reservation: CartData) {
        try {
            repository.saveCartData(token = "Bearer $token", reservation = reservation)
        } catch (error: Exception) {
            Log.d("StudioViewModel", "error = ${error.message}")
        }
    }

    // 검색 상태 변환
    fun stopSearch(isSearching: Boolean) {
        _isSearching.value = !isSearching
        _searchStudios.value = emptyList()
    }

    // 북마크 상태 변경 함수
    fun toggleBookmark() {
        _isBookmarked.value = !_isBookmarked.value
    }

    // -------- 장바구니 API --------

    // 기능 : 장바구니 저장 (회원)
    suspend fun saveCartsRequest(saveRequest: SaveCartsRequest){
        try {
            repository.saveCartsRequest(saveRequest = saveRequest)
        } catch (error: Exception) {
            Log.d("StudioViewModel","error = ${error.message}")
        }
    }

    // 기능 : 장바구니 목록 조회 (회원)
    fun cartList(memberId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val cartData = repository.cartList(memberId)
                _cartItems.value = cartData
            } catch (error: Exception) {
                Log.e("StudioViewModel", "Error fetching cart list: ${error.localizedMessage}", error)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 기능 : 장바구니 삭제
    fun deleteCartItem(cartId: Int) {
        viewModelScope.launch {
            try {
                // API 호출을 통해 서버에서 항목 삭제
                repository.cartDelete(cartId)

                // 로컬 상태에서 삭제된 항목 제거
                _cartItems.value = _cartItems.value.filter { it.cartId != cartId }
            } catch (error: Exception) {
                Log.e("StudioViewModel", "장바구니 삭제 중 오류 발생: ${error.localizedMessage}")
            }
        }
    }

}
