package com.toucheese.app.ui.viewmodel

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.toucheese.app.data.model.book_schedule.load_userbooklist.UserBookListItem
import com.toucheese.app.data.model.book_schedule.update_userbookschedule.UpdateUserBookSchedule
import com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponseItem
import com.toucheese.app.data.repository.BookScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookScheduleViewModel @Inject constructor(private val repository: BookScheduleRepository): ViewModel() {

    private val TAG = "BookScheduleViewModel"

    // 사용자 예약 내역
    private val _userBookList = MutableStateFlow<List<UserBookListItem>>(emptyList())
    val userBookList: StateFlow<List<UserBookListItem>> = _userBookList
    // ----- 예약 API -----

    // 사용자 예약 조회
    fun loadUserBookList(token: String?, page: Int = 0) {
        viewModelScope.launch {
            try {
                val response = repository.loadUserBookList(
                    token = "Bearer $token",
                    page = page,
                )
                // 사용자 예약 내역 조회
                _userBookList.value =  response.userBookList
            } catch (error: Exception){
                Log.d(TAG, "사용자 예약 내역 조회 error = ${error.localizedMessage}")
            }
        }
    }

    // 사용자 예약 일정 수정
    fun updateUserBookSchedule(
        token: String?,
        reservationId: Int,
        createDate: String,
        createTime: String
    ){
        // Body 객체 생성
        val updateUserBookSchedule = UpdateUserBookSchedule(createDate = createDate, createTime = createTime)

        viewModelScope.launch {
            try {
                repository.updateUserBookSchedule(
                    token = "Bearer $token",
                    reservationId = reservationId,
                    userBookSchedule = updateUserBookSchedule
                )
            } catch (error: Exception){
                Log.d(TAG, "사용자 예약 일정 수정 error = ${error.message}")
            }
        }
    }
    // ----- 스튜디오 API -----

    // 캘린더 휴무일 및 예약 희망 시간
    suspend fun loadCalendarTime(studioId: Int, yearMonth: String): List<CalendarTimeResponseItem> {
        return try {
            repository.loadCalendarTime(studioId, yearMonth).toList()
        } catch (error: Exception) {
            emptyList()
        }
    }
}