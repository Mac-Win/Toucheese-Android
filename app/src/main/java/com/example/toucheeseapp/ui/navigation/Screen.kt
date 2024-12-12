package com.example.toucheeseapp.ui.navigation

sealed class Screen(val route: String) {
    object Login: Screen("LoginScreen")
    object Home : Screen("HomeScreen")
    object StudioList : Screen("StudioListScreen/{conceptId}")
    object StudioDetail : Screen("StudioDetailScreen/{studioId}/{conceptId}")
    object ReviewDetail : Screen("ReviewDetailScreen/{reviewId}/{studioId}")
    object ProductOrderDetail : Screen("ProductOrderDetailScreen/{productId}/{studioId}/{conceptId}")
    object StudioProductReview: Screen("StudioProductReview/{studioId}/{productId}")
    object OrderPay: Screen("OrderPayScreen")
    object Cart: Screen("Cart")
}