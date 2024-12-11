package com.example.toucheeseapp.ui.screens.tab_Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toucheeseapp.ui.components.CartTopAppBarComponent
import com.example.toucheeseapp.ui.components.ChangeOptionBottomSheetComponent
import com.example.toucheeseapp.data.model.carts_list.CartListItem
import com.example.toucheeseapp.data.model.carts_list.ReservationTime
import com.example.toucheeseapp.ui.components.CartItemComponent

@Composable
fun CartScreen(
    cartItems: List<CartListItem>,
    totalAmount: String,
    onBackClick: () -> Unit,
    onClearCartClick: () -> Unit,
    onDeleteCartItem: (CartListItem) -> Unit,
    onOptionChangeClick: (CartListItem) -> Unit,
    onCheckoutClick: () -> Unit
) {
    // 바텀시트 상태 관리
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<CartListItem?>(null) }



    Scaffold(
        topBar = {
            CartTopAppBarComponent(
                onClickLeadingIcon = onBackClick,
                onClickTrailingIcon = onClearCartClick
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFFFFCF5),
                contentColor = Color.Black
            ) {
                Button(
                    onClick = onCheckoutClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC000))
                ) {
                    Text(
                        text = "예약하기 ($totalAmount)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFFFFCF5)),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (cartItems.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "장바구니가 비어있습니다.",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                        }
                    }
                } else {
                    items(
                        items = cartItems,
                        key = { it.cartId } // 고유 키 설정
                    ) { cartItem ->
                        CartItemComponent(
                            cartItem = cartItem,
                            onDeleteClick = { onDeleteCartItem(cartItem) },
                            onOptionChangeClick = {
                                selectedItem = cartItem
                                isBottomSheetVisible = true
                            }
                        )
                    }
                }
            }
        }
    )

    if (isBottomSheetVisible) {
        selectedItem?.let { item ->
            ChangeOptionBottomSheetComponent(
                cartItem = item,
                productNumOfPeople = 1,
                productNumOfPeoplePrice = 75000,
                productOptions = item.addOptions,
                numOfPeople = item.personnel,
                reviewCount = 5,
                isOverFlow = false,
                isOnlyOne = false,
                selectedOption = setOf(),
                onDecreaseClicked = { /* 감소 로직 */ },
                onIncreaseClicked = { /* 증가 로직 */ },
                onReviewButtonClicked = { /* 리뷰 보기 로직 */ },
                onOptionClicked = { /* 옵션 선택 로직 */ },
                onDeleteClick = {
                    onDeleteCartItem(item)
                    isBottomSheetVisible = false
                },
                onOptionChangeClick = { updatedCartItem: CartListItem ->
                    onOptionChangeClick(updatedCartItem)
                    isBottomSheetVisible = false
                },
                onClose = { isBottomSheetVisible = false },
                onConfirm = { isBottomSheetVisible = false },
                selectedOptionChanged = { /* 옵션 변경 처리 */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    val sampleCartItems = listOf(
        CartListItem(
            studioName = "공원스튜디오",
            personnel = 1,
            productName = "증명사진",
            reservationDate = "2024-12-10",
            reservationTime = ReservationTime(14, 0,0,0),
            totalPrice = 105000,
            addOptions = emptyList(),
            studioImageUrl = "",
            cartId = 1,
            productImageUrl = ""
        ),
        CartListItem(
            studioName = "바다스튜디오",
            personnel = 4,
            productName = "가족사진",
            reservationDate = "2024-12-15",
            reservationTime = ReservationTime(16, 0,0,0),
            totalPrice = 250000,
            addOptions = emptyList(),
            cartId = 1,
            studioImageUrl = "",
            productImageUrl = ""
        )
    )

    CartScreen(
        cartItems = sampleCartItems,
        totalAmount = "₩355,000",
        onBackClick = { /* 뒤로가기 로직 */ },
        onClearCartClick = { /* 장바구니 비우기 로직 */ },
        onDeleteCartItem = { /* 삭제 로직 */ },
        onOptionChangeClick = { /* 옵션 변경 로직 */ },
        onCheckoutClick = { /* 예약하기 로직 */ }
    )
}