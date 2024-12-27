package com.toucheese.app.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toucheese.app.data.model.book_schedule.load_userbooklist.UserBookListItem
import com.toucheese.app.data.model.book_schedule.update_userbookschedule.UpdateUserBookSchedule
import com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponseItem
import com.toucheese.app.data.repository.BookScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class BookScheduleViewModel @Inject constructor(private val repository: BookScheduleRepository) :
    ViewModel() {

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
                _userBookList.value = response.userBookList
            } catch (error: Exception) {
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
    ) {
        // Body 객체 생성
        val updateUserBookSchedule =
            UpdateUserBookSchedule(createDate = createDate, createTime = createTime)

        viewModelScope.launch {
            try {
                repository.updateUserBookSchedule(
                    token = "Bearer $token",
                    reservationId = reservationId,
                    userBookSchedule = updateUserBookSchedule
                )
            } catch (error: Exception) {
                Log.d(TAG, "사용자 예약 일정 수정 error = ${error.message}")
            }
        }
    }
    // ----- 스튜디오 API -----

    // 캘린더 휴무일 및 예약 희망 시간 - 해당 월 전체
    suspend fun loadCalendarTime(
        studioId: Int,
        yearMonth: String
    ): List<CalendarTimeResponseItem> {
        return try {
            Log.d("BookScheduleViewModel", "캘린더 내역 조회 studioId : ${studioId}")
            Log.d("BookScheduleViewModel", "캘린더 내역 조회 yearMonth : ${yearMonth}")
            repository.loadCalendarTime(studioId, yearMonth).toList()
        } catch (error: Exception) {
            Log.e(TAG, "캘린더 내역 조회 에러 error = ${error.message}")
            Log.e(TAG, "캘린더 내역 조회 에러 error = ${error.localizedMessage}")
            emptyList()
        }
    }

    // 첫 번째 반환값 : Text Color
    // 두 번째 반환값 : Container Color
    fun makeValue(state: String): Triple<Color, Color, String> {
        return when (state) {
            "예약접수" -> Triple(Color(0xFF595959), Color(0xFFF0F0F0), "예약일정 변경")
            "예약확정" -> Triple(Color(0xFF1F1F1F), Color(0xFFFFD129), "리뷰쓰기")
            "촬영완료" -> Triple(Color(0xFF2B89FE), Color(0xFFF0F0F0), "예약일정 변경")
            "예약취소" -> Triple(Color(0xFFEF4444), Color(0xFFF0F0F0), "")
            else -> Triple(Color.White, Color.White, "")
        }
    }

    // String -> LocalDate 변환
    @RequiresApi(Build.VERSION_CODES.O)
    fun castToLocalDate(date: String): LocalDate {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(date, dateTimeFormatter)
    }

}