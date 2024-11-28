package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.toucheeseapp.data.model.product_studio.Product

@Composable
fun ProductList(products: List<Product>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) { // LazyColumn 대신 Column 사용
        products.forEach { product ->
            ProductItem(product = product)
        }
    }
}