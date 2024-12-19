package com.toucheese.app.ui.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.toucheese.app.R
import com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponseItem
import com.toucheese.app.ui.components.calendar.CustomDatePickerComponent
import com.toucheese.app.ui.components.calendar.CustomDatePickerWeekComponent
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingScheduleChangeItemComponent(
    // 헤더 부분
    studioName: String,
    studioImage: String,
    statusLabel: String,
    createDate: String,
    createTime: String,
    chipTextColor: Color,
    chipContainerColor: Color,
    buttonLabelText: String,
    // 캘린더 부분
    selectedDate: LocalDate,
    calendarState: CalendarState<DynamicSelectionState>,
    // 시간대 부분
    selectedTime: String,
    operationTimeList: List<CalendarTimeResponseItem>,
    modifier: Modifier = Modifier,
    setSelectedTime: (String) -> Unit,
    setSelectedDate: (LocalDate) -> Unit,
    onCalendarOpenRequest: () -> Unit,
) {
    val morningTimes = operationTimeList[0].times.filter { time ->
        // LocalTime으로 변경
        val localTime = castToLocalTime(time)
        localTime < LocalTime.of(12, 0)
    }
    val afternoonTimes = operationTimeList[0].times.filter { time ->
        // LocalTime으로 변경
        val localTime = castToLocalTime(time)
        localTime >= LocalTime.of(12, 0)
    }



    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        BookingScheduleItemComponent(
            studioName = studioName,
            studioImage = studioImage,
            statusLabel = statusLabel,
            createDate = createDate,
            createTime = createTime,
            chipTextColor = chipTextColor,
            chipContainerColor = chipContainerColor,
            chipBorderColor = chipContainerColor,
            showButton = false,
            buttonLabel2 = buttonLabelText,
            elevation = CardDefaults.cardElevation(0.dp),
            onButtonClicked1 = { /* 버튼 자체가 표시 안 됨 */ },
            onButtonClicked2 = { /* 버튼 자체가 표시 안 됨 */ }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            AssistChip(
                onClick = onCalendarOpenRequest,
                label = {
                    Text(
                        text =  "${selectedDate.monthValue}월 ${selectedDate.dayOfMonth}일",
                        color = Color(0xFF595959)
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar_36px),
                        contentDescription = "Calender Icon",
                        tint = Color(0xFF8C8C8C)
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Arrow Forward Icon",
                        tint = Color(0xFF8C8C8C)
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = AssistChipDefaults.assistChipColors(
                    labelColor = Color(0xFF595959)
                ),
                border = BorderStroke(1.dp, Color(0xFFF0F0F0))
            )

            // 요일 및 날짜 표시
            CustomDatePickerWeekComponent(
                selectedDate = selectedDate,
                calendarState = calendarState,
            )
            Spacer(modifier = Modifier.height(4.dp))

            // 날짜 표시
            Spacer(modifier = Modifier.height(16.dp)) // 추가 간격

            // 예약 가능한 시간대 텍스트
            Text(
                text = "예약 가능한 시간대",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 오전 섹션
            Text(
                text = "오전",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            if (morningTimes.isNotEmpty()) {
                Log.d("BookScheduleScreen", "오전 시간 : ${morningTimes}")
                TimeSlotButtonComponent(
                    times = morningTimes,
                    selectedTime = selectedTime,
                    onTimeClick = setSelectedTime,
//                    isPast = ,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp)) // 섹션 간 간격
            }

            // 오후 섹션
            Text(
                text = "오후",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            if (afternoonTimes.isNotEmpty()) {
                Log.d("BookScheduleScreen", "오후 시간 : ${afternoonTimes}")
                TimeSlotButtonComponent(
                    times = afternoonTimes,
                    selectedTime = selectedTime,
                    onTimeClick = setSelectedTime,
//                    isPast = ,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// String -> LocalTime 변환
@RequiresApi(Build.VERSION_CODES.O)
fun castToLocalTime(time: String): LocalTime {
    val refinedTime = if (time.length == 4) "0$time" else time
    val format = DateTimeFormatter.ofPattern("HH:mm") // 시간 형식 정의
    return LocalTime.parse(refinedTime, format)
}