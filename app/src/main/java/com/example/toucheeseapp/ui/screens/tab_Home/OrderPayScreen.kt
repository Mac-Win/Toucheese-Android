package com.example.toucheeseapp.ui.screens.tab_Home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toucheeseapp.data.model.cart_order_pay.OrderPayResponse
import com.example.toucheeseapp.data.model.userInfo.UserInfoResponse
import com.example.toucheeseapp.data.token_manager.TokenManager
import com.example.toucheeseapp.ui.components.*
import com.example.toucheeseapp.ui.viewmodel.StudioViewModel
import kotlinx.coroutines.launch

@Composable
fun OrderPayScreen(
    selectedCartIds: List<Int>, // 선택한 장바구니 아이템의 id 리스트
    viewModel: StudioViewModel = hiltViewModel(),
    tokenManager: TokenManager,
    selectedPaymentMethod: Int = 0,
    onPaymentMethodSelected: (Int) -> Unit,
    onConfirmOrder: () -> Unit,
    onBackClick: () -> Unit
) {
    // 토큰 받아오기
    val token = tokenManager.getAccessToken()
    // 코루틴
    val coroutine = rememberCoroutineScope()
    // 선택한 상품들
    var orderPayResponse by remember { mutableStateOf<OrderPayResponse?>(null) }
    // cartIds List<Int> -> String
    val cartIds = selectedCartIds.joinToString(separator = ",")
    LaunchedEffect(selectedCartIds) {
        Log.d("OrderPayScreen", "cartIds: ${cartIds}")
        // 장바구니 결제 조회
        orderPayResponse = viewModel.loadOrderPayData(token, cartIds)
    }
    // 결제 수단 선택 여부
    val isPaymentMethodSelected = !(selectedPaymentMethod == 0)

    // 최종 가격
    var totalPrice = 0
    orderPayResponse?.cartPaymentList?.forEach { item ->
        totalPrice += item.totalPrice
    }
    // 사용자 정보
    val memberContactInfo = orderPayResponse?.memberContactInfo
    val name = memberContactInfo?.name ?: "testName"
    val email = memberContactInfo?.email ?: "test@email.com"
    val phone = memberContactInfo?.phone ?: "010-XXXX-XXXX"
    // 결제 수단
    val paymentMethods: List<PaymentMethod> = listOf(
            PaymentMethod(1, "신용/체크카드"),
            PaymentMethod(2, "카카오 페이"),
            PaymentMethod(3, "네이버 페이"),
            PaymentMethod(4, "휴대폰 결제")
        )
    Scaffold(
        topBar = {
            OrderPayTopAppBarComponent (onClickLeadingIcon = onBackClick)
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFFFFCF5)
            ) {
                Button(
                    enabled = isPaymentMethodSelected,
                    onClick = {
                        coroutine.launch {
                            // 서버에 데이터를 전송
                            viewModel.saveReservationData(token, cartIds)
                        }
                        // 예약일정 화면으로 이동
                        onConfirmOrder()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color(0xFFECECEC)
                    )
                ) {
                    Text(text = "예약하기 (₩$totalPrice)", fontSize = 16.sp)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item {
                SectionTitle("주문 확인")
            }

            // 사용자 정보
            item {
                OrderPayMyInfoComponent(
                    name = name,
                    phone = phone,
                    email = email
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                    thickness = 1.dp
                )
            }

            item {
                SectionTitle("상품확인")
            }

            item {
                OrderPayProductListComponent(
                    productItems = orderPayResponse?.cartPaymentList ?: emptyList(),
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                    thickness = 1.dp
                )
            }

            item {
                SectionTitle("결제수단")

                if (!isPaymentMethodSelected){
                    // 결제 수단 선택 안 한 경우
                    Text(
                        text = "결제 수단을 선택해주세요",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Red,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }

            items(paymentMethods) { paymentMethod ->
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp), // 버튼 간 간격 추가
                    verticalAlignment = Alignment.CenterVertically // 라디오 버튼과 텍스트 동일 높이 배치
                ) {
                    item {
                        Row(
                            modifier = Modifier.clickable { onPaymentMethodSelected(paymentMethod.paymentId) }, // 전체 Row 클릭 가능
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedPaymentMethod == paymentMethod.paymentId,
                                onClick = { onPaymentMethodSelected(paymentMethod.paymentId) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFFFFC000),
                                    unselectedColor = Color(0xFFFFE085)
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp)) // 버튼과 텍스트 간 간격

                            Text(
                                text = paymentMethod.paymentMethod,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

data class PaymentMethod(
    val paymentId: Int,
    val paymentMethod: String,
)