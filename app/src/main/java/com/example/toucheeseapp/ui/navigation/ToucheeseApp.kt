package com.example.toucheeseapp.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.toucheeseapp.data.network.ToucheeseServer
import com.example.toucheeseapp.ui.components.BottomNavigationBarComponent
import com.example.toucheeseapp.ui.components.ShareBottomSheetComponent
import com.example.toucheeseapp.ui.screens.home_main.HomeScreen
import com.example.toucheeseapp.ui.screens.home_main.ReviewDetailScreen
import com.example.toucheeseapp.ui.screens.home_main.StudioDetailScreen
import com.example.toucheeseapp.ui.screens.home_main.ProductOrderDetailScreen
import com.example.toucheeseapp.ui.screens.home_main.StudioListScreen
import com.example.toucheeseapp.ui.screens.home_main.StudioProductReviewScreen
import kotlinx.coroutines.launch


val TAG = "ToucheeseApp"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToucheeseApp(api: ToucheeseServer) {
    val navController = rememberNavController()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val pageLink = "https://yourwebsite.com/current-page-link" // * 수정필요 *
    var bottomNavSelectedTab by remember { mutableStateOf(0) }

    // 바텀 시트
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            ShareBottomSheetComponent(
                modifier = Modifier,
                context = context,
                pageLink = pageLink,
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
        startDestination = Screen.Home.route, // 첫 번째 화면 route 지정
    ) { // Builder 부문

        // 메인 화면
        composable(Screen.Home.route) {
            HomeScreen(
                selectedTab = bottomNavSelectedTab,
                onCardClick = { conceptId ->
                    // 스튜디오 조회 화면으로 이동
                    navController.navigate(
                        Screen.StudioList.route.replace(
                            "{conceptId}",
                            "${conceptId}"
                        )
                    )
                },
                onStudioClick = { studioId ->
                    // 스튜디오 상세 화면으로 이동
                    navController.navigate(
                        Screen.StudioDetail.route.replace(
                            "{studioId}",
                            "$studioId"
                        )
                    )
                },
                onTabSelected = { selectedTab ->
                    // backStack 초기화
                    navController.navigate("Test"){
                        popUpTo(navController.graph.id){
                            inclusive = true
                        }
                    }
                    // 탭 이동
                    bottomNavSelectedTab = selectedTab
                }
            )
        }
        // 스튜디오 조회 화면
        composable(Screen.StudioList.route) { backStackEntry ->
            val conceptId = backStackEntry.arguments?.getString("conceptId")?.toIntOrNull() ?: 0
            StudioListScreen(
                conceptId = conceptId,
                onClickLeadingIcon = { navController.navigateUp() },
                onClickTrailingIcon = { Log.d(TAG, "장바구니 화면 이동 클릭") },
                onStudioItemClicked = { studioId ->
                    // 스튜디오 상세 화면으로 이동
                    navController.navigate(
                        Screen.StudioDetail.route.replace(
                            "{studioId}",
                            "$studioId"
                        )
                    )
                }
            )
        }

        // 스튜디오 상세 화면
        composable(Screen.StudioDetail.route) { backStackEntry ->
            val studioId = backStackEntry.arguments?.getString("studioId")?.toIntOrNull() ?: 0

            StudioDetailScreen(
                studioId = studioId,
                navigateBack = { navController.navigateUp() },
                onShare = { Log.d(TAG, "공유 클릭") },
                onBookmark = { bookmarked -> Log.d(TAG, "북마크 상태: $bookmarked") },
                onReviewClick = { reviewId ->
                    // 리뷰 상세 화면으로 이동
                    navController.navigate(
                        Screen.ReviewDetail.route.replace(
                            "{reviewId}",
                            "$reviewId"
                        ).replace("{studioId}", "$studioId")
                    )
                },
                onProductClicked = { productId ->
                    // 상품 상세 화면으로 이동
                    navController.navigate(
                        Screen.ProductOrderDetail.route.replace(
                            "{productId}",
                            "$productId"
                        ).replace("{studioId}", "$studioId")
                    )
                }

            )
        }

        // 리뷰 상세 화면
        composable(
            Screen.ReviewDetail.route,
            arguments = listOf(
                navArgument("reviewId") { type = NavType.IntType },
                navArgument("studioId") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val reviewId = backStackEntry.arguments?.getInt("reviewId") ?: 0
            val studioId = backStackEntry.arguments?.getInt("studioId") ?: 0

            ReviewDetailScreen(
                reviewId = reviewId,
                studioId = studioId,
                navigateBack = { navController.navigateUp() }
            )
        }

        // 스튜디오 상품 상세 조회 화면
        composable(
            Screen.ProductOrderDetail.route,
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType },
                navArgument("studioId") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            val studioId = backStackEntry.arguments?.getInt("studioId") ?: 0
            ProductOrderDetailScreen(
                productId = productId,
                onBackButtonClicked = { navController.navigateUp() },
                onReviewButtonClicked = {
                    // 스튜디오별 특정 상품 리뷰로 이동
                    navController.navigate(
                        Screen.StudioProductReview.route
                            .replace("{studioId}", "$studioId")
                            .replace("{productId}", "$productId")
                    )
                }
            )
        }

        // 스튜디오별 특정 상품 리뷰 조회 화면
        composable(
            Screen.StudioProductReview.route,
            arguments = listOf(
                navArgument("studioId") { type = NavType.IntType },
                navArgument("productId") { type = NavType.IntType },
            )
        )
        { backStackEntry ->
            val studioId = backStackEntry.arguments?.getInt("studioId") ?: 0
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            StudioProductReviewScreen(
                studioId = studioId,
                productId = productId,
                onBackButtonClicked = { navController.navigateUp() },
                onReviewItemClicked = { reviewId ->
                    // 리뷰 상세 화면으로 이동
                    navController.navigate(Screen.ReviewDetail.route
                        .replace("{studioId}", "$studioId")
                        .replace("{reviewId}", "$reviewId")
                    )
                }
            )
        }

        // BottomNav Test 화면
        composable("Test"){
            Scaffold(
                bottomBar = {
                    BottomNavigationBarComponent(
                        selectedTab = bottomNavSelectedTab,
                        onTabSelected = { selectedTab ->
                            // 탭 이동
                            bottomNavClicked(
                                selectedTab = selectedTab,
                                navController = navController,
                            )
                            bottomNavSelectedTab = selectedTab
                        }
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "바텀 내비게이션 테스트 화면",
                        fontSize = 36.sp
                    )
                }
            }
        }

    }
}

fun bottomNavClicked(selectedTab: Int, navController: NavController) {
    when(selectedTab){
        // 홈화면으로 이동
        0 -> {
            navController.navigate(Screen.Home.route){
                popUpTo(navController.graph.id){
                    inclusive = true
                }
            }

        }
        // Test 화면 이동
        else -> {
            // backStack 초기화
            navController.navigate("Test"){
                popUpTo(navController.graph.id){
                    inclusive = true
                }
            }
        }
    }
}