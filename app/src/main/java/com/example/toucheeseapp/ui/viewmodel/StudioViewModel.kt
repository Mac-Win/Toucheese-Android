package com.example.toucheeseapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toucheeseapp.data.model.concept_studio.Studio
import com.example.toucheeseapp.data.model.search_studio.SearchResponseItem
import com.example.toucheeseapp.data.repository.StudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudioViewModel @Inject constructor(
    private val repository: StudioRepository
): ViewModel() {

    private val _studios = MutableStateFlow<List<Studio>>(emptyList())
    val studios: StateFlow<List<Studio>> = _studios

    private val _searchStudios = MutableStateFlow<List<SearchResponseItem>>(emptyList())
    val searchStudios: StateFlow<List<SearchResponseItem>> = _searchStudios

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching // 현재 검색중인지 여부를 확인

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked

    // 검색 스튜디오  조회
    fun searchStudios(keyword: String){
        viewModelScope.launch {
            if(keyword.isBlank()){
                _isSearching.value = false
                _searchStudios.value = emptyList() // 검색어가 없을 경우 빈 리스트로 초기화
                Log.d("SearchState", "Cleared search, isSearching: ${_isSearching.value}")
                return@launch
            }

            _isSearching.value = true // 검색 시작
            Log.d("SearchState", "Searching for keyword: $keyword, isSearching: ${_isSearching.value}")

            try {
                Log.d("Search", "$keyword")
                val result = repository.searchStudios(keyword= keyword)
                _searchStudios.value = result
                Log.d("Search", "Results: $result")
                _isSearching.value = true
            } catch (e: Exception){
                Log.d("Search", "${e.message}")
                _searchStudios.value = emptyList() // 에러 발생 시 빈 리스트로 초기화
            }
        }
    }

    init {
        // conceptId가 고정된 경우 초기화 로직 실행
        loadStudiosByConcept(5) // 테스트용 conceptId
    }

    fun loadStudiosByConcept(conceptId: Int) {
        viewModelScope.launch {
            Log.d("StudioViewModel", "Loading studios for conceptId: $conceptId")
            try {
                // 임시 데이터
                val mockStudios = listOf(
                    Studio(
                        id = 5, // studioId 5와 매칭
                        name = "Mock Studio",
                        profileImage = "https://via.placeholder.com/150",
                        images = listOf(
                            "https://via.placeholder.com/400x300",
                            "https://via.placeholder.com/400x300?text=Image2"
                        ),
                        rating = 4.5,
                        price = 50000
                    )
                )
                _studios.value = mockStudios // 임시 데이터를 Flow에 삽입
                Log.d("StudioViewModel", "Mock studios loaded: $mockStudios")
            } catch (e: Exception) {
                Log.e("StudioViewModel", "Error loading studios: ${e.message}")
            }
        }
    }

    // 스튜디오 필터 데이터 조회
    fun filterStudio(conceptId: Int, selectedFilters: Map<Int,Int>) {
        val price = when (selectedFilters[0]){
            1 -> 99999
            2 -> 199999
            3 -> 200000
            else -> null
        }

        val rating = when(selectedFilters[1]){
            1 -> 3.0
            2 -> 4.0
            3 -> 4.5
            else -> null
        }

        val location = when(selectedFilters[2]){
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
                val result = repository.filterStudios(conceptId= conceptId, price = price, rating = rating, locations = listOf(location))
                _studios.value = result
                Log.d("Retrofit", "$result")
            } catch (e: Exception) {
                Log.d("Retrofit", "error = ${e.message}")
            }
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
}
