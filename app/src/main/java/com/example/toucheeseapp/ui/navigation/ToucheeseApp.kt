package com.example.toucheeseapp.ui.navigation

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.toucheeseapp.data.network.ToucheeseServer
import com.example.toucheeseapp.ui.components.ShareBottomSheetComponent
import com.example.toucheeseapp.ui.screens.HomeScreen
import com.example.toucheeseapp.ui.screens.ReviewDetailScreen
import com.example.toucheeseapp.ui.screens.StudioDetailScreen
import com.example.toucheeseapp.ui.screens.StudioListScreen
import kotlinx.coroutines.launch


val TAG = "ToucheeseApp"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToucheeseApp(api: ToucheeseServer) {
    val navController = rememberNavController()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    // 바텀 시트
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {showBottomSheet = false},
            sheetState = sheetState
        ) {
            ShareBottomSheetComponent(
                modifier = Modifier,
                onDismiss = {
                    coroutineScope.launch {
                        sheetState.hide()
                        showBottomSheet = false
                    }
                }
            )
        }
    }



    NavHost(
        navController = navController,
        startDestination = "HomeScreen", // 첫 번째 화면 route 지정
    ){ // Builder 부문

        // 메인 화면
        composable("HomeScreen") {
            HomeScreen(
                onCardClick = { conceptId ->
                    navController.navigate("StudioListScreen/$conceptId")
                },
                onStudioClick = { studioId, address ->
                    val conceptId = 5
                    navController.navigate("StudioDetailScreen/$studioId/$address/$conceptId")
                    Log.d("Navigation", "Navigating to StudioDetailScreen with studioId=$studioId, address=$address, conceptId=$conceptId")
                }
            )
        }
        // 스튜디오 조회 화면
        composable("StudioListScreen/{conceptId}"){ backStackEntry ->
            val conceptId = backStackEntry.arguments?.getString("conceptId")?.toIntOrNull() ?:0
            StudioListScreen(
                conceptId = conceptId,
                onClickLeadingIcon = { navController.navigateUp() },
                onClickTrailingIcon = { Log.d(TAG, "장바구니 화면 이동 클릭")}

            )
        }
        composable("StudioDetailScreen/{studioId}/{address}/{conceptId}") { backStackEntry ->
            val studioId = backStackEntry.arguments?.getString("studioId")?.toIntOrNull() ?: 0
            val address = backStackEntry.arguments?.getString("address") ?: "주소 없음"
            val conceptId = backStackEntry.arguments?.getString("conceptId")?.toIntOrNull() ?: 0

            StudioDetailScreen(
                studioId = studioId,
                address = address,
                conceptId = conceptId,
                viewModel = hiltViewModel(),
                navigateBack = { navController.navigateUp() },
                onShare = { Log.d(TAG, "공유 클릭") },
                onBookmark = { bookmarked, passedConceptId -> Log.d(TAG, "북마크 상태: $bookmarked") },
                onReviewClick = { reviewId ->
                    navController.navigate("ReviewDetailScreen/$reviewId/$studioId/$conceptId")
                }
            )
        }

        // 리뷰 상세 화면
        composable(
            "ReviewDetailScreen/{reviewId}/{studioId}/{conceptId}",
            arguments = listOf(navArgument("reviewId") { type = NavType.IntType },
                navArgument("studioId") { type = NavType.IntType },
                navArgument("conceptId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val reviewId = backStackEntry.arguments?.getInt("reviewId") ?: 0
            val studioId = backStackEntry.arguments?.getInt("studioId") ?: 0
            val conceptId = backStackEntry.arguments?.getInt("conceptId") ?: 0

            ReviewDetailScreen(
                reviewId = reviewId,
                viewModel = hiltViewModel(),
                studioId = studioId,
                conceptId = conceptId,
                navigateBack = { navController.navigateUp() }

            )
        }
    }
}
