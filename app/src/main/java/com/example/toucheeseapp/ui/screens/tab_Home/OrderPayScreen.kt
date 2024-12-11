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
import com.example.toucheeseapp.data.model.userInfo.UserInfoResponse
import com.example.toucheeseapp.data.token_manager.TokenManager
import com.example.toucheeseapp.ui.components.*
import com.example.toucheeseapp.ui.viewmodel.StudioViewModel

@Composable
fun OrderPayScreen(
    selectedCartIds: List<Int>, // 선택한 장바구니 아이템의 id 리스트
    viewModel: StudioViewModel = hiltViewModel(),
    tokenManager: TokenManager,
    selectedPaymentMethod: String,
    onPaymentMethodSelected: (String) -> Unit,
    onConfirmOrder: () -> Unit,
    onBackClick: () -> Unit
) {
    // 선택한 상품들
    val products by viewModel.cartItems.collectAsState()
    var userInfo: UserInfoResponse? = null
    LaunchedEffect(selectedCartIds) {
        // 토큰 받아오기
        val token = tokenManager.getAccessToken()
        // 선택한 장바구니 데이터 조회
        viewModel.loadCartList(token)
        // 사용자 정보 조회
        userInfo = viewModel.loadUserData(token)
    }
    Log.d("OrderPayScreen", "products: ${products}")
    Log.d("OrderPayScreen", "userInfo: ${userInfo}")
    // 최종 가격
    var totalPrice = 0
    products.forEach { item ->
        totalPrice += item.totalPrice
    }
    // 사용자 정보
    val name = userInfo?.name ?: "testName"
    val email = userInfo?.email ?: "test@email.com"
    val phone = userInfo?.phone ?: "010-XXXX-XXXX"
    // 결제 수단
    val paymentMethods: List<String> = listOf("신용/체크카드", "카카오 페이", "네이버 페이", "휴대폰 결제")
    Scaffold(
        topBar = {
            OrderPayTopAppBarComponent (onClickLeadingIcon = onBackClick)
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFFFFCF5)
            ) {
                Button(
                    onClick = onConfirmOrder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC000))
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
                    productItems = products,
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                    thickness = 1.dp
                )
            }

            item {
                SectionTitle("결제수단")
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
                            modifier = Modifier.clickable { onPaymentMethodSelected(paymentMethod) }, // 전체 Row 클릭 가능
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedPaymentMethod == paymentMethod,
                                onClick = { onPaymentMethodSelected(paymentMethod) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFFFFC000),
                                    unselectedColor = Color(0xFFFFE085)
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp)) // 버튼과 텍스트 간 간격

                            Text(
                                text = paymentMethod,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}