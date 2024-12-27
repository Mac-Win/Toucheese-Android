import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.toucheese.app.data.model.home.review_studio.StudioReviewResponseItem
import com.toucheese.app.ui.components.ReviewItemComponent

@Composable
fun ReviewListComponent(
    modifier: Modifier = Modifier,
    reviews: List<StudioReviewResponseItem>,
    onReviewClick: (Int) -> Unit
) {
    if (reviews.isEmpty()) {
        // 리뷰가 없을 때 메시지 표시
        Box(
            modifier = modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "작성된 리뷰가 없습니다.",
                textAlign = TextAlign.Center
            )
        }
    } else {
        val chunkedReviews = reviews.chunked(3)

        Column(
            modifier = modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(4.dp)
        ) {
            chunkedReviews.forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    rowItems.forEach { review ->
                        ReviewItemComponent(
                            review = review,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clickable { onReviewClick(review.id) }
                        )
                    }
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
