package com.example.toucheeseapp.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReviewListComponent(reviews: List<Review>) {
    // 한 줄에 3개의 아이템을 배치
    val chunkedReviews = reviews.chunked(3)

    Column(modifier = Modifier.fillMaxSize().padding(4.dp)) {
        chunkedReviews.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp) // 아이템 간 간격
            ) {
                rowItems.forEach { review ->
                    ReviewItemComponent(
                        review = review,
                        modifier = Modifier.weight(1f).aspectRatio(1f) // 동일한 크기로 배치
                    )
                }
                // 만약 아이템 개수가 3보다 적다면 빈 공간을 채워 균등 배치
                repeat(3 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}