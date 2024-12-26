package com.toucheese.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import coil3.compose.rememberAsyncImagePainter
import com.toucheese.app.R
import com.toucheese.app.data.model.home.studio_detail.Product

@Composable
fun ProductItem(product: Product, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp), // 전체 아이템의 패딩
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 상품 이미지 (둥글게 클립)
        Image(
            painter = rememberAsyncImagePainter(model = product.productImage),
            contentDescription = "상품 이미지",
            modifier = Modifier
                .size(128.dp) // 이미지 크기 조정 (필요 시 변경 가능)
                .clip(RoundedCornerShape(8.dp)), // 원형으로 클립
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp)) // 이미지와 텍스트 사이 간격

        // 상품 정보와 가격을 포함한 Column
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // 상품명
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyLarge,
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

            Spacer(modifier = Modifier.height(12.dp))

            // 리뷰 수 (수정된 부분)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 4.dp) // 리뷰 텍스트와 위 요소 간 간격 (옵션)
                    .clickable {
                        // 리뷰 클릭 시 동작 추가 (예: 리뷰 목록 화면으로 이동)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.chat),
                    contentDescription = "리뷰 아이콘",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(16.dp) // 아이콘 크기 설정
                )
                Spacer(modifier = Modifier.width(4.dp)) // 아이콘과 텍스트 사이 간격

                Text(
                    text = "리뷰 ${product.reviewCount}개 >",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp)) // 리뷰와 가격 사이 간격

            // 상품 가격 (리뷰 아래에 배치)
            Text(
                text = "${product.price / 1000},000원",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                ), // Bold 설정
                color = Color.Black
            )
        }
    }
}