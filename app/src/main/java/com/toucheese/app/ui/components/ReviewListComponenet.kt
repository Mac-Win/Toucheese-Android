package com.toucheese.app.ui.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.toucheese.app.data.model.home.review_studio.StudioReviewResponseItem

@Composable
fun ReviewListComponent(modifier: Modifier = Modifier, reviews: List<com.toucheese.app.data.model.home.review_studio.StudioReviewResponseItem>, onReviewClick: (Int) -> Unit) {
    // 한 줄에 3개의 아이템을 배치
    val chunkedReviews = reviews.chunked(3)

    Column(modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(4.dp)) {
        chunkedReviews.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp) // 아이템 간 간격
            ) {
                rowItems.forEach { review ->
                    ReviewItemComponent(
                        review = review,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clickable { onReviewClick(review.id) }// 동일한 크기로 배치
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