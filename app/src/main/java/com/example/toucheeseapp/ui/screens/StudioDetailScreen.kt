package com.example.toucheeseapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.toucheeseapp.ui.components.ImageSliderComponent
import com.example.toucheeseapp.ui.components.NoticeSectionComponent
import com.example.toucheeseapp.ui.components.StudioInfoComponent
import com.example.toucheeseapp.ui.components.StudioTopAppBarComponent
import com.example.toucheeseapp.ui.components.TabBarComponent
import com.example.toucheeseapp.ui.viewmodel.StudioViewModel

@Composable
fun StudioDetailScreen(
    studioId: Int,
    address: String,
    conceptId: Int,
    viewModel: StudioViewModel,
    navigateBack: () -> Unit,
    onShare: () -> Unit,
    onBookmark: (Boolean,Int) -> Unit
) {

    Log.d("StudioDetailScreen", "Received studioId: $studioId, address: $address, conceptId: $conceptId")

    val studios by viewModel.studios.collectAsState()
    val studio = studios.find { it.id == studioId }

    Log.d("StudioDetailScreen", "Loaded studios: $studios")
    Log.d("StudioDetailScreen", "Selected studio: $studio")

    if (studio != null) {
        val isBookmarked by viewModel.isBookmarked.collectAsState()
        var expandedNotice by remember { mutableStateOf(false) }
        var selectedTab by remember { mutableStateOf(0) }

        LaunchedEffect(Unit) {
            Log.d("StudioDetailScreen", "Loading data for conceptId: $conceptId")
            viewModel.loadStudiosByConcept(conceptId)
        }

        Column(modifier = Modifier.fillMaxSize()) {
            // TopAppBar + ImageSlider
            Box {
                ImageSliderComponent(images = studio.images)
                StudioTopAppBarComponent(
                    isBookmarked = isBookmarked,
                    onNavigateBack = navigateBack,
                    onShare = onShare,
                    onBookmarkToggle = {
                        viewModel.toggleBookmark()
                        onBookmark(isBookmarked, conceptId)
                    }
                )
            }

            // Studio Info Section
            StudioInfoComponent(studio = studio, address = address)

            // Tabs
            TabBarComponent(
                selectedTabIndex = selectedTab,
                onTabSelected = { selectedTab = it },
                tabTitles = listOf("가격", "리뷰")
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Notice Section
            NoticeSectionComponent(
                notice = "스튜디오 이용 시 주차장 이용이 불가능합니다. 근처 주차장을 이용해주세요. " +
                         "저희 스튜디오는 사진을 잘 찍어요르단",
                expanded = expandedNotice,
                onToggleExpand = { expandedNotice = !expandedNotice }
            )
        }
    } else {
        // Loading or error state
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "스튜디오 정보를 불러오는 중입니다.")
        }
    }
}