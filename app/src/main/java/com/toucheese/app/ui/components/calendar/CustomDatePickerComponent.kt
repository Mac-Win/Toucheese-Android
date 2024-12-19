package com.toucheese.app.ui.components.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponseItem
import com.toucheese.app.ui.components.castToLocalTime
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
    selectedTime: String,
    selectedDate: String,
    operationHours: List<CalendarTimeResponseItem>,
    calendarState: CalendarState<DynamicSelectionState>,
    onDismissRequest: () -> Unit,
    onMonthChanged: (YearMonth) -> Unit,
    onDateClicked: (LocalDate) -> Unit = {},
    onTimeClicked: (String, String) -> Unit
) {
    // 예약 확정시 전송할 데이터
    var confirmDay by remember { mutableStateOf<List<CalendarTimeResponseItem>>(emptyList()) }
    var confirmTime by remember { mutableStateOf("") }
    
    Dialog(
        properties = DialogProperties(
            dismissOnClickOutside = false, // 외부 영역 클릭 시 꺼짐 X
            dismissOnBackPress = true, // 백버튼 클릭 시 꺼짐
        ),
        onDismissRequest = onDismissRequest, // 꺼짐 요청
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            SelectableCalendar(
                modifier = Modifier.padding(16.dp),
                horizontalSwipeEnabled = false,
                calendarState = calendarState,
                monthHeader = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 이전 달로 이동
                        IconButton(
                            onClick = {
                                calendarState.monthState.currentMonth =
                                    calendarState.monthState.currentMonth.minusMonths(1)
                                Log.d(
                                    "DatePicked",
                                    "currentMonth: ${calendarState.monthState.currentMonth}"
                                )
                                onMonthChanged(calendarState.monthState.currentMonth)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                                contentDescription = "이전 달로 이동"
                            )
                        }

                        Text(
                            text = "${calendarState.monthState.currentMonth}"
                        )

                        // 다음 달로 이동
                        IconButton(
                            onClick = {
                                calendarState.monthState.currentMonth =
                                    calendarState.monthState.currentMonth.plusMonths(1)
                                Log.d(
                                    "DatePicked",
                                    "currentMonth: ${calendarState.monthState.currentMonth}"
                                )
                                onMonthChanged(calendarState.monthState.currentMonth)
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
                    //  서버에서 받아온 데이터 중에 같은 날짜를 출력
                    confirmDay = operationHours.filter { calendarTimeResponseItem ->
                        calendarTimeResponseItem.date == date.toString()
                    }
                    if (confirmDay.isNotEmpty()) {
                        // date에 해당하는 날짜 데이터 받아옴: CalendarTimeResponseItem
                        val day = confirmDay[0]
                        val today = LocalDate.now()
                        // 현재보다 과거인지 여부
                        val isPastDate = date.isBefore(today)
                        Card(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(4.dp)
                                // 휴무일이거나 과거인 경우 비활성화
                                .clickable(enabled = day.status && !isPastDate) {
                                    // 날짜 선택
                                    onDateClicked(date)
                                    selectionState.onDateSelected(date)
                                    onTimeClicked(date.toString(), "")
                                    Log.d("DatePicker", "date clicked: ${date}")
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectionState.isDateSelected(date)) MaterialTheme.colorScheme.primary else Color.Transparent,
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
                                    color = if (day.status && !isPastDate) Color.Black else Color.Gray.copy(
                                        alpha = 0.5f
                                    )
                                )
                            }
                        }
                    }
                },
            )
            val reservationDateList = operationHours.filter { item ->
                // 선택한 날짜 추출
                item.date == selectedDate
            }
            // 날짜가 선택된 경우
            if (reservationDateList.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "예약 가능한 시간대",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                val reservationDate = reservationDateList[0]
                // 선택한 날짜의 시간 추출
                val morningTimes = reservationDate.times.filter { time ->
                    // LocalTime으로 변경
                    val localTime = castToLocalTime(time)
                    localTime < LocalTime.of(12, 0)
                }
                val afternoonTimes = reservationDate.times.filter { time ->
                    // LocalTime으로 변경
                    val localTime = castToLocalTime(time)
                    localTime >= LocalTime.of(12, 0)
                }


                if (morningTimes.isNotEmpty()) {
                    Text(
                        text = "오전",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .heightIn(min = 50.dp, max = 500.dp)
                            .padding(16.dp)
                    ) {
                        items(morningTimes) { time ->
                            val isSelected = time == selectedTime
                            val isPast = isPastTime(selectedDate, time)
                            SuggestionChip(
                                enabled = isPast,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFD9D9D9)
                                ),
                                label = {
                                    Text(
                                        text = time,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    labelColor = Color(0xFF1F1F1F),
                                    disabledContainerColor = Color(0xFFD9D9D9),
                                ),
                                modifier = Modifier
                                    .width(96.dp)
                                    .height(40.dp),
                                onClick = {
                                    onTimeClicked(selectedDate, time)
                                    confirmTime = time
                                },
                            )
                        }
                    }
                }
                if (afternoonTimes.isNotEmpty()) {
                    Text(
                        text = "오후",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .heightIn(min = 50.dp, max = 500.dp)
                            .padding(16.dp)
                    ) {
                        items(afternoonTimes) { time ->
                            val isSelected = time == selectedTime
                            val isPast = isPastTime(selectedDate, time)
                            SuggestionChip(
                                enabled = isPast,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected && !isPast) MaterialTheme.colorScheme.primary
                                    else Color(0xFFD9D9D9)
                                ),
                                label = {
                                    Text(
                                        text = time,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    labelColor = Color(0xFF1F1F1F),
                                    disabledContainerColor = Color(0xFFD9D9D9),
                                ),
                                modifier = Modifier
                                    .width(96.dp)
                                    .height(40.dp),
                                onClick = {
                                    onTimeClicked(selectedDate, time)
                                    confirmTime = time
                                },
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
            // 닫기 및 예약일 선택 버튼
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                // 닫기 버튼
                Button(
                    border = BorderStroke(1.dp, Color(0xFFF0F0F0)),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF0F0F0),
                    ),
                    modifier = Modifier.weight(1f),
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = "닫기",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF595959)
                    )
                }

                // 예약일 선택 버튼
                Button(
                    border = BorderStroke(1.dp, Color(0xFFFFD129)),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFD129),
                    ),
                    modifier = Modifier.weight(1f),
                    onClick = {

                    }
                ) {
                    Text(
                        text = "예약일 선택",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF1F1F1F)
                    )
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