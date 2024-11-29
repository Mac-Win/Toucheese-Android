package com.example.toucheeseapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.toucheeseapp.ui.components.dummyPhotoUrls
import com.example.toucheeseapp.ui.components.dummyReviewContent
import com.example.toucheeseapp.ui.viewmodel.StudioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDetailScreen(
    viewModel: StudioViewModel = hiltViewModel(),
    studioId: Int,
    reviewId: Int,
    navigateBack: () -> Unit,
    ) {

    // 스튜디오 데이터 로드
    val studios by viewModel.studios.collectAsState()
    val studio = studios.firstOrNull { it.id == studioId }

    // 사진 다이얼로그 상태 관리
    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedPhotoIndex by remember { mutableStateOf(0) }

    var isShareSheetVisible by remember { mutableStateOf(false) }

    // 리뷰 상세 데이터 조회
    LaunchedEffect(studioId, reviewId) {
        viewModel.loadStudioSpecificReview(studioId, reviewId)
    }

    if (isShareSheetVisible) {
        // 공유 바텀 시트
        ModalBottomSheet(
            onDismissRequest = { isShareSheetVisible = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            ShareBottomSheetComponent(
                modifier = Modifier
                    .fillMaxWidth(),
                onDismiss = { isShareSheetVisible = false }
            )
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // 전체 배경색
    ) {
        // TopAppBar
        item {
            StudioTopAppBarComponent(
                isBookmarked = false, // 임의 값
                onNavigateBack = { navigateBack() },
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
            ReviewPhotoComponent(
                modifier = Modifier.padding(8.dp),
                photoUrls = dummyPhotoUrls,
                onPhotoClick = { index ->
                    selectedPhotoIndex = index
                    isDialogVisible = true
                }
            )
        }

        // 리뷰 내용
        item {
            ReviewContent(
                reviewText = dummyReviewContent
            )
        }

        // 디바이더
        item {
            Divider(
                color = Color(0xFFFFC000),
                thickness = 1.dp
            )
        }

        // 스튜디오 프로필과 댓글 리스트
        item {
            ReviewStudioAndCommentComponent(
                studio = studio,
                comments = dummyComments
            )
        }
    }

    // 이미지 다이얼로그 표시
    if (isDialogVisible) {
        ReviewImageDialog(
            photoUrls = dummyPhotoUrls,
            selectedIndex = selectedPhotoIndex,
            onDismiss = { isDialogVisible = false }
        )
    }
}