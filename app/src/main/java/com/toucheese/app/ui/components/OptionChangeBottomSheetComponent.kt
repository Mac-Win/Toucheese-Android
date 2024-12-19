package com.toucheese.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toucheese.app.data.model.home.carts_list.AddOption
import com.toucheese.app.data.model.home.carts_list.CartListResponseItem
import com.toucheese.app.data.model.home.carts_optionChange.ChangedCartItem
import kotlin.math.max

@Composable
fun ChangeOptionBottomSheetComponent(
    cartItem: com.toucheese.app.data.model.home.carts_list.CartListResponseItem,
    productNumOfPeople: Int,
    productNumOfPeoplePrice: Int,
    productOptions: List<com.toucheese.app.data.model.home.carts_list.AddOption>,
    numOfPeople: Int,
    reviewCount: Int,
    isOverFlow: Boolean,
    isOnlyOne: Boolean,
    selectedOption: Set<Int>,
    onDecreaseClicked: () -> Unit,
    onIncreaseClicked: () -> Unit,
    onReviewButtonClicked: () -> Unit,
    onOptionClicked: (Int) -> Unit,
    onDeleteClick: (com.toucheese.app.data.model.home.carts_list.CartListResponseItem) -> Unit,
    onOptionChangeClick: (com.toucheese.app.data.model.home.carts_optionChange.ChangedCartItem) -> Unit,
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

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = Color(0xFFFFFCF5),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
    ) {
        item {
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

            // 구분선
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                thickness = DividerDefaults.Thickness,
                color = Color.Gray
            )

            // 최종 가격 표시
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "최종 가격",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "${totalPrice / 1000},000원",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            }

            // 최종 확인 버튼
            Button(
                onClick = {
                    // 변경 사항 ViewModel에 전달
                    val changedCartItem =
                        com.toucheese.app.data.model.home.carts_optionChange.ChangedCartItem(
                            personnel = numOfPeople,
                            addOptions = selectedOption.toList(),
                            totalPrice = totalPrice
                        )
                    onOptionChangeClick(changedCartItem)
                    onConfirm()
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFF2CC),
                ),

                ) {
                Text(
                    text = "옵션 변경하기",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
