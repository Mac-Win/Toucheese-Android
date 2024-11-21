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

    init {
        loadStudios(1)
    }

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

    private fun loadStudios(conceptId: Int){
        viewModelScope.launch {
            try {
                val result = repository.getStudios(conceptId)
                _studios.value = result
                Log.d("Retrofit", "$result")
            } catch (e: Exception){
                Log.d("Retrofit", "error = ${e.message}")
            }
        }
    }
}