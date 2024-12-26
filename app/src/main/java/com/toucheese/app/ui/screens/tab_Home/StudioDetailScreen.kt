package com.toucheese.app.ui.screens.tab_Home

import ReviewListComponent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.ui.components.BottomActionButtons
import com.toucheese.app.ui.components.ImageSliderComponent
import com.toucheese.app.ui.components.ProductList
import com.toucheese.app.ui.components.ShareBottomSheetComponent
import com.toucheese.app.ui.components.StudioInfoComponent
import com.toucheese.app.ui.components.StudioTopAppBarComponent
import com.toucheese.app.ui.components.TabBarComponent
import com.toucheese.app.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StudioDetailScreen(
    selectedTab: Int,
    studioId: Int,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    onShare: () -> Unit,
    onBookmark: (Boolean) -> Unit,
    onReviewClick: (Int) -> Unit,
    onProductClicked: (Int) -> Unit,
    onSelectedTabChanged: (Int) -> Unit,
    ) {
    val studio by viewModel.studioDetail.collectAsState()
    val isBookmarked by viewModel.isBookmarked.collectAsState()
    var expandedNotice by remember { mutableStateOf(false) }
    val studioReviews by viewModel.studioReviews.collectAsState()
    var isShareSheetVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val pageLink = "http://yourwebsite.com/studio/$studioId"

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(studioId) {
        viewModel.loadStudioDetail(studioId)
        viewModel.loadStudioReviewList(studioId)
    }

    if (studio != null) {
        Scaffold(
            // 상단 앱바 설정
            topBar = {
                StudioTopAppBarComponent(
                    isBookmarked = isBookmarked,
                    onNavigateBack = navigateBack,
                    onShare = { isShareSheetVisible = true
                              scope.launch { sheetState.show() }},
                    onBookmarkToggle = {
                        viewModel.toggleBookmark()
                        onBookmark(isBookmarked)
                    },
                    showActions = true,
                    showProfile = false
                )
            },
            // 하단 예약 버튼 설정
            bottomBar = {
                if (selectedTab == 0) {
                    BottomActionButtons(modifier = Modifier)
                }
            },
            modifier = Modifier.safeDrawingPadding()
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                // 이미지 슬라이더
                item {
                    ImageSliderComponent(
                        images = studio!!.facilityImageUrls,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }

                // 스튜디오 정보
                item {
                    StudioInfoComponent(
                        studio = studio!!,
                        modifier = Modifier
                            .fillMaxWidth() // 화면 너비를 채움
                            .background(Color(0xFFFFF2CC)) // 노란 배경
                            .clip(RoundedCornerShape(8.dp)) // 모서리 둥글게
                   )
                }


                // 탭 컴포넌트
                item {
                    TabBarComponent(
                        selectedTabIndex = selectedTab,
                        onTabSelected = onSelectedTabChanged,
                        tabTitles = listOf("상품", "리뷰")
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

        if (isShareSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    isShareSheetVisible = false
                    scope.launch { sheetState.show() }
                },
                sheetState = sheetState
            ) {
                ShareBottomSheetComponent(
                    modifier = Modifier.fillMaxWidth(),
                    onDismiss = {
                        isShareSheetVisible = false
                        scope.launch { sheetState.hide() }
                    },
                    context = context,
                    pageLink = pageLink
                )
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