package com.toucheese.app.ui.components

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponseItem
import com.toucheese.app.data.token_manager.TokenManager
import com.toucheese.app.ui.components.calendar.CustomDatePickerComponent
import com.toucheese.app.ui.components.dialog.TwoButtonTextDialog
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.BookScheduleViewModel
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingScheduleChangeScreen(
    studioId: Int,
    reservationId: Int,
    tokenManager: TokenManager,
    viewModel: BookScheduleViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    // token
    val token = tokenManager.getAccessToken()
    // context
    val context = LocalContext.current
    // coroutine
    val coroutine = rememberCoroutineScope()
    val calendarState = rememberSelectableCalendarState()
    // 사용자 예약 내역 조회
    val userBookList by viewModel.userBookList.collectAsState()
    // 사용자 예약 내역
    val temp = userBookList.filter { item ->
        item.reservationId == reservationId
    }
    val userBook = if (temp.isNotEmpty()) temp[0] else null
    // 해당 요일
    val date = userBook?.createDate?.let { viewModel.castToLocalDate(it) }
    LaunchedEffect(date) {
        viewModel.loadUserBookList(token)
        viewModel.loadCalendarDateTime(
            studioId = studioId,
            yearMonth = "${date?.year}-${date?.monthValue}",
            date = date.toString()
        )
    }
    val calendarMonthTimeList by viewModel.calendarMonthTimeList.collectAsState()
    val calendarDateTimeList by viewModel.calendarDateTimeList.collectAsState()
    // ---- 캘린더 ----
    // 캘린더 오픈 여부
    val (isCalendarOpen, setCalendarOpen) = remember { mutableStateOf(false) }
    // 선택일자의 운영시간
    val (operatingHours, setOperationHours) = remember { mutableStateOf<List<CalendarTimeResponseItem>>(emptyList()) }
    // 예약 변경 모달 클릭
    val (changeModalState, setChangeModalState) = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = "예약 일정 변경",
                showLeadingIcon = true,
                leadingIcon = Icons.AutoMirrored.Default.ArrowBack,
                showTrailingIcon = false,
                onClickLeadingIcon = onBackClick,
                onClickTrailingIcon = { /* 필요 시 구현 */ }
            )
        },
        bottomBar = {
            BookingBottomActionButtons(
                modifier = Modifier.fillMaxWidth(),
                onCancelClick = {
                    Toast.makeText(context, "예약취소는 관리자에게 문의해주세요", Toast.LENGTH_SHORT).show()
                },
                onChangeClick = {
                    // 예약 변경 확인 모달 띄워줌
                    setChangeModalState(true)
                }
            )
        },
        modifier = Modifier.safeDrawingPadding()
    ) { paddingValues ->
        // 예약 스케줄 변경 컴포넌트를 스크롤 가능하게 하기 위해 LazyColumn 사용
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            item {
                if (temp.isNotEmpty() && calendarDateTimeList.isNotEmpty()){
                    val userBook = temp[0]
                    // 값
                    val uiValues = viewModel.makeValue(state = userBook.status)
                    // 예약상태에 따른 chip text 색상
                    val chipTextColor = uiValues.first
                    // 예약상태에 따른 chip container 색상
                    val chipContainerColor = uiValues.second
                    // 버튼 label 텍스트
                    val buttonLabelText = uiValues.third


                    // 선택된 날짜를 상태로 관리
                    var selectedDate by remember { mutableStateOf(date)  }
                    // 선택된 시간을 상태로 관리
                    var selectedTime by remember { mutableStateOf( userBook.createTime ) }
                    // 예약가능한 시간을 상태로 관리
                    var operationTime by remember { mutableStateOf( calendarDateTimeList ) }

                    // 카드 아이템
                    BookingScheduleChangeItemComponent(
                        studioName = userBook.studioName,
                        studioImage = userBook.studioImage,
                        statusLabel = userBook.status,
                        createDate = userBook.createDate,
                        createTime = userBook.createTime,
                        chipTextColor = chipTextColor,
                        chipContainerColor = chipContainerColor,
                        buttonLabelText = buttonLabelText,
                        calendarState = calendarState,
                        selectedDate = selectedDate ?: LocalDate.now(),
                        selectedTime = selectedTime,
                        operationTimeList = operationTime,
                        modifier = Modifier.fillMaxWidth(),
                        setSelectedTime = { selectedTime = it },
                        setSelectedDate = { selectedDate = it },
                        onCalendarOpenRequest = {
                            setCalendarOpen(true)
                            viewModel.loadCalendarMonthTime(studioId = studioId, yearMonth = calendarState.monthState.currentMonth.toString())
                            setOperationHours(calendarMonthTimeList)
                        }
                    )

                    // 캘린더 모달
                    if (isCalendarOpen){
                        CustomDatePickerComponent(
                            monthDateTimeList = operatingHours,
                            onMonthChanged = { selectedMonth ->
                                // 서버 API 비동기 호출
                                coroutine.launch {
                                    val result = viewModel.loadCalendarMonthTime(
                                        studioId = studioId,
                                        yearMonth = selectedMonth.toString(),
                                    )
                                    Log.d("ProductOrderDetailScreen", "API result: ${result}")
                                    // 그 월에 해당하는 운영시간 로드
                                    setOperationHours(calendarMonthTimeList)
                                }
                            },
                            onDismissRequest = {
                                setCalendarOpen(false)
                                setOperationHours(emptyList())
                            },
                            onConfirmClicked = { reservationDate: LocalDate, reservationTime: String ->
                                // 예약일자 전송
                                selectedDate = reservationDate
                                // 예약 시간 저장
                                selectedTime = reservationTime
                            }
                        )
                    }
                }
            }
        }
    }
}

// String -> LocalDate 변환
@RequiresApi(Build.VERSION_CODES.O)
private fun castToLocalDate(date: String): LocalDate {
    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd") // 시간 형식 정의
    return LocalDate.parse(date, format)
}