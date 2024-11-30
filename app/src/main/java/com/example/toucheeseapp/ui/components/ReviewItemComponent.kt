package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.toucheeseapp.data.model.review_studio.StudioReviewResponseItem

@Composable
fun ReviewItemComponent(review: StudioReviewResponseItem, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier.padding(4.dp)
    ) {
        AsyncImage(
            model = review.firstImage,
            contentDescription = "Review Image",
            modifier = Modifier.fillMaxSize()
        )
    }
}

// 임시 데이터 모델
data class Review(val imageUrl: String, val id: Int)

// 임시 데이터 리스트
val dummyReviews = listOf(
    Review(id = 1, imageUrl = "https://via.placeholder.com/300x200"),
    Review(imageUrl = "https://via.placeholder.com/300x200", id = 2),
    Review(imageUrl = "https://via.placeholder.com/300x200", id = 3),
    Review(imageUrl = "https://via.placeholder.com/300x200", id = 4),
    Review(imageUrl = "https://via.placeholder.com/300x200", id = 5),
    Review(imageUrl = "https://via.placeholder.com/300x200", id = 6),
    Review(imageUrl = "https://via.placeholder.com/300x200", id = 7),
    Review(imageUrl = "https://via.placeholder.com/300x200", id = 8),
    Review(imageUrl = "https://via.placeholder.com/300x200", id = 9),
    Review(imageUrl = "https://via.placeholder.com/300x200", id = 10)
)