package com.toucheese.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.toucheese.app.data.repository.BookScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookScheduleViewModel @Inject constructor(repository: BookScheduleRepository): ViewModel() {

}