package com.toucheese.app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toucheese.app.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _studios =
        MutableStateFlow<List<com.toucheese.app.data.model.home.concept_studio.Studio>>(emptyList())
    val studios: StateFlow<List<com.toucheese.app.data.model.home.concept_studio.Studio>> = _studios

    private val _searchStudios =
        MutableStateFlow<List<com.toucheese.app.data.model.home.search_studio.SearchResponseItem>>(
            emptyList()
        )
    val searchStudios: StateFlow<List<com.toucheese.app.data.model.home.search_studio.SearchResponseItem>> =
        _searchStudios

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked

    private val _studioDetail =
        MutableStateFlow<com.toucheese.app.data.model.home.studio_detail.StudioDetailResponse?>(null)
    val studioDetail: StateFlow<com.toucheese.app.data.model.home.studio_detail.StudioDetailResponse?> =
        _studioDetail

    private val _studioReviews =
        MutableStateFlow<List<com.toucheese.app.data.model.home.review_studio.StudioReviewResponseItem>>(
            emptyList()
        )
    val studioReviews: StateFlow<List<com.toucheese.app.data.model.home.review_studio.StudioReviewResponseItem>> =
        _studioReviews

    private val _specificReview =
        MutableStateFlow<com.toucheese.app.data.model.home.specific_review.ReviewResponse?>(null)
    val specificReview: StateFlow<com.toucheese.app.data.model.home.specific_review.ReviewResponse?> =
        _specificReview

    private val _cartItems = MutableStateFlow<List<com.toucheese.app.data.model.home.carts_list.CartListResponseItem>>(emptyList())
    val cartItems: StateFlow<List<com.toucheese.app.data.model.home.carts_list.CartListResponseItem>> = _cartItems

    // 컨셉 이름
    private val _conceptName = MutableStateFlow("")
    val conceptName: StateFlow<String> = _conceptName

    // ----- 컨셉 API -----
    suspend fun loadConceptName(conceptId: Int) {
        val conceptList = repository.loadConcept().toList()
        val result = conceptList.find { it.id == conceptId }
        _conceptName.value = result?.name ?: "검색"
    }

    // -------- 스튜디오 API --------

    // 스튜디오 검색
    fun searchStudios(keyword: String) {
        viewModelScope.launch {
            if (keyword.isBlank()) {
                _searchStudios.value = emptyList() // 검색어가 없을 경우 빈 리스트로 초기화
                return@launch
            }

            try {

                val result = repository.searchStudios(keyword = keyword)
                _searchStudios.value = result
                Log.d("Search", "Results: $result")
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
    suspend fun loadCalendarTime(
        studioId: Int,
        yearMonth: String
    ): List<com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponseItem> {
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
    fun filterStudio(
        conceptId: Int,
        selectedPrice: Int, // 가격 필터
        selectedRating: Int, // 인기 필터
        selectedLocations: Set<Int>, // 지역 필터

    ) {
        val price = when (selectedPrice) {
            1 -> 99999
            2 -> 199999
            3 -> 200000
            else -> null
        }

        val rating = when (selectedRating) {
            1 -> 3.0
            2 -> 4.0
            3 -> 4.5
            else -> null
        }

        val location = mutableListOf<String>()
        if (!selectedLocations.contains(0)) {
            selectedLocations.forEach { index ->
                when (index) {
                    1 -> location.add("강남")
                    2 -> location.add("서초")
                    3 -> location.add("송파")
                    4 -> location.add("강서")
                    5 -> location.add("마포")
                    6 -> location.add("영등포")
                    7 -> location.add("강북")
                    8 -> location.add("용산")
                    9 -> location.add("성동")
                }
            }
        } else {
            // 전체인 경우
            location.clear()
            location.add("")
        }

        viewModelScope.launch {
            try {
                val result = repository.filterStudios(
                    conceptId = conceptId,
                    price = price,
                    rating = rating,
                    locations = location
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
    suspend fun loadProductReview(
        studioId: Int,
        productId: Int
    ): List<com.toucheese.app.data.model.home.review_studio.StudioReviewResponseItem>? {
        return try {
            repository.loadProductReview(studioId, productId).toList()
        } catch (error: Exception) {
            Log.d("StudioViewModel", "${error.message}")
            null
        }
    }

    // -------- 상품 API --------

    // 상품 상세 조회
    suspend fun loadProductDetail(productId: Int): com.toucheese.app.data.model.home.product_detail.ProductDetailResponse? {
        return try {
            repository.loadProductDetail(productId)
        } catch (error: Exception) {
            Log.d("StudioViewModel", "error = ${error.message}")
            null
        }
    }

    // -------- 예약 API --------

    // 장바구니 저장 기능
    suspend fun saveCartData(
        token: String?,
        cartData: com.toucheese.app.data.model.home.saveCartData.CartData
    ) {
        try {
            repository.saveCartData(token = "Bearer $token", reservation = cartData)
        } catch (error: Exception) {
            Log.d("StudioViewModel", "error = ${error.message}")
        }
    }

    // 예약 정보 저장 기능
    suspend fun saveReservationData(token: String?, cartIds: String) {
        try {
            val result = repository.saveReservationData(
                token = "Bearer $token",
                saveReservationRequest = com.toucheese.app.data.model.home.saveReservationData.SaveReservationRequest(
                    cartIds
                )
            )
            Log.d("StudioViewModel", "예약 정보 저장 기능: ${result}")
        } catch (error: Exception) {
            Log.d("StudioViewModel", "error = ${error.message}")
        }
    }

    // 장바구니 목록 조회
    fun loadCartList(token: String?) {
        viewModelScope.launch {
            try {
                val cartData = repository.loadCartList(token = "Bearer $token").toList()
                _cartItems.value = cartData
                Log.d("StudioViewModel", "cardData: ${cartData}")
            } catch (error: Exception) {
                Log.e("StudioViewModel", "Error fetching cart list: ${error.message}", error)
            } finally {
            }
        }
    }

    // 해당 장바구니 삭제
    fun deleteCartItem(token: String?, cartId: Int) {
        viewModelScope.launch {
            try {
                // API 호출을 통해 서버에서 항목 삭제
                Log.d("StudioViewModel", "CartId: $cartId")
                Log.d("StudioViewModel,", "token: $token")
                repository.deleteCartItem("Bearer $token", cartId)
                Log.d("StudioViewModel", "CartId2: $cartId")
                loadCartList(token)
                // 로컬 상태에서 삭제된 항목 제거
                _cartItems.value = _cartItems.value.filter { it.cartId != cartId }
            } catch (error: Exception) {
                Log.e("StudioViewModel", "장바구니 삭제 중 오류 발생: ${error.localizedMessage}")
            }
        }
    }

    // 장바구니 옵션 및 인원 변경
    fun updateCartItem(
        token: String?,
        cartId: Int,
        changedCartItem: com.toucheese.app.data.model.home.carts_optionChange.ChangedCartItem
    ) {
        viewModelScope.launch {
            try {
                repository.updateCartItem("Bearer $token", cartId, changedCartItem)
                loadCartList(token)
            } catch (error: Exception) {
                Log.e("StudioViewModel", "장바구니 옵션 및 인원 변경 error: ${error.message}")
            }
        }

    }

    // 장바구니 결제 조회
    suspend fun loadOrderPayData(
        token: String?,
        cartIds: String
    ): com.toucheese.app.data.model.home.cart_order_pay.OrderPayResponse? {
        return try {
            Log.d("StudioViewModel", "cardIds=$cartIds")
            val result = repository.loadOrderPayData("Bearer $token", cartIds)
            Log.d("StudioViewModel", "장바구니 결제 조회 result: ${result}")
            result
        } catch (error: Exception) {
            Log.e("StudioViewModel", "장바구니 결제 조회 error: ${error}")
            null
        }
    }


    // 검색 상태 변환
    fun stopSearch() {
        _searchStudios.value = emptyList()
    }

    // 북마크 상태 변경 함수
    fun toggleBookmark() {
        _isBookmarked.value = !_isBookmarked.value
    }
}
