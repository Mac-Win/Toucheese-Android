package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.toucheeseapp.data.model.concept_studio.Studio

@Composable
fun StudioInfoComponent(
    studio: Studio,
    address: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth() // 화면 너비를 채움
            .background(Color(0xFFFFF2CC)) // 노란 배경
            .clip(RoundedCornerShape(8.dp)) // 모서리 둥글게
    ) {
        // 노란 배경 안에 로고와 스튜디오 이름
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // 내부 패딩
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(model = studio.profileImage),
                    contentDescription = "스튜디오 로고",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = studio.name,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "여기 스튜디오는 다양한 스타일의 촬영을 제공합니다. " +
                        "각기 다른 컨셉의 촬영을 즐기세요. " +
                        "편안한 분위기에서 최고의 퀄리티를 경험할 수 있습니다.",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        // 하얀색 배경 영역
        Column(
            modifier = Modifier
                .fillMaxWidth() // 화면 너비 꽉 채우기
                .background(Color.White) // 하얀 배경
                .padding(vertical = 16.dp) // 내부 여백 설정
        ) {
            // 평점과 하트 아이콘
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp) // 좌우 패딩
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Icon",
                    tint = Color.Red,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "평점: ${studio.rating} (20 리뷰)",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 운영시간과 주소
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "운영시간: 10:00 AM - 8:00 PM",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "주소: $address",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}