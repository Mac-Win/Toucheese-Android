package com.toucheese.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.toucheese.app.data.repository.QnaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QnaViewModel @Inject constructor(repository: QnaRepository): ViewModel() {
}