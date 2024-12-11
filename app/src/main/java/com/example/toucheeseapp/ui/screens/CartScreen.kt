package com.example.toucheeseapp.ui.screens

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
import com.example.toucheeseapp.data.model.carts_list.AddOption
import com.example.toucheeseapp.ui.components.CartItemListComponent
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

    // 인원 변경 관련
    var numOfPeople by remember { mutableStateOf(1) }
    var selectedOptions by remember { mutableStateOf(setOf<Int>()) }
    var totalPrice by remember { mutableStateOf(0) }

// selectedItem이 바뀔 때 해당 아이템의 정보를 상태에 반영
    LaunchedEffect(selectedItem) {
        selectedItem?.let { item ->
            numOfPeople = item.personnel
            totalPrice = item.totalPrice
            // addOptions 리스트를 인덱스로 관리한다면, 초기 선택 옵션 세팅 가능
            // 예: 모든 옵션 선택 가정 시 selectedOptions = item.addOptions.indices.toSet()
            // 또는 조건에 맞게 selectedOptions를 초기화
            selectedOptions = setOf()
        }
    }


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
            // 재계산 함수 정의
            fun recalcTotalPrice() {
                val productNumOfPeople = item.personnel
                val productNumOfPeoplePrice = 75000
                val productOptions = item.addOptions

                // 기준 인원당 가격
                val basePricePerPerson = productNumOfPeoplePrice.toFloat() / productNumOfPeople
                // 현재 인원에 따른 기본 가격
                val basePrice = basePricePerPerson * numOfPeople
                // 옵션 가격 합산
                val optionsTotal = selectedOptions.sumOf { productOptions[it].price }
                // 총 가격 계산
                totalPrice = (basePrice + optionsTotal).toInt()
            }

            ChangeOptionBottomSheetComponent(
                cartItem = item,
                productNumOfPeople = item.personnel,
                productNumOfPeoplePrice = 75000,
                productOptions = item.addOptions,
                numOfPeople = numOfPeople,
                reviewCount = 5,
                isOverFlow = numOfPeople > item.personnel,
                isOnlyOne = item.personnel == 1,
                selectedOption = selectedOptions,
                onDecreaseClicked = {
                    if (numOfPeople > 1) { // 기준 인원보다 적어질 수 없다는 등의 조건 처리
                        numOfPeople -= 1
                        recalcTotalPrice()
                    } else {
                        // Toast나 다른 UI 피드백 제공 가능
                    }
                },
                onIncreaseClicked = {
                    numOfPeople += 1
                    recalcTotalPrice()
                },
                onOptionClicked = { optionIndex ->
                    if (selectedOptions.contains(optionIndex)) {
                        selectedOptions = selectedOptions - optionIndex
                    } else {
                        selectedOptions = selectedOptions + optionIndex
                    }
                    recalcTotalPrice()
                },
                onDeleteClick = {
                    onDeleteCartItem(item)
                    isBottomSheetVisible = false
                },
                onOptionChangeClick = { updatedCartItem: CartListItem ->
                    onOptionChangeClick(
                        updatedCartItem.copy(
                        personnel = numOfPeople,
                        addOptions = selectedOptions.map { item.addOptions[it] },
                        totalPrice = totalPrice
                        )
                    )
                    isBottomSheetVisible = false
                },
                onClose = { isBottomSheetVisible = false },
                onConfirm = { isBottomSheetVisible = false },
                selectedOptionChanged = { /* 옵션 변경 처리 */ },
                showReviewButton = false,
                onReviewButtonClicked = {}
            )

            // 아이템을 처음 선택했을 때 초기 계산 한 번 수행
            // (이미 LaunchedEffect에서 totalPrice를 초기화했지만 기준/옵션 계산을 한 번 더 정확히 하려면 아래 호출)
            LaunchedEffect(item) {
                recalcTotalPrice()
            }
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
        ),
        CartListItem(
            studioName = "거지스튜디오",
            personnel = 2,
            productName = "손사진",
            reservationDate = "2024-12-24",
            reservationTime = ReservationTime(11, 0,0,0),
            totalPrice = 12000,
            addOptions = emptyList(),
            cartId = 1,
            studioImageUrl = "",
            productImageUrl = ""
        ),
        CartListItem(
            studioName = "하이디라오",
            personnel = 3,
            productName = "발사진",
            reservationDate = "2024-12-31",
            reservationTime = ReservationTime(10, 0,0,0),
            totalPrice = 20000,
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