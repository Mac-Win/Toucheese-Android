package com.toucheese.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun AppBarImageComponent(
    productName: String, // 상품명
    productInfo: String, // 상품 상세 설명
    productImage: String, // 상품 이미지
    modifier: Modifier = Modifier,
    onReviewButtonClicked: () -> Unit,
    showReviewButton: Boolean = true,
    reviewCount: Int,
) {
    Box(
        modifier = Modifier
            .safeDrawingPadding()
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopStart,
    ) {
        // 내부 Column을 사용하여 이미지, 텍스트, 버튼을 세로로 배치
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // 상품 이미지
            AsyncImage(
                model = productImage,
                contentDescription = "Photo",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(300.dp)
                    .border(BorderStroke(1.dp, Color.Black))
            )

            // 상품명
            Text(
                text = productName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            // 상품 상세설명
            Text(
                text = productInfo,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .width(220.dp)
                    .padding(top = 4.dp)
            )

            // 리뷰 보러가기 버튼
            if (showReviewButton) {
                TextButton(
                    onClick = onReviewButtonClicked,
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = "리뷰 ${reviewCount}개 >",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}