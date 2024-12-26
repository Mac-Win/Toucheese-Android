package com.toucheese.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.toucheese.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudioTopAppBarComponent(
    isBookmarked: Boolean,
    onNavigateBack: () -> Unit,
    onShare: () -> Unit,
    onBookmarkToggle: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
            }
        },
        actions = {
            // 공유하기 버튼
            IconButton(onClick = onShare) {
                Icon(
                    painter = painterResource(id = R.drawable.share),
                    contentDescription = "공유하기"
                )
            }

            // 북마크 버튼
            IconButton(onClick = onBookmarkToggle) {
                Icon(
                    painter = painterResource(
                        id = if (isBookmarked) R.drawable.bookmarkfull_24px else R.drawable.bookmark_24px
                    ),
                    contentDescription = if (isBookmarked) "북마크 설정됨" else "북마크 해제",
                    tint = if (isBookmarked) Color.Yellow else Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}