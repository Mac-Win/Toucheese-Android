package com.toucheese.app.ui.components.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomDatePickerWeekComponent(
    selectedDate: LocalDate,
    calendarState: CalendarState<DynamicSelectionState>,
    modifier: Modifier = Modifier,
) {
    SelectableCalendar(
        calendarState = calendarState,
        monthHeader = {},
        showAdjacentMonths = false,
        horizontalSwipeEnabled = false,
        dayContent = { state ->
            val date = state.date
            val selectionState = state.selectionState
            Log.d(
                "CustomDatePickerWeekComponent",
                "state = ${date}, selectionState = ${selectionState}"
            )
            // 선택된 날짜와 같은 주에 속하는지 여부
            val isSameWeek: Boolean = isSameWeek(selectedDate, date)
            // 선택된 날짜인지 여부
            val isSelectedDate: Boolean = selectedDate == date
            // 현재보다 과거인지 여부
            val isPastDate = date.isBefore(selectedDate)
            Log.d("CustomDatePickerWeekComponent", "같은 주에 속하나? : $isSameWeek")

            if (isSameWeek) {
                Card(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(4.dp),
                    // 휴무일이거나 과거인 경우 비활성화
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelectedDate) MaterialTheme.colorScheme.primary else Color.Transparent,
                        disabledContainerColor = Color.Gray,
                    ),
                    shape = RoundedCornerShape(50.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = date.dayOfMonth.toString(),
                            // 휴무일이거나 과거인 경우 비활성화
                            color = if (/*day.status &&*/ !isPastDate) Color.Black else Color.Gray.copy(
                                alpha = 0.5f
                            )
                        )
                    }
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun isSameWeek(date1: LocalDate, date2: LocalDate): Boolean {
    // 로케일에 따라 주 계산 방식 결정 (기본은 ISO-8601, 주 시작은 월요일)
    val weekFields = WeekFields.of(Locale.getDefault())
    val weekOfYear1 = date1.get(weekFields.weekOfWeekBasedYear())
    val weekOfYear2 = date2.get(weekFields.weekOfWeekBasedYear())
    val year1 = date1.get(weekFields.weekBasedYear())
    val year2 = date2.get(weekFields.weekBasedYear())

    return weekOfYear1 == weekOfYear2 && year1 == year2
}