package com.example.toucheeseapp.ui.screens.tab_Home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toucheeseapp.ui.components.BottomActionButtons
import com.example.toucheeseapp.ui.components.ImageSliderComponent
import com.example.toucheeseapp.ui.components.NoticeSectionComponent
import com.example.toucheeseapp.ui.components.ProductList
import com.example.toucheeseapp.ui.components.ReviewListComponent
import com.example.toucheeseapp.ui.components.StudioInfoComponent
import com.example.toucheeseapp.ui.components.StudioTopAppBarComponent
import com.example.toucheeseapp.ui.components.TabBarComponent
import com.example.toucheeseapp.ui.viewmodel.StudioViewModel

@Composable
fun StudioDetailScreen(
    studioId: Int,
    viewModel: StudioViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    onShare: () -> Unit,
    onBookmark: (Boolean) -> Unit,
    onReviewClick: (Int) -> Unit,
    onProductClicked: (Int) -> Unit,

    ) {
    val studio by viewModel.studioDetail.collectAsState()
    val isBookmarked by viewModel.isBookmarked.collectAsState()
    var expandedNotice by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }
    val studioReviews by viewModel.studioReviews.collectAsState()

    LaunchedEffect(studioId) {
        viewModel.loadStudioDetail(studioId)
        viewModel.loadStudioReviewList(studioId)
    }

    if (studio != null) {
        Scaffold(
            topBar = {
                // 이미지 슬라이더 및 상단 바
                Box {
                    ImageSliderComponent(
                        images = studio!!.facilityImageUrls,
                        modifier = Modifier.fillMaxWidth().height(300.dp)
                    )
                    StudioTopAppBarComponent(
                        isBookmarked = false,
                        onNavigateBack = navigateBack,
                        onShare = onShare,
                        onBookmarkToggle = {
                            viewModel.toggleBookmark()
                            onBookmark(isBookmarked)
                        }
                    )
                }
            },

            bottomBar = {
                if (selectedTab == 0){
                    // 예약바
                    BottomActionButtons(modifier = Modifier)
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                // 스튜디오 정보
                item {
                    StudioInfoComponent(studio = studio!!)
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
                        notice = studio!!.notice,
                        expanded = expandedNotice,
                        onToggleExpand = { expandedNotice = !expandedNotice }
                    )
                }

                // 탭 선택에 따른 콘텐츠
                item {
                    when (selectedTab) {
                        0 -> {
                            ProductList(
                                products = studio!!.products,
                                onProductClicked = onProductClicked
                            )
                        }

                        1 -> {
                            Log.d("StudioDetailScreen", "reviews: ${studioReviews}")
                            ReviewListComponent(
                                reviews = studioReviews,
                                onReviewClick = { reviewId ->
                                    onReviewClick(reviewId)
                                }
                            )
                        }
                    }
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