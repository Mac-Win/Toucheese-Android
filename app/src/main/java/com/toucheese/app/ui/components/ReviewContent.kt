package com.toucheese.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReviewContent(
    reviewText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 작성일 표시
        Text(
            text = "작성일 : 2024.12.24",
            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp) // 날짜와 내용 사이 간격 조정
        )

        // 리뷰 내용 표시
        Text(
            text = reviewText,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}
