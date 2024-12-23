package com.toucheese.app.ui.screens.tab_Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.data.model.home.product_detail.ProductDetailResponse
import com.toucheese.app.data.model.home.review_studio.StudioReviewResponseItem
import com.toucheese.app.ui.components.AppBarImageComponent
import com.toucheese.app.ui.components.ReviewListComponent
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.HomeViewModel


@Composable
fun StudioProductReviewScreen(
    viewModel: HomeViewModel = hiltViewModel(),
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
                TopAppBarComponent(
                    title = "",
                    showLeadingIcon = true,
                    onClickLeadingIcon = onBackButtonClicked,
                    leadingIcon = Icons.AutoMirrored.Default.ArrowBack
                )
            }

        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    AppBarImageComponent(
                        productName = productDetail!!.name,
                        productInfo = productDetail!!.description,
                        productImage = productDetail!!.productImage,
                        modifier = Modifier,
                        onReviewButtonClicked = { },
                        reviewCount = productDetail!!.reviewCount,
                        showReviewButton = false
                    )
                }
                item {
                    ReviewListComponent(
                        reviews = productReviewList!!,
                        onReviewClick = onReviewItemClicked
                    )
                }
            }
        }
    } else {
        // 로딩 상태나 에러 처리 등을 여기에 구현
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "로딩 중...",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}
