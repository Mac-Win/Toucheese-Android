package com.example.toucheeseapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toucheeseapp.data.model.concept_studio.Studio
import com.example.toucheeseapp.data.model.filter_studio.FilterResponse
import com.example.toucheeseapp.data.model.filter_studio.FilterStudio
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

//    private val _filterStudios = MutableStateFlow<List<FilterStudio>>(emptyList())
//    val filterStudios: StateFlow<List<FilterStudio>> = _filterStudios

    init {
        loadStudios(1)
    }

    // 검색 스튜디오  조회
    fun searchStudios(keyword: String){
        viewModelScope.launch {
            try {
                Log.d("Search", "$keyword")
                val result = repository.searchStudios(keyword= keyword)
                _searchStudios.value = result
                Log.d("Search", "$result")
            } catch (e: Exception){
                Log.d("Search", "${e.message}")
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
}