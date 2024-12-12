package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.toucheeseapp.data.model.carts_list.CartListResponseItem

@Composable
fun OrderPayProductListComponent(
    productItems: List<CartListResponseItem>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        productItems.forEach { product ->
            OrderPayProductItemComponent(product = product)
        }
    }
}