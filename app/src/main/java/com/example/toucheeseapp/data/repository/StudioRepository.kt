package com.example.toucheeseapp.data.repository

import com.example.toucheeseapp.data.model.concept_studio.Studio
import com.example.toucheeseapp.data.model.search_studio.SearchResponseItem
import com.example.toucheeseapp.data.network.ToucheeseServer
import javax.inject.Inject

class StudioRepository @Inject constructor(private val apiService: ToucheeseServer) {

    // 컨셉별 Studio 리스트 조회
    suspend fun getStudios(conceptId: Int, page: Int = 0): List<Studio> = apiService.getStudios(conceptId, page).studioList

    // Studio 검색 데이터 조회
    suspend fun searchStudios(keyword: String): List<SearchResponseItem> = apiService.searchStudios(keyword)
}