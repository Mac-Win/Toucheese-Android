package com.toucheese.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.toucheese.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudioTopAppBarComponent(
    onNavigateBack: () -> Unit,
    showProfile: Boolean = false,
    showActions: Boolean = true,
    isBookmarked: Boolean = false,
    onShare: () -> Unit = {},
    onBookmarkToggle: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            if (showProfile) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.filter), // 임의의 기본 프로필 이미지
                        contentDescription = "프로필 이미지",
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                    Text(text = "사용자님의 리뷰", style = MaterialTheme.typography.titleMedium)
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Default.KeyboardArrowLeft, contentDescription = "뒤로가기")
            }
        },
        actions = {
            if (showActions) {
                IconButton(onClick = onShare) {
                    Icon(
                        painter = painterResource(id = R.drawable.share),
                        contentDescription = "공유하기"
                    )
                }
                IconButton(onClick = onBookmarkToggle) {
                    Icon(
                        painter = painterResource(
                            id = if (isBookmarked) R.drawable.bookmarkfull_24px else R.drawable.bookmark_24px
                        ),
                        contentDescription = if (isBookmarked) "북마크 설정됨" else "북마크 해제",
                        tint = if (isBookmarked) Color.Yellow else Color.Black
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}
