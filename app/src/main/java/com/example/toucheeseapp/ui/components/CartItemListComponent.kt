package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.toucheeseapp.data.model.carts_list.CartListItem
import com.example.toucheeseapp.data.model.carts_list.ReservationTime

@Composable
fun CartItemListComponent(
    cartItems: List<CartListItem>,
    onDeleteClick: (Int) -> Unit,
    onOptionChangeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(cartItems) { cartItem ->
            CartItemComponent(
                cartItem = cartItem,
                onDeleteClick = onDeleteClick,
                onOptionChangeClick = onOptionChangeClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemListPreview() {
    val sampleItems = listOf(
        CartListItem(
            studioName = "공원스튜디오",
            personnel = 1, // 예약 인원
            productName = "증명사진",
            reservationDate = "2024-01-10",
            reservationTime = ReservationTime(12, 0,0,0), // ReservationTime 객체
            totalPrice = 105000,
            addOptions = emptyList(), // 추가 옵션 기본값
            cartId = 1,
            productImageUrl = "",
            studioImageUrl = ""
        ),
        CartListItem(
            studioName = "바다스튜디오",
            personnel = 4, // 예약 인원
            productName = "가족사진",
            reservationDate = "2024-01-15",
            reservationTime = ReservationTime(15, 0,0,0), // ReservationTime 객체
            totalPrice = 200000,
            addOptions = emptyList(), // 추가 옵션 기본값
            cartId = 1,
            productImageUrl = "",
            studioImageUrl = ""
        )
    )

    CartItemListComponent(
        cartItems = sampleItems,
        onDeleteClick = { /* 삭제 로직 */ },
        onOptionChangeClick = { /* 옵션 변경 로직 */ }
    )
}