package com.toucheese.app.ui.screens.tab_Home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.data.model.home.cart_order_pay.OrderPayResponse
import com.toucheese.app.data.token_manager.TokenManager
import com.toucheese.app.ui.components.*
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun OrderPayScreen(
    selectedCartIds: List<Int>, // 선택한 장바구니 아이템의 id 리스트
    viewModel: HomeViewModel = hiltViewModel(),
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
            TopAppBarComponent(
                title = "주문/결제",
                leadingIcon = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                showLeadingIcon = true,
                showTrailingIcon = false,
                onClickLeadingIcon = onBackClick,
                onClickTrailingIcon = { /* 필요시 구현 */ }
            )
        },
        bottomBar = {
            Button(
                enabled = isPaymentMethodSelected,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color(0xFF1F1F1F),
                    disabledContainerColor = Color(0xFFD9D9D9),
                    disabledContentColor = Color(0xFF8C8C8C),
                    ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = {
                    coroutine.launch {
                        // 서버에 데이터를 전송
                        viewModel.saveReservationData(token, cartIds)
                    }
                    // 예약일정 화면으로 이동
                    onConfirmOrder()
                }
            ) {
                Text(
                    text = "결제하기 (총 ${totalPrice / 1000},000원)",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        modifier = Modifier.fillMaxSize().safeDrawingPadding(),
    ) { paddingValues ->

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            // 사용자 정보
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 내 정보
                    Text(
                        text = "내 정보",
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                    // 성함, 연락처, 이메일
                    OrderPayMyInfoComponent(
                        name = name,
                        phone = phone,
                        email = email,
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 32.dp, end = 16.dp)
                    )
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 상품 확인
                    Text(
                        text = "상품 확인",
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                    //
                    OrderPayProductListComponent(
                        productItems = orderPayResponse?.cartPaymentList ?: emptyList(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
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