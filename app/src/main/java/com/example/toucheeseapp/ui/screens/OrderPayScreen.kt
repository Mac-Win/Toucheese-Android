package com.example.toucheeseapp.ui.screens

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
import com.example.toucheeseapp.ui.components.*

@Composable
fun OrderPayScreen(
    userName: String,
    userPhone: String,
    userEmail: String,
    productItems: List<ProductItem>,
    totalPrice: String,
    paymentMethods: List<String>,
    selectedPaymentMethod: String,
    onPaymentMethodSelected: (String) -> Unit,
    onConfirmOrder: () -> Unit,
    onBackClick: () -> Unit
) {
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

            item {
                OrderPayMyInfoComponent(
                    name = userName,
                    phone = userPhone,
                    email = userEmail
                )
                Divider(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                    thickness = 1.dp
                )
            }

            item {
                SectionTitle("상품확인")
            }

            item {
                OrderPayProductListComponent(
                    productItems = productItems,
                    modifier = Modifier.fillMaxWidth()
                )
                Divider(
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
@Preview(showBackground = true)
@Composable
fun OrderPayScreenPreview() {
    val productItems = listOf(
        ProductItem(
            name = "증명 사진",
            additionalName = "보정 추가",
            price = 105000,
            studioName = "공원스튜디오",
            imageUrl = "https://via.placeholder.com/80",
            people = 1
        ),
        ProductItem(
            name = "증명 사진",
            additionalName = null,
            price = 75000,
            studioName = "강남스튜디오",
            imageUrl = "https://via.placeholder.com/80",
            people = 2
        )
    )
    val paymentMethods = listOf("신용/체크카드", "카카오페이", "네이버페이", "휴대폰 결제")

    var selectedPaymentMethod by remember { mutableStateOf(paymentMethods.first()) }

    OrderPayScreen(
        userName = "강미미",
        userPhone = "010-9593-3561",
        userEmail = "kan9mimi@gmail.com",
        productItems = productItems,
        totalPrice = "180,000",
        paymentMethods = paymentMethods,
        selectedPaymentMethod = selectedPaymentMethod,
        onPaymentMethodSelected = { selectedPaymentMethod = it },
        onConfirmOrder = { /* 예약하기 로직 */ },
        onBackClick = { /* 뒤로가기 로직 */ }
    )
}