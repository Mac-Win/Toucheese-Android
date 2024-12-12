package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toucheeseapp.data.model.carts_list.AddOption
import com.example.toucheeseapp.data.model.carts_list.CartListResponseItem
import com.example.toucheeseapp.data.model.carts_optionChange.ChangedCartItem
import kotlin.math.max

@Composable
fun ChangeOptionBottomSheetComponent(
    cartItem: CartListResponseItem,
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
    onDeleteClick: (CartListResponseItem) -> Unit,
    onOptionChangeClick: (ChangedCartItem) -> Unit,
    onClose: () -> Unit,
    onConfirm: () -> Unit,
    selectedOptionChanged: (Int) -> Unit,
) {
    // 총 가격 재계산 함수
    fun recalcTotalPrice(personnel: Int, selectedOptionIds: Set<Int>): Int {
        val chargeablePeople = max(personnel, productNumOfPeople)
        val pricePerPerson = if (productNumOfPeople != 0) productNumOfPeoplePrice / productNumOfPeople else 0
        val peoplePrice = pricePerPerson * chargeablePeople

        val selectedOptionsPrice = productOptions
            .filter { selectedOptionIds.contains(it.id) }
            .sumOf { it.price }

        return peoplePrice + selectedOptionsPrice
    }

    // totalPrice를 상태로 관리하여 numOfPeople 또는 selectedOption이 변경될 때 자동으로 재계산
    val totalPrice by remember(numOfPeople, selectedOption) {
        derivedStateOf { recalcTotalPrice(numOfPeople, selectedOption) }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
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
                // 변경 사항 ViewModel에 전달
                val changedCartItem = ChangedCartItem(
                    personnel = numOfPeople,
                    addOptions = selectedOption.toList(),
                    totalPrice = totalPrice
                )
                onOptionChangeClick(changedCartItem)
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
            onOptionChangeClick = { /* 필요시 구현 */ },
            modifier = Modifier.padding(bottom = 16.dp),
            showDeleteIcon = false,
            showOptionChangeButton = false
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
            showReviewButton = false
        )

        // 총 가격 표시
        Text(
            text = "변경된 가격: ₩$totalPrice",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp)
        )
    }
}
