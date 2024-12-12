package com.example.toucheeseapp.ui.screens.tab_Home

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toucheeseapp.data.model.calendar_studio.CalendarTimeResponseItem
import com.example.toucheeseapp.data.model.product_detail.ProductDetailResponse
import com.example.toucheeseapp.data.model.saveCartData.CartData
import com.example.toucheeseapp.data.token_manager.TokenManager
import com.example.toucheeseapp.ui.components.AppBarImageComponent
import com.example.toucheeseapp.ui.components.DatePickComponent
import com.example.toucheeseapp.ui.components.ProductOrderOptionComponent
import com.example.toucheeseapp.ui.components.calendar.CustomDatePickerComponent
import com.example.toucheeseapp.ui.viewmodel.StudioViewModel
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter

val TAG = "ProductOrderDetailScreen"
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductOrderDetailScreen(
    tokenManager: TokenManager,
    memberId: Int,
    studioId: Int,
    viewModel: StudioViewModel = hiltViewModel(),
    productId: Int,
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit,
    onReviewButtonClicked: () -> Unit,
    onOrderClicked: () -> Unit,
    ) {
    val coroutineScope = rememberCoroutineScope()
    var productDetail by remember { mutableStateOf<ProductDetailResponse?>(null) }
    LaunchedEffect(productId) {
        productDetail = viewModel.loadProductDetail(productId)
    }
    // 참여 인원
    val (numOfPerson, setPerson) = remember { mutableIntStateOf(productDetail?.standard ?: 1) }
    val calendarState = rememberSelectableCalendarState()
    // 선택된 옵션들
    var selectedOption by remember { mutableStateOf(setOf<Int>()) } // 선택된 옵션의 Index를 저장
    // 날짜가 선택되었는지를 저장
    val (isDateClicked, setDateClicked) = remember { mutableStateOf(false) }
    // 선택일자
    val (selectedDate, setSelectedDate) = remember { mutableStateOf<LocalDate>(LocalDate.now()) }
    // 선택일자의 운영시간
    val (operatingHours, setOperationHours) = remember { mutableStateOf<List<CalendarTimeResponseItem>>(emptyList()) }
    // 선택 시간
    var selectedTime by remember { mutableStateOf("") }
    // 시간 선택했는지 여부
    val isTimeSelected = selectedTime.isNotEmpty()
    // context
    val context = LocalContext.current

    if (productDetail != null) {
        // 기준 인원이 1인지 여부
        val isOnlyOne: Boolean = productDetail!!.standard == 1
        // 최종 가격
        var totalPrice by remember { mutableIntStateOf(productDetail!!.price) }
        val (showDialog, setDialog) = remember { mutableStateOf(false) }


        Scaffold(
            topBar = {
                AppBarImageComponent(
                    productName = productDetail!!.name, // 상품명
                    productInfo = productDetail!!.description, // 상품 설명
                    productImage = productDetail!!.productImage, // 상품 이미지
                    onBackButtonClicked = onBackButtonClicked,
                )
            },
            bottomBar = {
                // 주문 버튼
                BottomAppBar(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    containerColor = Color(0xFFFFFFFF),
                ) {
                    Button(
                        enabled = isTimeSelected,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            disabledContainerColor = Color.Gray
                        ),
                        onClick = {
                            if (isTimeSelected) {
                                // 예약 정보 데이터로 만든다
                                val cartData = CartData(
                                    productId= productId,
                                    studioId = studioId,
                                    memberId = memberId,
                                    totalPrice = totalPrice,
                                    createDate = selectedDate.toString(),
                                    createTime = selectedTime,
                                    personnel = numOfPerson,
                                    addOptions = selectedOption.toList()
                                )
                                Log.d(TAG, "productReservation: ${cartData}")
                                val token = tokenManager.getAccessToken()
                                Log.d(TAG, "token: ${token}")
                                // 예약 정보를 서버로 전송한다
                                coroutineScope.launch {
                                    // 서버로 데이터 전송
                                    viewModel.saveCartData(token= token, cartData = cartData)
                                    Log.d(TAG, "서버 전송 클릭")
                                }

                                // 장바구니 화면으로 이동한다
                                onOrderClicked()
                            } else {
                                Toast.makeText(context, "시간을 선택해주세요", Toast.LENGTH_LONG).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    ) {
                        Text(
                            text = "선택 상품 주문 (₩$totalPrice)",
                            fontSize = 16.sp,
                        )
                    }
                }
            },

            ) {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                item {
                    // 가격 & 옵션
                    ProductOrderOptionComponent(
                        productNumOfPeople = productDetail!!.standard, // 기준 인원
                        productNumOfPeoplePrice = productDetail!!.price, // 기준 가격
                        productOptions = productDetail!!.addOptions, // 추가 옵션
                        numOfPeople = numOfPerson,
                        reviewCount = productDetail!!.reviewCount, // 리뷰 갯수
                        isOverFlow = numOfPerson > productDetail!!.standard, // 화면에 표시된 인원수가 상품 기준인원보다 높은지 여부
                        isOnlyOne = isOnlyOne, // 기준 인원이 1명인지 여부
                        onDecreaseClicked = {
                            // 기준 인원보다 큰 경우에만 작동
                            if (numOfPerson > productDetail!!.standard) {
                                setPerson(numOfPerson - 1) // 클릭 시 인원 -1
                                totalPrice -= (totalPrice / productDetail!!.standard)// 기준 인원 금액으로 감소
                            } else {
                                // Toast 메시지를 띄워줌
                                Toast.makeText(
                                    context,
                                    "기준 인원보다 적은 인원을 선택하실 수 없습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        onIncreaseClicked = {
                            setPerson(numOfPerson + 1)
                            totalPrice += (totalPrice / productDetail!!.standard)
                        }, // 클릭 시 인원 +1
                        onReviewButtonClicked = onReviewButtonClicked,
                        selectedOption = selectedOption,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onOptionClicked = { optionPrice ->
                            // 옵션 상품을 금액에 추가 및 제거한다
                            totalPrice += optionPrice
                        },
                        selectedOptionChanged = { index ->
                            selectedOption = if (selectedOption.contains(index)) {
                                selectedOption - index
                            } else {
                                selectedOption + index
                            }
                        }
                    )

                    // 촬영날짜
                    DatePickComponent(
                        date = if (isTimeSelected) "${selectedDate.year}년 ${monthToKorea(selectedDate.month)}월 ${selectedDate.dayOfMonth}일 ( ${selectedTime} )" else "예약일자 및 시간 선택",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onDateClicked = {
                            setDialog(true)
                            coroutineScope.launch {
                                val result  = viewModel.loadCalendarTime(
                                    studioId = studioId,
                                    yearMonth = calendarState.monthState.currentMonth.toString()
                                )
                                setOperationHours(result)
                            }
                        },
                    )
                }
            }


            if (showDialog) {
                CustomDatePickerComponent(
                    selectedDate = selectedDate.toString(),
                    operationHours = operatingHours,
                    calendarSate = calendarState,
                    isDateClicked = isDateClicked,
                    onMonthChanged = { selectedMonth ->
                        // 서버 API 비동기 호출
                        coroutineScope.launch {
                            val result = viewModel.loadCalendarTime(
                                studioId = studioId,
                                yearMonth =  selectedMonth.toString(),
                            )
                            Log.d("ProductOrderDetailScreen", "API result: ${result}")
                            // 그 월에 해당하는 운영시간 로드
                            setOperationHours(result)
                        }
                    },

                    onDateClicked = { clickedDate ->
                        if (isDateClicked && selectedDate == clickedDate) { // 같은 날짜를 다시 누른 경우
                            setDateClicked(false)
                            setSelectedDate(LocalDate.now())
                        } else { // 다른 날짜를 누른 경우
                            setDateClicked(true)
                            setSelectedDate(clickedDate)
                        }
                    },
                    onDismissRequest = {
                        setDialog(false)
                        setDateClicked(false)
                        setOperationHours(emptyList())
                    },
                    onTimeClicked = { date: String, time: String ->
                        // 포맷팅
                        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        val parsedDate = LocalDate.parse(date, dateFormatter)

                        // 날짜 설정
                        setSelectedDate(parsedDate)
                        // 시간 설정
                        selectedTime = time
                        Log.d("ProductOrderDetailScreen", "선택 일자 : ${selectedDate}")
                        Log.d("ProductOrderDetailScreen", "선택 시간 : ${selectedTime}")
                    }
                )
            }
        }
    } else {
        Surface {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "화면을 불러오는 중입니다.\n" +
                            "잠시만 기다려주세요.",
                    fontSize = 24.sp
                )

            }
        }
    }
}

// 월을 한글로 표시한다
@RequiresApi(Build.VERSION_CODES.O)
fun monthToKorea(month: Month): Int {
    return when (month) {
        Month.JANUARY -> 1
        Month.FEBRUARY -> 2
        Month.MARCH -> 3
        Month.APRIL -> 4
        Month.MAY -> 5
        Month.JUNE -> 6
        Month.JULY -> 7
        Month.AUGUST -> 8
        Month.SEPTEMBER -> 9
        Month.OCTOBER -> 10
        Month.NOVEMBER -> 11
        Month.DECEMBER -> 12
    }
}