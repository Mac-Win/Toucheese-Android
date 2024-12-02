package com.example.toucheeseapp.ui.screens.home_main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toucheeseapp.R
import com.example.toucheeseapp.ui.components.ProfileComponent
import com.example.toucheeseapp.ui.components.ReviewContent
import com.example.toucheeseapp.ui.components.ReviewImageDialog
import com.example.toucheeseapp.ui.components.ReviewPhotoComponent
import com.example.toucheeseapp.ui.components.ReviewStudioAndCommentComponent
import com.example.toucheeseapp.ui.components.ShareBottomSheetComponent
import com.example.toucheeseapp.ui.components.StudioTopAppBarComponent
import com.example.toucheeseapp.ui.components.dummyComments
import com.example.toucheeseapp.ui.viewmodel.StudioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDetailScreen(
    viewModel: StudioViewModel = hiltViewModel(),
    studioId: Int,
    reviewId: Int,
    navigateBack: () -> Unit,
) {

    // Context 가져오기 및 pageLink 가져오기
    val context = LocalContext.current
    val pageLink = "https://yourwebsite.com/current-page-link" // *수정필요*

    // 스튜디오 데이터 및 리뷰 상세 데이터 로드
    val studios by viewModel.studios.collectAsState()
    val specificReview by viewModel.specificReview.collectAsState()
    val studio = studios.firstOrNull { it.id == studioId }

    // 상태 관리
    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedPhotoIndex by remember { mutableStateOf(0) }
    var isShareSheetVisible by remember { mutableStateOf(false) }

    // 데이터 로드
    LaunchedEffect(studioId, reviewId) {
        viewModel.loadStudioSpecificReview(studioId, reviewId)
    }

    if (specificReview != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White), // 전체 배경색
            verticalArrangement = Arrangement.spacedBy(16.dp) // 요소 간 간격
        ) {
            // TopAppBar
            item {
                StudioTopAppBarComponent(
                    isBookmarked = false, // 임의 값
                    onNavigateBack = navigateBack,
                    onShare = { isShareSheetVisible = true },
                    onBookmarkToggle = { /* 북마크 처리 */ }
                )
            }

            // 프로필 섹션
            item {
                ProfileComponent(
                    profileDrawableRes = R.drawable.profileimage, // 드로어블 리소스 ID
                    profileNickname = "정두콩"
                )
            }

            // 리뷰 사진
            item {
                specificReview?.reviewImages?.let { photoUrls ->
                    ReviewPhotoComponent(
                        modifier = Modifier.padding(8.dp),
                        photoUrls = photoUrls,
                        onPhotoClick = { index ->
                            selectedPhotoIndex = index
                            isDialogVisible = true
                        }
                    )
                }
            }

            // 리뷰 내용
            item {
                specificReview?.reviewContent?.let { reviewText ->
                    ReviewContent(reviewText = reviewText)
                }
            }

            // 디바이더
            item {
                Divider(
                    color = Color(0xFFFFC000),
                    thickness = 1.dp
                )
            }

            // 스튜디오 프로필 및 댓글 섹션
            item {
                ReviewStudioAndCommentComponent(
                    studio = studio,
                    comments = dummyComments
                )
            }
        }
    } else {
        // 로딩 상태 표시
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "리뷰 정보를 불러오는 중입니다.")
        }
    }

    // 이미지 다이얼로그 표시
    if (isDialogVisible) {
        specificReview?.reviewImages?.let { photoUrls ->
            ReviewImageDialog(
                photoUrls = photoUrls,
                selectedIndex = selectedPhotoIndex,
                onDismiss = { isDialogVisible = false }
            )
        }
    }

    // 공유 바텀 시트
    if (isShareSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { isShareSheetVisible = false },
            modifier = Modifier.fillMaxHeight(),
        ) {
            ShareBottomSheetComponent(
                modifier = Modifier.fillMaxWidth(),
                context = context,
                pageLink = pageLink,
                onDismiss = { isShareSheetVisible = false }
            )
        }
    }
}