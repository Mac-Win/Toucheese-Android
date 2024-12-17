package com.example.toucheeseapp.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.toucheeseapp.data.model.calendar_studio.CalendarTimeResponseItem
import com.example.toucheeseapp.ui.theme.ToucheeseAppTheme
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingScheduleChangeScreen(
    operationHours: List<CalendarTimeResponseItem>,
    calendarState: CalendarState<DynamicSelectionState>,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            com.example.toucheeseapp.ui.components.topbar.TopAppBarComponent(
                title = "예약 일정",
                showLeadingIcon = true,
                leadingIcon = Icons.AutoMirrored.Default.ArrowBack,
                showTrailingIcon = false,
                onClickLeadingIcon = onBackClick,
                onClickTrailingIcon = { /* 필요 시 구현 */ }
            )
        },
        content = { paddingValues ->
            // 예약 스케줄 변경 컴포넌트를 스크롤 가능하게 하기 위해 LazyColumn 사용
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
            ) {
                BookingScheduleChangeItemComponent(
                    operationHours = operationHours,
                    calendarState = calendarState,
                    modifier = Modifier
                        .weight(1f) // 남은 공간을 차지
                        .fillMaxWidth()
                )
            }
        },
        bottomBar = {
            BookingBottomActionButtons(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun BookingScheduleChangeScreenPreview() {
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
            times = listOf("10:00", "11:00","12:00", "14:00", "16:00","17:00","18:00"),
            status = true
        ),
        CalendarTimeResponseItem(
            date = LocalDate.now().plusDays(1).toString(),
            times = listOf("09:00", "13:00", "15:00"),
            status = true
        ),
        // 추가 샘플 데이터...
    )

    ToucheeseAppTheme {
        Surface {
            BookingScheduleChangeScreen(
                operationHours = sampleOperationHours,
                calendarState = sampleCalendarState,
                onBackClick = { /* 뒤로가기 클릭 이벤트 */ }
            )
        }
    }
}