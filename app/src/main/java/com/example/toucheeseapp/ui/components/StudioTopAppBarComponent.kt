package com.example.toucheeseapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

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
            IconButton(onClick = onShare) {
                Icon(Icons.Default.Share, contentDescription = "공유하기")
            }
            IconButton(onClick = onBookmarkToggle) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "북마크",
                    tint = if (isBookmarked) Color.Yellow else Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}