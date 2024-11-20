package com.example.toucheeseapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.toucheeseapp.ui.screens.HomeScreen
import com.example.toucheeseapp.ui.screens.StudioListScreen

@Composable
fun ToucheeseApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "HomeScreen", // 첫 번째 화면 route 지정
    ){ // Builder 부문

        // 메인 화면
        composable("HomeScreen"){
            HomeScreen()
        }
        // 스튜디오 조회 화면
        composable("StudioListScreen"){
            StudioListScreen(navController = navController)
        }
    }
}