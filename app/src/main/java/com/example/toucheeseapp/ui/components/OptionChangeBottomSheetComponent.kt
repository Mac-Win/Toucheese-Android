package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.toucheeseapp.data.model.product_detail.AddOption

@Composable
fun ChangeOptionBottomSheetComponent(
    studioName: String,
    studioProfileImageUrl: String,
    productImageUrl: String,
    productName: String,
    reservationPeople: Int,
    reservationDate: String,
    reservationTime: String,
    totalPrice: String,
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
    onDeleteClick: () -> Unit,
    onOptionChangeClick: () -> Unit,
    onClose: () -> Unit,
    onConfirm: () -> Unit,
    selectedOptionChanged: (Int) -> Unit,
) {
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
            IconButton(onClick = onConfirm) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Confirm"
                )
            }
        }

        // CartItemComponent 표시
        CartItemComponent(
            studioName = studioName,
            studioProfileImageUrl = studioProfileImageUrl,
            productImageUrl = productImageUrl,
            productName = productName,
            reservationPeople = reservationPeople,
            reservationDate = reservationDate,
            reservationTime = reservationTime,
            totalPrice = totalPrice,
            onDeleteClick = onDeleteClick,
            onOptionChangeClick = onOptionChangeClick,
            modifier = Modifier.padding(bottom = 16.dp)
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
            selectedOptionChanged = selectedOptionChanged
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
    ChangeOptionBottomSheetComponent(
        studioName = "공원스튜디오",
        studioProfileImageUrl = "https://via.placeholder.com/40",
        productImageUrl = "https://via.placeholder.com/80",
        productName = "증명사진",
        reservationPeople = 1,
        reservationDate = "2024-01-10",
        reservationTime = "12:00",
        totalPrice = "105,000원",
        productNumOfPeople = 1,
        productNumOfPeoplePrice = 75000,
        productOptions = sampleOptions,
        numOfPeople = 1,
        reviewCount = 5,
        isOverFlow = false,
        isOnlyOne = false,
        selectedOption = setOf(),
        onDecreaseClicked = { /* 감소 로직 */ },
        onIncreaseClicked = { /* 증가 로직 */ },
        onReviewButtonClicked = { /* 리뷰 보기 로직 */ },
        onOptionClicked = { /* 옵션 선택 로직 */ },
        onDeleteClick = { /* 삭제 로직 */ },
        onOptionChangeClick = { /* 옵션 변경 로직 */ },
        onClose = { /* 닫기 로직 */ },
        onConfirm = { /* 확인 로직 */ },
        selectedOptionChanged = {}
    )
}