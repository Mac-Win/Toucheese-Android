package com.toucheese.app.data.repository

import com.toucheese.app.data.model.book_schedule.load_userbooklist.UserBookListResponse
import com.toucheese.app.data.model.book_schedule.update_userbookschedule.UpdateUserBookSchedule
import com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponse
import com.toucheese.app.data.network.BookScheduleService
import javax.inject.Inject

class BookScheduleRepository @Inject constructor(private val apiService: BookScheduleService) {

    // ----- 예약 API -----

    // 사용자 예약 조회
    suspend fun loadUserBookList(token: String?, page: Int): UserBookListResponse = apiService.loadUserBookList(token, page)

    // 사용자 예약 일정 수정
    suspend fun updateUserBookSchedule(token: String?, reservationId: Int, userBookSchedule: UpdateUserBookSchedule) = apiService.updateUserBookSchedule(token, reservationId, userBookSchedule)
    // ----- 스튜디오 API -----

    // 캘린더 휴무일 및 예약 희망 시간
    suspend fun loadCalendarTime(studioId: Int, yearMonth: String): com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponse = apiService.loadCalendarTime(studioId, yearMonth)
}