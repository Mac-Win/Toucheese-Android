package com.example.toucheeseapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.toucheeseapp.ui.components.ProductList
import com.example.toucheeseapp.data.model.product_studio.SampleProducts
import com.example.toucheeseapp.ui.components.ReviewListComponent
import com.example.toucheeseapp.ui.components.dummyReviews
import com.example.toucheeseapp.ui.viewmodel.StudioViewModel

@Composable
fun StudioDetailScreen(
    studioId: Int,
    address: String,
    conceptId: Int,
    viewModel: StudioViewModel,
    navigateBack: () -> Unit,
    onShare: () -> Unit,
    onBookmark: (Boolean, Int) -> Unit
) {
    val studios by viewModel.studios.collectAsState()
    val studio = studios.find { it.id == studioId }

    if (studio != null) {
        val isBookmarked by viewModel.isBookmarked.collectAsState()
        var expandedNotice by remember { mutableStateOf(false) }
        var selectedTab by remember { mutableStateOf(0) }

        LaunchedEffect(Unit) {
            viewModel.loadStudiosByConcept(conceptId)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 이미지 슬라이더 및 상단 바
            item {
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
            }

            // 스튜디오 정보
            item {
                StudioInfoComponent(studio = studio, address = address)
            }

            // 탭 컴포넌트
            item {
                TabBarComponent(
                    selectedTabIndex = selectedTab,
                    onTabSelected = { selectedTab = it },
                    tabTitles = listOf("가격", "리뷰")
                )
            }

            // 공지사항: 항상 마지막에 표시
            item {
                NoticeSectionComponent(
                    notice = "스튜디오 이용 시 주차장 이용이 불가능합니다. 근처 주차장을 이용해주세요. " +
                            "저희 스튜디오는 사진을 잘 찍어요르단",
                    expanded = expandedNotice,
                    onToggleExpand = { expandedNotice = !expandedNotice }
                )
            }

            // 탭 선택에 따른 콘텐츠
            item {
                when (selectedTab) {
                    0 -> ProductList(products = SampleProducts.products)
                    1 -> ReviewListComponent(reviews = dummyReviews )
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "스튜디오 정보를 불러오는 중입니다.")
        }
    }
}