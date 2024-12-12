package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.toucheeseapp.data.model.carts_list.CartListResponseItem

@Composable
fun CartItemListComponent(
    cartItems: List<CartListResponseItem>,
    onDeleteClick: (CartListResponseItem) -> Unit,
    onOptionChangeClick: (CartListResponseItem) -> Unit,
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

