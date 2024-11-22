package com.example.toucheeseapp.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.toucheeseapp.data.network.ToucheeseServer
import com.example.toucheeseapp.ui.screens.HomeScreen
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
        composable("HomeScreen"){
            HomeScreen(){ conceptId ->
                // 스튜디오 조회 화면으로 이동
                navController.navigate("StudioListScreen/$conceptId")

            }
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
    }
}