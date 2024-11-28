package com.example.toucheeseapp.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.toucheeseapp.data.network.ToucheeseServer
import com.example.toucheeseapp.ui.screens.HomeScreen
import com.example.toucheeseapp.ui.screens.StudioDetailScreen
import com.example.toucheeseapp.ui.screens.ProductOrderDetailScreen
import com.example.toucheeseapp.ui.screens.StudioListScreen

val TAG = "ToucheeseApp"

@Composable
fun ToucheeseApp(api: ToucheeseServer) {
    val navController = rememberNavController()

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
                onBookmark = { bookmarked, passedConceptId -> Log.d(TAG, "북마크 상태: $bookmarked") }
            )
        }

        // 스튜디오 상품 상세 조회 화면
        composable("ProductOrderDetailScreen"){
            ProductOrderDetailScreen()
        }
    }
}