package com.toucheese.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.toucheese.app.R
import com.toucheese.app.data.model.home.concept_studio.Studio

// 추후 parameter에 studio: Studio 추가
@Composable
fun StudioListItemComponent(
    studio: Studio,
    modifier: Modifier = Modifier,
    ) {
    val (isMarked, setMarkState) = remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
    ) {
        // Item Title
        StudioListItemTitleComponent(
            studioName = studio.name,
            studioProfileImageUrl = studio.profileImage,
            studioRating = studio.rating,
            price = studio.price,
            isMarked,
            modifier = Modifier.fillMaxWidth(),
            onMarkChanged = { setMarkState(!isMarked) }
        )
        // Item Carousel
        StudioListItemCarouselComponent(
            imageUrlList = studio.images
        )

    }
}

@Composable
private fun StudioListItemTitleComponent(
    studioName: String,
    studioProfileImageUrl: String,
    studioRating: Double, // 별점
    price: Int, // 가격
    isMarked: Boolean,
    modifier: Modifier = Modifier,
    onMarkChanged: () -> Unit,
) {
    // 윗 부분
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // profile Image
        AsyncImage(
            model = studioProfileImageUrl,
            contentDescription = "스튜디오 프로필 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .padding(8.dp)
                .clip(
                    RoundedCornerShape(50.dp)
                )
        )
        Spacer(modifier = Modifier.width(4.dp))
        // 상호명
        Text(
            text = titleFilter(studioName),
            fontSize = 24.sp,
        )

        Spacer(Modifier.weight(1f))

        // 북마크 버튼
        IconButton(
            onClick = onMarkChanged
        ) {
            Icon(
                imageVector = if (isMarked) ImageVector.vectorResource(R.drawable.bookmarkfull_24px) else ImageVector.vectorResource(
                    R.drawable.bookmark_24px
                ),
                contentDescription = null,
                tint = if (isMarked) Color(0xFFFFCC00) else Color(0xFF000000),
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
            )
        }
    }

    // 별점, 가격,
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
        // 별점
        SuggestionChip(
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFCC00),
                    modifier = Modifier.size(24.dp)
                )
            },
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, Color(0xFFF0F0F0)),
            enabled = false,
            label = {
                Text(
                    text = "$studioRating",
                    style = MaterialTheme.typography.labelSmall
                )
            },
            colors = SuggestionChipDefaults.suggestionChipColors(
                disabledLabelColor = Color.Black,
                disabledContainerColor = Color(0xFFFAFAFA),
            ),
            onClick = {},
        )


        // 가격
        SuggestionChip(
            icon = {
                Icon(
                    imageVector = Icons.Default.Star, // 아이콘 구해서 한 번에 수정
                    contentDescription = null,
                    tint = Color(0xFFFFCC00),
                    modifier = Modifier.size(24.dp)
                )
            },
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, Color(0xFFF0F0F0)),
            enabled = false,
            label = {
                Text(
                    text = "${price / 1000},000원~",
                    style = MaterialTheme.typography.labelSmall
                )
            },
            colors = SuggestionChipDefaults.suggestionChipColors(
                disabledLabelColor = Color.Black,
                disabledContainerColor = Color(0xFFFAFAFA),
            ),
            onClick = {},
        )
    }
}

// Carousel
@Composable
private fun StudioListItemCarouselComponent(
    imageUrlList: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(imageUrlList) {
            CarouselItem(imageUrl = it)
        }


    }
}

// Carousel Item
@Composable
private fun CarouselItem(imageUrl: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier.width(92.dp).height(120.dp)
        )
    }
}

// 제목을 필터링한다
private fun titleFilter(title: String): String {
    return title
        .replace(oldValue = "스튜디오", newValue = "")
        .replace(oldValue = "STUDIO", newValue = "")
        .trim() // 공백 제거
        .substringBefore(" ") // 공백 뒤로 날림
        .substringBefore("(") // 열린 소괄호 뒤로 날림
}

@Preview
@Composable
private fun StudioListItemPreview() {
//    StudioListItemCarouselComponent()
//    CarouselItem()
}