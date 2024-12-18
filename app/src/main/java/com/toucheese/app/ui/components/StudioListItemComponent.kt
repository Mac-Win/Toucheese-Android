package com.toucheese.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.toucheese.app.R
import com.toucheese.app.data.model.home.concept_studio.Studio

// 추후 parameter에 studio: Studio 추가
@Composable
fun StudioListItemComponent(studio: com.toucheese.app.data.model.home.concept_studio.Studio, isMarked: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(top = 16.dp, start = 16.dp, bottom = 16.dp)
    ) {
        // Item Title
        StudioListItemTitleComponent(
            studioName = studio.name,
            studioProfileImageUrl = studio.profileImage,
            studioRating = studio.rating,
            price = studio.price,
            isMarked,
            modifier = Modifier.fillMaxWidth()
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
    studioRating: Double,
    price: Int,
    isMarked: Boolean,
    modifier: Modifier = Modifier
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
        // 상호명 및 별점
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(4.dp)
        ) {
            // 상호명
            Text(
//                text = studioName,
                text = titleFilter(studioName),
                fontSize = 24.sp,
            )
            // 별점
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp),

                ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFCC00),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "$studioRating", // rating 연결
                    color = Color(0xFFFFCC00),
                )
            }
        }

        Spacer(Modifier.weight(1f))

        // 가격
        Text(
            text = "${price / 1000},000원",
            modifier = Modifier.padding(8.dp)
        )

        // 북마크 버튼
        IconButton(
            onClick = {

            }
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
}

// Carousel
@Composable
private fun StudioListItemCarouselComponent(
    imageUrlList: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier.size(150.dp)
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