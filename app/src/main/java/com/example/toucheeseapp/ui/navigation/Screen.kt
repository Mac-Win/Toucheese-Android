package com.example.toucheeseapp.ui.navigation

import com.google.gson.Gson

sealed class Screen(val route: String) {
    object Login: Screen("LoginScreen")
    object Home : Screen("HomeScreen")
    object StudioList : Screen("StudioListScreen/{conceptId}")
    object StudioDetail : Screen("StudioDetailScreen/{studioId}/{conceptId}")
    object ReviewDetail : Screen("ReviewDetailScreen/{reviewId}/{studioId}")
    object ProductOrderDetail : Screen("ProductOrderDetailScreen/{productId}/{studioId}/{conceptId}")
    object StudioProductReview: Screen("StudioProductReview/{studioId}/{productId}")
    object OrderPay : Screen("OrderPayScreen/{orderIds}") {
        fun createRoute(orderIds: List<Int>): String {
            val json = Gson().toJson(orderIds) // List<Int>를 JSON으로 변환
            return "OrderPayScreen/$json"
        }
    }
    object Cart: Screen("Cart")
    object Qna: Screen("Qna")
    object QnaContent: Screen("QnaContent")
}