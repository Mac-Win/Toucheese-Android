package com.example.toucheeseapp.data.repository

import com.example.toucheeseapp.data.model.Studio
import com.example.toucheeseapp.data.network.ToucheeseServer
import javax.inject.Inject

class StudioRepository @Inject constructor(private val apiService: ToucheeseServer) {

    // 컨셉별 Studio 리스트 조회
    suspend fun getStudios(conceptId: Int, page: Int = 0): List<Studio> = apiService.getStudios(conceptId, page).studioList

    //
}