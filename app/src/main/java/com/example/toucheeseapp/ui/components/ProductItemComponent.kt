package com.example.toucheeseapp.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.toucheeseapp.data.model.studio_detail.Product

@Composable
fun ProductItem(product: Product, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 상품 이미지
        Image(
            painter = rememberAsyncImagePainter(model = product.productImage),
            contentDescription = "상품 이미지",
            modifier = Modifier
                .size(80.dp) // 고정된 크기 설정
                .padding(end = 16.dp)
                .border(1.dp, Color.Black),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp)) // 이미지와 텍스트 사이 간격

        // 상품 정보
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // 상품명
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1, // 한 줄로 제한
                overflow = TextOverflow.Ellipsis // 텍스트가 넘칠 경우 생략 처리
            )

            // 상품 설명
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f) // 약간 흐린 색상
            )

            // 리뷰 수
            Text(
                text = "리뷰 ${product.reviewCount}개",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.width(16.dp)) // 텍스트와 가격 사이 간격

        // 상품 가격 (오른쪽 하단에 배치)
        Column(
            modifier = Modifier.padding(top = 32.dp), // 위쪽에 여백 추가
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${product.price / 1000},000원",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold), // Bold 설정
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
