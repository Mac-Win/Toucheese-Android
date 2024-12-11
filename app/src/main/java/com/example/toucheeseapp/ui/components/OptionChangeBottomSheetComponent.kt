package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.toucheeseapp.data.model.carts_list.CartListItem
import com.example.toucheeseapp.data.model.carts_list.ReservationTime
import com.example.toucheeseapp.data.model.product_detail.AddOption

@Composable
fun ChangeOptionBottomSheetComponent(
    cartItem: CartListItem,
    productNumOfPeople: Int,
    productNumOfPeoplePrice: Int,
    productOptions: List<AddOption>,
    numOfPeople: Int,
    reviewCount: Int,
    isOverFlow: Boolean,
    isOnlyOne: Boolean,
    selectedOption: Set<Int>,
    onDecreaseClicked: () -> Unit,
    onIncreaseClicked: () -> Unit,
    onReviewButtonClicked: () -> Unit,
    onOptionClicked: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onOptionChangeClick: (CartListItem) -> Unit,
    onClose: () -> Unit,
    onConfirm: () -> Unit,
    selectedOptionChanged: (Int) -> Unit,
    showReviewButton: Boolean = true
) {

    var updatedOptions by remember { mutableStateOf(cartItem.addOptions) } // 변경된 옵션 상태 관리


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 상단 닫기 및 확인 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
            IconButton(onClick = {
                onOptionChangeClick(cartItem.copy(addOptions = updatedOptions))
                onConfirm()
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Confirm"
                )
            }
        }

        // CartItemComponent 표시
        CartItemComponent(
            cartItem = cartItem,
            onDeleteClick = onDeleteClick,
            onOptionChangeClick = {
                val updatedOptions = cartItem.addOptions.toMutableList() // 기존 옵션 가져오기

                // 임시 로직: 첫 번째 옵션을 선택된 상태로 토글
                if (updatedOptions.isNotEmpty()) {
                    val firstOption = updatedOptions[0]
                    if (updatedOptions.contains(firstOption)) {
                        updatedOptions.remove(firstOption) // 옵션 제거
                    } else {
                        updatedOptions.add(firstOption) // 옵션 추가
                    }
                }

                // 변경된 옵션이 반영된 새로운 CartListItem 생성
                val updatedCartItem = cartItem.copy(addOptions = updatedOptions)

                // 상위 콜백에 CartListItem 전달
                onOptionChangeClick(updatedCartItem)
            },
            modifier = Modifier.padding(bottom = 16.dp),
        )

        // ProductOrderOptionComponent 표시
        ProductOrderOptionComponent(
            productNumOfPeople = productNumOfPeople,
            productNumOfPeoplePrice = productNumOfPeoplePrice,
            productOptions = productOptions,
            numOfPeople = numOfPeople,
            reviewCount = reviewCount,
            isOverFlow = isOverFlow,
            isOnlyOne = isOnlyOne,
            selectedOption = selectedOption,
            onDecreaseClicked = onDecreaseClicked,
            onIncreaseClicked = onIncreaseClicked,
            onReviewButtonClicked = onReviewButtonClicked,
            onOptionClicked = onOptionClicked,
            selectedOptionChanged = selectedOptionChanged,
            showReviewButton = showReviewButton
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ChangeOptionBottomSheetPreview() {
    val sampleOptions = listOf(
        AddOption(name = "보정 사진 추가", price = 30000),
        AddOption(name = "원본 전체 받기", price = 10000),
        AddOption(name = "액자 프린팅", price = 15000)
    )

    val sampleCartItem = com.example.toucheeseapp.data.model.carts_list.CartListItem(
        studioName = "공원스튜디오",
        personnel = 1,
        productName = "증명사진",
        reservationDate = "2024-01-10",
        reservationTime = ReservationTime(12, 0,0,0),
        totalPrice = 105000,
        addOptions = sampleOptions,
        studioImageUrl = "",
        productImageUrl = "",
        cartId = 1
    )

    ChangeOptionBottomSheetComponent(
        cartItem = sampleCartItem,
        productNumOfPeople = 1,
        productNumOfPeoplePrice = 75000,
        productOptions = sampleOptions,
        numOfPeople = sampleCartItem.personnel,
        reviewCount = 5,
        isOverFlow = false,
        isOnlyOne = false,
        selectedOption = setOf(0, 1),
        onDecreaseClicked = { /* 감소 로직 */ },
        onIncreaseClicked = { /* 증가 로직 */ },
        onReviewButtonClicked = { /* 리뷰 보기 로직 */ },
        onOptionClicked = { /* 옵션 선택 로직 */ },
        onClose = { /* 닫기 로직 */ },
        onConfirm = { /* 확인 로직 */ },
        selectedOptionChanged = { index -> println("옵션 변경: $index") },
        onOptionChangeClick = {},
        onDeleteClick = {}
    )
}