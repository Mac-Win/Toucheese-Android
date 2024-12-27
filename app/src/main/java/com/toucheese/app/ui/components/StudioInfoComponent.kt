package com.toucheese.app.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.toucheese.app.R
import com.toucheese.app.data.model.home.studio_detail.OperatingHour
import com.toucheese.app.data.model.home.studio_detail.StudioDetailResponse
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StudioInfoComponent(
    studio: StudioDetailResponse,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) } // 설명 펼치기/접기 상태
    var isOperationHoursExpanded by remember { mutableStateOf(false) }

    val today = getCurrentDayOfWeekInKorean() // 오늘 요일
    val currentDateTime = LocalDateTime.now()
    val currentTime = currentDateTime.toLocalTime()

    // 오늘의 운영 시간 찾기
    val todayOperatingHour = studio.operatingHours.find { it.dayOfWeek == today }

    // 현재 상태 결정
    val currentStatus = when {
        todayOperatingHour == null -> "Unknown"
        todayOperatingHour.openTime == "휴무" -> "휴무"
        else -> {
            val open = parseTime(todayOperatingHour.openTime)
            val close = parseTime(todayOperatingHour.closeTime)
            if (currentTime.isAfter(open) && currentTime.isBefore(close)) {
                "영업중"
            } else {
                "영업종료"
            }
        }
    }

    // 상태에 따른 색상 설정
    val statusColor = when (currentStatus) {
        "영업중" -> Color.Blue
        "휴무", "영업종료" -> Color.Red
        else -> Color.Black
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White) // 전체 배경 흰색
            .padding(16.dp) // 기본 패딩
    ) {
        // 로고와 이름
        Row(
            verticalAlignment = Alignment.CenterVertically, // 수직 중앙 정렬
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = studio.profileImage),
                contentDescription = "스튜디오 로고",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape), // 원형으로 클립
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp)) // 로고와 텍스트 간 간격
            Text(
                text = studio.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold // 굵은 글씨
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 설명 및 펼치기/접기
        if (isExpanded) {
            // 펼쳐졌을 때: 텍스트와 아이콘을 Column으로 배치
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFDE6)) // 배경 노란색
                    .padding(16.dp) // 내부 여백 조정
            ) {
                Text(
                    text = studio.notice,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isExpanded = false } // 텍스트 클릭 시 접기
                )
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(R.drawable.arrow_up_yellow),
                    contentDescription = "접기",
                    modifier = Modifier
                        .size(12.dp)
                        .align(Alignment.End)
                        .clickable { isExpanded = false }
                )
            }
        } else {
            // 접혔을 때: Row로 텍스트와 아이콘을 배치
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFDE6)) // 배경 노란색
                    .padding(vertical = 4.dp, horizontal = 8.dp) // 내부 여백 조정
            ) {
                Text(
                    text = if (studio.notice.length > 50) studio.notice.take(50) + "..." else studio.notice,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1, // 한 줄만 표시
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f)
                        .clickable { isExpanded = true } // 텍스트 클릭 시 펼치기
                )
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(R.drawable.arrow_drop_down),
                    contentDescription = "펼치기",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { isExpanded = true }
                )
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        // 평점과 리뷰 섹션
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // 평점 카드
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.size(width = 60.dp, height = 30.dp) // 크기 조정
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "별 아이콘",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${studio.rating}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp)) // 카드와 리뷰 간 간격

            // 리뷰 갯수
            Text(
                text = "리뷰 ${studio.reviewCount}개 >",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        // 리뷰 클릭 동작 추가
                    }
            )
        }

        Spacer(modifier = Modifier.height(8.dp)) // 간격 줄임

        // 주소 섹션
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.location_on),
                contentDescription = "주소 아이콘",
                modifier = Modifier.size(21.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${studio.address}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp)) // 간격 줄임

        // 운영시간 섹션
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isOperationHoursExpanded = !isOperationHoursExpanded }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                // horizontalArrangement = Arrangement.SpaceBetween, // 제거
                modifier = Modifier.fillMaxWidth()
            ) {
                // R.drawable.watch 아이콘과 현재 상태 텍스트 및 아이콘
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.watch),
                        contentDescription = "운영 시간 아이콘",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = currentStatus,
                        color = statusColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Icon(
                        tint = Color.Blue,
                        painter = painterResource(
                            if (isOperationHoursExpanded) R.drawable.arrow_up_gray else R.drawable.arrow_drop_down
                        ),
                        contentDescription = if (isOperationHoursExpanded) "접기" else "펼치기",
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(12.dp)
                            .clickable { isOperationHoursExpanded = !isOperationHoursExpanded }
                    )
                }
            }

            if (isOperationHoursExpanded) {
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    studio.operatingHours.forEach { operatingHour ->
                        // 휴무일인 경우
                        if (operatingHour.openTime == "휴무") {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "${operatingHour.dayOfWeek} : ",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "휴무",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Red
                                )
                            }
                        } else {
                            // 영업일인 경우
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "${operatingHour.dayOfWeek} : ",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "${operatingHour.openTime} ~ ${operatingHour.closeTime}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
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
@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDayOfWeekInKorean(): String {
    val today = LocalDateTime.now()
    val koreanDays = mapOf(
        "MONDAY" to "월",
        "TUESDAY" to "화",
        "WEDNESDAY" to "수",
        "THURSDAY" to "목",
        "FRIDAY" to "금",
        "SATURDAY" to "토",
        "SUNDAY" to "일"
    )
    return koreanDays[today.dayOfWeek.name] ?: "Unknown"
}

// 현재 영업 중인지 확인하는 함수
@RequiresApi(Build.VERSION_CODES.O)
fun isCurrentlyOpen(
    operatingHour: OperatingHour,
    todayKorean: String,
    currentTime: LocalTime
): Boolean {
    if (operatingHour.dayOfWeek != todayKorean) return false
    if (operatingHour.openTime == "휴무") return false
    val open = parseTime(operatingHour.openTime)
    val close = parseTime(operatingHour.closeTime)
    return currentTime.isAfter(open) && currentTime.isBefore(close)
}

// 문자열 시간을 LocalTime으로 변환
@RequiresApi(Build.VERSION_CODES.O)
fun parseTime(timeStr: String): LocalTime {
    return try {
        LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"))
    } catch (e: Exception) {
        // 예외 처리: 기본 시간 반환 또는 에러 메시지
        LocalTime.MIDNIGHT
    }
}