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
}