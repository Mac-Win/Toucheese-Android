package com.toucheese.app.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toucheese.app.R
import com.toucheese.app.data.model.calendar_studio.CalendarTimeResponseItem
import com.toucheese.app.ui.components.calendar.CustomDatePickerComponent
import com.toucheese.app.ui.theme.ToucheeseAppTheme
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.time.YearMonth
import java.util.Locale
import java.time.temporal.TemporalAdjusters

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingScheduleChangeItemComponent(
    modifier: Modifier = Modifier,
    operationHours: List<CalendarTimeResponseItem>,
    calendarState: CalendarState<DynamicSelectionState>
    ){
        // 선택된 날짜를 상태로 관리
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }
        // 선택된 시간을 상태로 관리
        var selectedTime by remember { mutableStateOf(LocalTime.now()) }

        var isDatePickerOpen by remember { mutableStateOf(false) }

        val startOfWeek = selectedDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        val weekDates = (0..6).map { startOfWeek.plusDays(it.toLong())}

        // 선택된 날짜에 대한 예약 가능한 시간 가져오기
        val selectedDayOperationHours = operationHours.find { it.date == selectedDate.toString() }?.times ?: emptyList()

        // 오전과 오후로 시간대 분리
        val morningTimes = selectedDayOperationHours.filter {
            val hour = it.substringBefore(":").toIntOrNull() ?: 0
            hour < 12
        }

        val afternoonTimes = selectedDayOperationHours.filter {
            val hour = it.substringBefore(":").toIntOrNull() ?: 0
            hour >= 12
        }



    Card (
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            BookingScheduleItemComponent(
                showButton = false
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(
                    onClick = {
                        isDatePickerOpen = true
                    },
                    label = { Text("${selectedDate.monthValue}월 ${selectedDate.dayOfMonth}일") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.calendar_36px),
                            contentDescription = "Calender Icon",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Arrow Forward Icon",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            // 요일 및 날짜 표시
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                // 요일 표시
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    weekDates.forEach { date ->
                        Text(
                            text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // 날짜 표시
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    weekDates.forEach { date ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    /* 날짜 선택 이벤트 */
                                    selectedDate = date
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = date.dayOfMonth.toString(),
                                color = if (date == selectedDate) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .background(
                                        color = if (date == selectedDate) MaterialTheme.colorScheme.primary else Color.Transparent,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp)) // 추가 간격

            // 예약 가능한 시간대 텍스트
            Text(
                text = "예약 가능한 시간대",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 오전 섹션
            if (morningTimes.isNotEmpty()) {
                Text(
                    text = "오전",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                TimeSlotButtonComponent(
                    times = morningTimes,
                    selectedTime = selectedTime?.toString(),
                    onTimeClick = { time ->
                        selectedTime = LocalTime.parse(time)
                        println("Selected morning time: $time")
                    },
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(16.dp)) // 섹션 간 간격
            }

            // 오후 섹션
            if (afternoonTimes.isNotEmpty()) {
                Text(
                    text = "오후",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                TimeSlotButtonComponent(
                    times = afternoonTimes,
                    selectedTime = selectedTime?.toString(),
                    onTimeClick = { time ->
                        selectedTime = LocalTime.parse(time)
                        println("Selected afternoon time: $time")
                    },
                    modifier = Modifier
                )
            }
        }
    }

    if (isDatePickerOpen) {
        CustomDatePickerComponent(
            selectedDate = selectedDate.toString(),
            operationHours = operationHours,
            calendarState = calendarState,
            isDateClicked = true,
            onDismissRequest = { isDatePickerOpen = false },
            onMonthChanged = { month ->

            },
            onDateClicked = { date ->
                selectedDate = date
                isDatePickerOpen = false
            },
            onTimeClicked = { date, time ->

            }
        )
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun BookingScheduleChangeItemPreview() {
    // 샘플 CalendarState 생성
    val sampleCalendarState = remember {
        CalendarState(
            monthState = MonthState(
                initialMonth = YearMonth.now(),
                minMonth = YearMonth.now().minusMonths(6),
                maxMonth = YearMonth.now().plusMonths(6)
            ),
            selectionState = DynamicSelectionState(
                selectionMode = SelectionMode.Single,
                selection = listOf(LocalDate.now())
            )
        )
    }


    // 샘플 operationHours 데이터
    val sampleOperationHours = listOf(
        CalendarTimeResponseItem(
            date = LocalDate.now().toString(),
            times = listOf("10:00", "14:00", "16:00"),
            status = true
        ),
        CalendarTimeResponseItem(
            date = LocalDate.now().plusDays(1).toString(),
            times = listOf("09:00", "13:00", "15:00"),
            status = true
        ),
    )

    ToucheeseAppTheme {
        Surface {
            BookingScheduleChangeItemComponent(
                operationHours = sampleOperationHours,
                calendarState = sampleCalendarState, // 'calendarSate' → 'calendarState'로 수정
            )
        }
    }
}