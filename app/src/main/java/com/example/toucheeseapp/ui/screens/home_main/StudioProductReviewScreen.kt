package com.example.toucheeseapp.ui.screens.home_main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toucheeseapp.data.model.product_detail.ProductDetailResponse
import com.example.toucheeseapp.data.model.review_studio.StudioReviewResponseItem
import com.example.toucheeseapp.ui.components.AppBarImageComponent
import com.example.toucheeseapp.ui.components.ReviewListComponent
import com.example.toucheeseapp.ui.viewmodel.StudioViewModel


@Composable
fun StudioProductReviewScreen(
    viewModel: StudioViewModel = hiltViewModel(),
    studioId: Int,
    productId: Int,
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit, // 뒤로가기
    onReviewItemClicked: (Int) -> Unit, // 리뷰 아이템 클릭
) {
    var productReviewList by remember { mutableStateOf<List<StudioReviewResponseItem>?>(null) }
    var productDetail by remember { mutableStateOf<ProductDetailResponse?>(null) }

    LaunchedEffect(studioId, productId) {
        productReviewList = viewModel.loadProductReview(studioId, productId)
        productDetail = viewModel.loadProductDetail(productId)
    }

    if (productDetail != null && productReviewList != null){
        Scaffold(
            topBar = {
                AppBarImageComponent(
                    productName = productDetail!!.name,
                    productInfo = productDetail!!.description,
                    productImage = productDetail!!.productImage,
                    modifier = Modifier,
                    onBackButtonClicked = onBackButtonClicked,
                )
            }

        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                ReviewListComponent(
                    reviews = productReviewList!!,
                    onReviewClick = onReviewItemClicked
                )
            }
        }
    } else {

    }
}
