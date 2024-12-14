package com.example.toucheeseapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.toucheeseapp.data.repository.QnaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QnaViewModel @Inject constructor(
    private val repository: QnaRepository
): ViewModel() {

}