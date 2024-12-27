package com.toucheese.app.ui.components

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponseItem
import com.toucheese.app.data.token_manager.TokenManager
import com.toucheese.app.ui.components.calendar.CustomDatePickerComponent
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.BookScheduleViewModel
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
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
    onScheduleChangeClicked: () -> Unit,
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
    val userBook = userBookList.find { bookItem ->
        bookItem.reservationId == reservationId
    }
    // 예약변경 모달 상태
    val (changeModalState, setChangeModalState) = remember { mutableStateOf(false) }
    // 예약변경 캘린더 상태
    val (calendarVisibleState, setCalendarVisibleState) = remember { mutableStateOf(false) }
    // 운영시간
    val (operatingHours, setOperationHours) = remember { mutableStateOf<List<CalendarTimeResponseItem>>(emptyList()) }
    // 운영시간
    // 예약 날짜
    LaunchedEffect(reservationId, studioId) {
        // 사용자 예약 내역 조회
        viewModel.loadUserBookList(token)
    }
    // 선택된 날짜를 상태로 관리
    var selectedDate by remember { mutableStateOf(LocalDate.now())  }
    // 선택된 시간을 상태로 관리
    var selectedTime by remember { mutableStateOf( "" ) }

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
                if (userBook != null) {
                    // 값
                    val uiValues = viewModel.makeValue(state = userBook.status)
                    // 예약상태에 따른 chip text 색상
                    val chipTextColor = uiValues.first
                    // 예약상태에 따른 chip container 색상
                    val chipContainerColor = uiValues.second
                    // 버튼 label 텍스트
                    val buttonLabelText = uiValues.third

                    LaunchedEffect(reservationId, studioId) {
                        val date = castToLocalDate(userBook.createDate)
                        // 캘린더 내역 불러오기
                        Log.d("BookChangeScreen", "date = $date")
                        Log.d("BookChangeScreen",
                            "yearMonth = ${date.year}-${date.monthValue}-${date.dayOfMonth}"
                        )
                        // 해당 월의 예약 가능 시간 데이터 불러오기
                        val result = viewModel.loadCalendarTime(
                            studioId = studioId,
                            yearMonth = calendarState.monthState.currentMonth.toString()
                        )
                        setOperationHours(result)
                    }
                    Log.d("BookChangeScreen", "userBook = ${userBook}")
                    Log.d("BookChangeScreen", "calendar schedule = ${operatingHours}")
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
                        selectedDate = selectedDate,
                        selectedTime = selectedTime,
                        operationTimeList = operatingHours,
                        modifier = Modifier.fillMaxWidth(),
                        setSelectedTime = { selectedTime = it },
                        setSelectedDate = { selectedDate = it },
                        onCalendarOpenRequest = {
                            // 서버에서 해당 월의 데이터 불러옴
                            coroutine.launch {
                                val result = viewModel.loadCalendarTime(
                                    studioId = studioId,
                                    yearMonth = YearMonth.from(LocalDate.now()).toString()
                                )
                                setOperationHours(result)
                            }
                            setCalendarVisibleState(true)
                        }
                    )

                    // 캘린더 모달
                    if (calendarVisibleState) {
                        CustomDatePickerComponent(
                            monthDateTimeList = operatingHours,
                            onMonthChanged = { selectedMonth ->
                                // 서버 API 비동기 호출
                                coroutine.launch {
                                    val result = viewModel.loadCalendarTime(
                                        studioId = studioId,
                                        yearMonth = selectedMonth.toString(),
                                    )
                                    // 그 월에 해당하는 운영시간 로드
                                    setOperationHours(result)
                                }
                            },
                            onDismissRequest = {
                                setCalendarVisibleState(false)
                            },
                            onConfirmClicked = { reservationDate: LocalDate, reservationTime: String ->
                                // 예약일자 전송
                                selectedDate = reservationDate
                                // 예약 시간 저장
                                selectedTime = reservationTime
                                // 데이터 저장
                                calendarState.selectionState.onDateSelected(reservationDate)

                                // 월 데이터 변경
                                val currentMonth = YearMonth.from(reservationDate)
                                Log.d("BookChangeScreen", "캘린더에서 선택한 연월 데이터 : ${currentMonth}")
                                // 선택한 월이 될 때까지 이동
                                while (calendarState.monthState.currentMonth != currentMonth) {
                                    // 현재 달력 데이터가 선택한 날짜보다 이전인 경우
                                    if (calendarState.monthState.currentMonth.isBefore(currentMonth)) {
                                        Log.d("BookChangeScreen", "변경된 캘린더 연월 데이터 +1 : ${calendarState.monthState.currentMonth}")
                                        // 달 + 1
                                        calendarState.monthState.currentMonth = calendarState.monthState.currentMonth.plusMonths(1)
                                    }
                                    // 현재 달력 데이터가 선택한 날짜보다 이후인 경우
                                    else {
                                        Log.d("BookChangeScreen", "변경된 캘린더 연월 데이터 -1 : ${calendarState.monthState.currentMonth}")
                                        // 달 - 1
                                        calendarState.monthState.currentMonth = calendarState.monthState.currentMonth.minusMonths(1)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    // 예약변경 모달
    if (changeModalState) {
        Dialog(
            properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = true,
            ),
            onDismissRequest = { setChangeModalState(false) }
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),

                    ) {
                    // 예약 변경 문구
                    Text(
                        text = "${selectedDate.year}년 ${selectedDate.monthValue}월 ${selectedDate.dayOfMonth}일 ${selectedTime}으로\n예약일정을 변경하시겠습니까?",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 24.dp)
                    )

                    // 버튼
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // 아니오
                        Button(
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF3F3F3),
                                contentColor = Color(0xFF7A7A7A),
                            ),
                            modifier = Modifier.weight(1f),
                            onClick = { setChangeModalState(false) }
                        ) {
                            Text(
                                text = "아니오",
                                textAlign = TextAlign.Center,

                                )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // 예
                        Button(
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color(0xFF1F1F1F),
                            ),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                setChangeModalState(false)
                                // 예약 변경 API 호출
                                viewModel.updateUserBookSchedule(
                                    token = token,
                                    reservationId = reservationId,
                                    createDate = selectedDate.toString(),
                                    createTime = selectedTime
                                )
                                // 예약 변경 알림 Toast
                                Toast.makeText(context, "예약이 변경되었습니다", Toast.LENGTH_SHORT).show()
                                // 화면 이동 -> 뒤로가기
                                onScheduleChangeClicked()
                            }
                        ) {
                            Text(
                                text = "예",
                                textAlign = TextAlign.Center,
                            )
                        }
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