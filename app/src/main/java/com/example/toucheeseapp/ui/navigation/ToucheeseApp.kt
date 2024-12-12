package com.example.toucheeseapp.ui.navigation

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.example.toucheeseapp.data.token_manager.TokenManager
import com.example.toucheeseapp.ui.components.BottomNavigationBarComponent
import com.example.toucheeseapp.ui.components.ShareBottomSheetComponent
import com.example.toucheeseapp.ui.screens.login.LoginScreen
import com.example.toucheeseapp.ui.screens.tab_Home.CartScreen
import com.example.toucheeseapp.ui.screens.tab_Home.HomeScreen
import com.example.toucheeseapp.ui.screens.tab_Home.OrderPayScreen
import com.example.toucheeseapp.ui.screens.tab_Home.ProductOrderDetailScreen
import com.example.toucheeseapp.ui.screens.tab_Home.ReviewDetailScreen
import com.example.toucheeseapp.ui.screens.tab_Home.StudioDetailScreen
import com.example.toucheeseapp.ui.screens.tab_Home.StudioListScreen
import com.example.toucheeseapp.ui.screens.tab_Home.StudioProductReviewScreen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch


val TAG = "ToucheeseApp"

@RequiresApi(Build.VERSION_CODES.O)
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
    // 로그인 상태
    var isLoggedIn by remember { mutableStateOf(false) }
    // 앱 시작 시 자동 로그인 처리
    val tokenManager = TokenManager(context)
    // 첫 화면
    val firstScreen = if (tokenManager.getAccessToken() != null){
        isLoggedIn = true
        Screen.Home.route
//        Screen.OrderPay.route
    } else {
        isLoggedIn = false
        Screen.Login.route
    }
    // 멤버 Id
    var memberId by remember { mutableStateOf(0) }

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
        startDestination = firstScreen, // 첫 번째 화면 route 지정
    ) { // Builder 부문

        // 로그인 화면
        composable(Screen.Login.route) {
            LoginScreen(
                modifier = Modifier,
                onLoginClicked = { memberLoginId: Int, memberName: String, result: Boolean ->
                    Log.d("ToucheeseApp", "result = $result")
                    // 로그인 상태 저장
                    isLoggedIn = result
                    Log.d("ToucheeseApp", "isLoggedIn = $isLoggedIn")
                    // 로그인 성공
                    if (isLoggedIn){
                        // 홈 화면으로 이동
                        navController.navigate(
                            Screen.Home.route
                        )
                        // 멤버 Id 저장
                        memberId = memberLoginId
                        // 환영 메시지
                        Toast.makeText(context, "${memberName}님 반갑습니다.", Toast.LENGTH_LONG).show()
                    }
                }
            )
        }
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
                onClickTrailingIcon = {
                    // 장바구니 화면으로 이동
                    navController.navigate(Screen.Cart.route)
                },
                onStudioItemClicked = { studioId ->
                    // 스튜디오 상세 화면으로 이동
                    navController.navigate(
                        Screen.StudioDetail.route
                            .replace("{studioId}", "$studioId")
                            .replace("{conceptId}", "$conceptId")
                    )
                }
            )
        }

        // 스튜디오 상세 화면
        composable(Screen.StudioDetail.route) { backStackEntry ->
            val studioId = backStackEntry.arguments?.getString("studioId")?.toIntOrNull() ?: 0
            val conceptId = backStackEntry.arguments?.getString("conceptId")?.toIntOrNull() ?: 0

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
                        Screen.ProductOrderDetail.route
                            .replace("{productId}", "$productId")
                            .replace("{studioId}", "$studioId")
                            .replace("{conceptId}", "$conceptId")
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
                navArgument("conceptId") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            val studioId = backStackEntry.arguments?.getInt("studioId") ?: 0
            val conceptId = backStackEntry.arguments?.getInt("conceptId") ?: 0
            ProductOrderDetailScreen(
                tokenManager = tokenManager,
                memberId = memberId,
                studioId = studioId,
                productId = productId,
                onBackButtonClicked = { navController.navigateUp() },
                onReviewButtonClicked = {
                    // 스튜디오별 특정 상품 리뷰로 이동
                    navController.navigate(
                        Screen.StudioProductReview.route
                            .replace("{studioId}", "$studioId")
                            .replace("{productId}", "$productId")
                    )
                },
                onOrderClicked = {
                    // 장바구니 화면으로 이동
                    navController.navigate(Screen.Cart.route){
                        // 백스택 제거
                        popUpTo(Screen.StudioList.route
                            .replace("{conceptId}", "$conceptId")
                        ) { inclusive = true } // 모든 백스택 제거
                    }
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

        // 장바구니 화면
        composable(
            Screen.Cart.route
        ) {
            CartScreen(
                onBackClick = {navController.navigateUp()},
                onClearCartClick = {},
                onCheckoutClick = { cartIds ->
                    val route = Screen.OrderPay.createRoute(cartIds)
                    navController.navigate(route)
                },
                tokenManager = tokenManager,
            )

        }


        // 주문 및 결제 화면
        composable(
            route = Screen.OrderPay.route,
            arguments = listOf(
                navArgument("orderIds") { type = NavType.StringType }
            )
        ){ backStackEntry ->
            // 전달된 데이터 수신 및 역직렬화
            val orderIdsJson = backStackEntry.arguments?.getString("orderIds") ?: "[]"
            val selectedCartIds = Gson().fromJson<List<Int>>(orderIdsJson, object : TypeToken<List<Int>>() {}.type)
            var selectedPaymentMethod by remember { mutableStateOf(0) }
            Log.d("ToucheeseApp", "orderIdsJson: ${orderIdsJson}")
            Log.d("ToucheeseApp", "selectedCartIds: ${orderIdsJson}")

            OrderPayScreen(
                selectedCartIds = selectedCartIds,
                tokenManager = tokenManager,
                selectedPaymentMethod = selectedPaymentMethod,
                onPaymentMethodSelected = { index ->
                    selectedPaymentMethod = index
                },
                onConfirmOrder = {
                    // 예약 일정 탭으로 이동
                    bottomNavSelectedTab = 1
                    navController.navigate("Test"){
                        // 백스택 제거
                        popUpTo(0) { inclusive = true } // 모든 백스택 제거
                    }
                },
                onBackClick = {
                    // 뒤로가기(장바구니 화면으로 이동)
                    navController.navigateUp()
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

                    Button(
                        onClick = {
                            // 토큰 삭제
                            tokenManager.clearAccessToken()
                            Toast.makeText(context, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                            // 로그인 화면으로 이동
                            navController.navigate(Screen.Login.route){
                                bottomNavSelectedTab = 0
                                // 백스택 제거
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                    ) {
                        Text(text = "로그아웃")
                    }
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