package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OrderPayProductListComponent(
    productItems: List<ProductItem>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        productItems.forEach { product ->
            OrderPayProductItemComponent(product = product)
        }
    }
}