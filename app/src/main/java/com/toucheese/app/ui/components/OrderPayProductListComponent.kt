package com.toucheese.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.toucheese.app.data.model.home.cart_order_pay.CartPayment

@Composable
fun OrderPayProductListComponent(
    productItems: List<com.toucheese.app.data.model.home.cart_order_pay.CartPayment>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        productItems.forEach { product ->
            OrderPayProductItemComponent(product = product)
        }
    }
}