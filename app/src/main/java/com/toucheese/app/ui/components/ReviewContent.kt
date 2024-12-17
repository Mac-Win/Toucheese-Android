package com.toucheese.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ReviewContent(reviewText: String) {
    Text(
        text = reviewText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
        color = Color.Black
    )
}

// 임의 데이터
val dummyReviewContent = "이 스튜디오 정말 좋았습니다! 사진도 잘 나오고 분위기가 너무 마음에 들었어요. " +
        "다음에도 또 이용하고 싶네요. 강력 추천합니다!" + "ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ" +
        "ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ"