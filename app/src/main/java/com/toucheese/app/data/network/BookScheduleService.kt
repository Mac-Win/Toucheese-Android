package com.toucheese.app.data.network

import com.toucheese.app.data.model.book_schedule.load_userbooklist.UserBookListResponse
import com.toucheese.app.data.model.book_schedule.update_userbookschedule.UpdateUserBookSchedule
import com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BookScheduleService {

    // ----- 예약 API -----

    // 사용자 예약 조회
    @GET("v1/members/reservations")
    suspend fun loadUserBookList(
        @Header("Authorization") token: String?,
        @Query("page") page: Int,
    ): UserBookListResponse

    // 사용자 예약 일정 수정
    @PUT("v1/members/reservations/{reservationId}")
    suspend fun updateUserBookSchedule(
        @Header("Authorization") token: String?,
        @Path("reservationId") reservationId: Int,
        @Body userBookSchedule: UpdateUserBookSchedule
    )

    // ----- 스튜디오 API -----

    // 캘린더 휴무일 및 예약 희망 시간
    @GET("v1/studios/{studioId}/calendars")
    suspend fun loadCalendarTime(
        @Path("studioId") studioId: Int,
        @Query("yearMonth") yearMonth: String,
    ): CalendarTimeResponse
}