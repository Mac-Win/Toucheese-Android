package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.toucheeseapp.data.model.studio_detail.StudioDetailResponse
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.now
import kotlinx.datetime.LocalDate

@Composable
fun StudioInfoComponent(
    studio: StudioDetailResponse,
    modifier: Modifier = Modifier
) {

    var isExpanded by remember { mutableStateOf(false) } // 펼치기/접기
    val (isOperationHoursExpanded, setOperationHoursExpanded) = remember { mutableStateOf(false) }
    // 오늘 날짜
    val today = getCurrentDayOfWeekInKorean()
    Column(
        modifier = modifier
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
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = studio.name,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 스튜디오 설명 - 펼치기/접기 기능 추가
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isExpanded) studio.description else studio.description.take(50) + "...",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "접기" else "펼치기"
                    )
                }
            }
        }

        // 하얀색 배경 영역
        Column(
            modifier = Modifier
                .fillMaxWidth() // 화면 너비 꽉 채우기
                .background(Color.White) // 하얀 배경
                .padding(16.dp) // 내부 여백 설정
        ) {
            // 평점과 하트 아이콘
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Icon",
                    tint = Color.Red,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "평점: ${studio.rating} (${studio.reviewCount} 리뷰)",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            // 주소
            Row {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location Icon",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "주소: ${studio.address}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            // 운영시간
            Column(
                modifier = Modifier
                    .wrapContentWidth(Alignment.End)
                    .clickable {
                        // 확장 클릭 반대로
                        setOperationHoursExpanded(!isOperationHoursExpanded)
                    }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Operation Hours",
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "운영시간:",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Icon(
                        imageVector = if (isOperationHoursExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isOperationHoursExpanded) "운영시간 열림" else "운영시간 접힘",
                    )
                }
                // 운영 시간
                if (isOperationHoursExpanded) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        studio.operatingHours.forEach { operatingHour ->
                            // 요일
                            if (operatingHour.openTime == "휴무") { // 휴무일
                                Text(
                                    text = "${operatingHour.dayOfWeek} ---- 휴무일 ----",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Red,
                                    fontWeight = if (operatingHour.dayOfWeek == today) FontWeight.Bold else FontWeight.Medium
                                )
                            } else {

                                Text(
                                    text = "${operatingHour.dayOfWeek} ${operatingHour.openTime} ~ ${operatingHour.closeTime}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (operatingHour.dayOfWeek == today) FontWeight.Bold else FontWeight.Medium,
                                    color = if (operatingHour.dayOfWeek == today) Color.Blue else Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// 오늘 요일 구하기
fun getCurrentDayOfWeekInKorean(): String {
    val today = LocalDate.now()
    val koreanDays = mapOf(
        "MONDAY" to "월",
        "TUESDAY" to "화",
        "WEDNESDAY" to "수",
        "THURSDAY" to "목",
        "FRIDAY" to "금",
        "SATURDAY" to "토",
        "SUNDAY" to "일"
    )
    return koreanDays[today.dayOfWeek.name] ?: "Unknown Day"
}