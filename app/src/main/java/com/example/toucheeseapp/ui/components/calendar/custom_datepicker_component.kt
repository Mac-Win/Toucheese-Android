package com.example.toucheeseapp.ui.components.calendar

import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerComponent(
    calendarSate: CalendarState<DynamicSelectionState>,
    isDateClicked: Boolean,
    onDismissRequest: () -> Unit,
    onDateClicked: (LocalDate) -> Unit = {},
) {


    Dialog(
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
        ),
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            ) {

            SelectableCalendar(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp),
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
                                // 버전 분기 처리
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    calendarSate.monthState.currentMonth =
                                        calendarSate.monthState.currentMonth.minusMonths(1)
                                } else {

                                }
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
                                // 버전 분기 처리
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    calendarSate.monthState.currentMonth =
                                        calendarSate.monthState.currentMonth.plusMonths(1)
                                } else {

                                }
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
                            .padding(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectionState.isDateSelected(date)) Color(
                                0xFFFFCC00
                            ) else Color.Transparent,
                            disabledContainerColor = Color.Gray,
                        ),
                        shape = RoundedCornerShape(50.dp)

                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    // 날짜 선택
                                    onDateClicked(date)
                                    Log.d("DatePicker", "date clicked: ${date}")
                                    selectionState.onDateSelected(date)

                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    date.dayOfMonth.toString()
                                } else {
                                    // 분기 처리
                                    ""
                                },
                            )
                        }
                    }
                },

                )
            // 날짜가 선택된 경우
            if (isDateClicked) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Gray,
                    modifier = Modifier.alpha(0.7f)
                )
                Text(
                    text = "예약 가능한 시간",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )

                // 예약시간 칩
                Column() {

                }

                // 확인 및 취소
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    // 취소
                    TextButton(
                        onClick = {
                            // 닫아준다
                            onDismissRequest()
                        },
                    ) {
                        Text(
                            text = "Cancel",
                        )
                    }

                    // 확인
                    TextButton(
                        onClick = {
                            // 닫아준다
                            onDismissRequest()
                        },
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text(
                            text = "OK",
                        )
                    }
                }
            }
        }
    }
}

@Preview()
@Composable()
fun CustomDatePickerPreview() {
    val calendarState = rememberSelectableCalendarState()

//    CustomDatePickerComponent(
//        calendarSate = calendarState,
//        isDateClicked = true,
//        onDateClicked = {},
//        onDismissClicked = {}
//    )
}