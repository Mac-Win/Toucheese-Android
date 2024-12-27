package com.toucheese.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.toucheese.app.data.model.home.studio_detail.Product

@Composable
fun ProductList(
    products: List<Product>,
    modifier: Modifier = Modifier,
    onProductClicked: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp) // 전체 리스트의 패딩
    ) {
        // 헤더 텍스트 추가
        Text(
            text = "촬영 상품",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .padding(bottom = 8.dp) // 헤더와 상품 목록 사이 간격
        )

        // 상품 목록 표시
        Column {
            products.forEach { product ->
                ProductItem(
                    product = product,
                    modifier = Modifier
                        .fillMaxWidth() // 가로는 전체 크기
                        .wrapContentHeight() // 세로는 콘텐츠 크기에 맞게 설정
                        .clickable {
                            // 상품 클릭 시 상품번호 전달
                            onProductClicked(product.id)
                        }
                        .padding(vertical = 2.dp) // 각 상품 간 세로 간격
                )
            }
        }
    }
}