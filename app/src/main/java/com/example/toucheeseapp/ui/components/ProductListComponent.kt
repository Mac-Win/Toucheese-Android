package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.toucheeseapp.data.model.studio_detail.Product

@Composable
fun ProductList(products: List<Product>, modifier: Modifier = Modifier, onProductClicked: (Int) -> Unit) {
    Column(modifier = modifier) { // LazyColumn 대신 Column 사용
        products.forEach { product ->
            ProductItem(
                product = product,
                modifier = Modifier
                    .fillMaxWidth() // 가로는 전체 크기
                    .wrapContentHeight() // 세로는 콘텐츠 크기에 맞게 설정
                    .padding(16.dp)
                    .clickable {
                        // 상품 클릭 시 상품번호 전달
                        onProductClicked(product.id)
                    }
            )
        }
    }
}