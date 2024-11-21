package com.example.toucheeseapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toucheeseapp.data.model.Studio
import com.example.toucheeseapp.data.network.ToucheeseServer
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

    init {
        loadStudios(1)
    }
    fun fetchStudios(conceptId: Int){
        viewModelScope.launch {
            try {
                val result = repository.getStudios(conceptId, page = 0)
                _studios.value = result
                Log.d("Retrofit", "$result")
            } catch (e: Exception){

                Log.d("Retrofit", "${e.message}")
            }
        }
    }

    private fun loadStudios(conceptId: Int){
        viewModelScope.launch {
            val result = repository.getStudios(conceptId)
            _studios.value = result
            Log.d("Retrofit", "$result")
        }
    }
}