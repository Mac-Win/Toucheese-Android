package com.example.toucheeseapp.ui.components.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.toucheeseapp.data.model.calendar_studio.CalendarTimeResponseItem
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerComponent(
    selectedDate: String,
    operationHours: List<CalendarTimeResponseItem>,
    calendarSate: CalendarState<DynamicSelectionState>,
    isDateClicked: Boolean,
    onDismissRequest: () -> Unit,
    onMonthChanged: (YearMonth) -> Unit,
    onDateClicked: (LocalDate) -> Unit = {},
    onTimeClicked: (String, String) -> Unit
) {

    Dialog(
        properties = DialogProperties(
            dismissOnClickOutside = true, // 외부 영역 클릭 시 꺼짐
            dismissOnBackPress = true, // 백버튼 클릭 시 꺼짐
        ),
        onDismissRequest = onDismissRequest, // 꺼짐 요청
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFFCF5)
            )
        ) {
            SelectableCalendar(
                modifier = Modifier.padding(16.dp),
                calendarState = calendarSate,
                monthHeader = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 이전 달로 이동
                        IconButton(
                            onClick = {
                                calendarSate.monthState.currentMonth =
                                    calendarSate.monthState.currentMonth.minusMonths(1)
                                Log.d(
                                    "DatePicked",
                                    "currentMonth: ${calendarSate.monthState.currentMonth}"
                                )
                                onMonthChanged(calendarSate.monthState.currentMonth)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                                contentDescription = "이전 달로 이동"
                            )
                        }

                        Text(
                            text = "${calendarSate.monthState.currentMonth}"
                        )

                        // 다음 달로 이동
                        IconButton(
                            onClick = {
                                calendarSate.monthState.currentMonth =
                                    calendarSate.monthState.currentMonth.plusMonths(1)
                                Log.d(
                                    "DatePicked",
                                    "currentMonth: ${calendarSate.monthState.currentMonth}"
                                )
                                onMonthChanged(calendarSate.monthState.currentMonth)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                                contentDescription = "이전 달로 이동"
                            )
                        }
                    }
                },

                showAdjacentMonths = false, // 해당 월에 포함되지 않는 날짜 제거
                dayContent = { state ->
                    val date = state.date
                    val selectionState = state.selectionState
                    Card(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .clickable {
                                // 날짜 선택
                                onDateClicked(date)
                                selectionState.onDateSelected(date)
                                Log.d("DatePicker", "date clicked: ${date}")
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectionState.isDateSelected(date)) Color(
                                0xFFFFF2CC
                            ) else Color.Transparent,
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
                                color = Color.Black
                            )
                        }
                    }
                },

                )
            Log.d("DatePicker", "isDateClicked: ${isDateClicked}")
            Log.d("DatePicker", "DatePicker operationHours: ${operationHours}")

            val reservationDateList = operationHours.filter { item ->
                // 선택한 날짜 추출
                item.date == selectedDate
            }
            // 날짜가 선택된 경우
            if (isDateClicked) {
                if (reservationDateList.isNotEmpty()) {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.Gray,
                        modifier = Modifier.alpha(0.7f)
                    )
                    Text(
                        text = "예약 가능한 시간",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                    val reservationDate = reservationDateList[0]
                    // 선택한 날짜의 시간 추출
                    val availableTime = reservationDate.times

                    if (availableTime.isNotEmpty()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(availableTime) { time ->
                                SuggestionChip(
                                    enabled = isPastTime(selectedDate, time),
                                    onClick = {
                                        Log.d("DatePicker", "clicked Time: $time")
                                        onDismissRequest()
                                        onTimeClicked(selectedDate, time)
                                    },
                                    colors = SuggestionChipDefaults.suggestionChipColors(
                                        containerColor = Color(0xFFFFF2CC),
                                        disabledContainerColor = Color.Gray,
                                    ),
                                    label = {
                                        Text(
                                            text = time,
                                            maxLines = 1,
                                            color = Color.Black,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.width(60.dp)
                                        )
                                    },
                                    modifier = Modifier
                                        .width(80.dp)
                                        .padding(horizontal = 4.dp)
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "예약 가능한 시간이 없습니다.",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 24.dp)
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun isPastTime(date: String, time: String): Boolean {
    return try {
        // `time` 정규화: 한 자리 숫자를 두 자리로 패딩
        val normalizedTime = if (time.length == 4) "0$time" else time

        // 날짜와 시간 파싱
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val parsedDate = LocalDate.parse(date, dateFormatter)
        val parsedTime = LocalTime.parse(normalizedTime, timeFormatter)

        // `date`와 `time`을 결합해 LocalDateTime 생성
        val inputDateTime = parsedDate.atTime(parsedTime)

        // 현재 시각
        val now = LocalDateTime.now()

        // 입력이 과거인지 판단
        !inputDateTime.isBefore(now)
    } catch (e: DateTimeParseException) {
        false // 형식 오류 발생 시 false 반환
    }
}