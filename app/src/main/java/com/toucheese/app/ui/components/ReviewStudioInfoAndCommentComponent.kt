package com.toucheese.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.toucheese.app.data.model.home.concept_studio.Studio

@Composable
fun ReviewStudioAndCommentComponent(
    studio: Studio?, // 스튜디오 데이터
    comments: List<String>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDE6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // 카드 내부 패딩
        ) {
            // 스튜디오 이름
            Text(
                text = studio?.name ?: "스튜디오 이름",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 리뷰 내용 (listOf 사용)
            dummyReviewContent.forEach { reviewText ->
                Text(
                    text = reviewText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // 담당자 및 작성일
            Text(
                text = "터치즈 담당자 / 작성일 : 24.12.30",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

// 더미 데이터
val dummyReviewContent = listOf(
    """
        안녕하세요. 000님.
        정성스러운 리뷰 남겨주셔서 감사합니다!
        앞으로도 더 발전하는 00스튜디오가 되도록 하겠습니다. 감사합니다!
    """.trimIndent()
)