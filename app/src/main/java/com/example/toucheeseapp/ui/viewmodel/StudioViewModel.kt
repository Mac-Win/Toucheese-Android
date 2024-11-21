package com.example.toucheeseapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toucheeseapp.data.model.Studio
import com.example.toucheeseapp.data.network.ToucheeseServer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudioViewModel(private val api: ToucheeseServer): ViewModel() {

    private val _studios = MutableStateFlow<List<Studio>>(emptyList())
    val studios: StateFlow<List<Studio>> = _studios

    fun fetchStudios(conceptId: Int, page: Int){
        viewModelScope.launch {
            try {
                val response = api.getStudios(conceptId, page)
                Log.d("Retrofit", "$response")
            } catch (e: Exception){

                Log.d("Retrofit", "${e.message}")
            }
        }
    }
}