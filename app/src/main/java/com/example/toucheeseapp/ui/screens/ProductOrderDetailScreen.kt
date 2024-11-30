package com.example.toucheeseapp.ui.screens

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.util.Log
import android.util.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.Bitmap
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.request.crossfade
import com.example.toucheeseapp.data.model.product_detail.ProductDetailResponse
import com.example.toucheeseapp.ui.components.AppBarImageComponent
import com.example.toucheeseapp.ui.components.DatePickComponent
import com.example.toucheeseapp.ui.components.ProductOrderOptionComponent
import com.example.toucheeseapp.ui.viewmodel.StudioViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ProductOrderDetailScreen(
    viewModel: StudioViewModel = hiltViewModel(),
    productId: Int,
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit,

) {
    var productDetail by remember { mutableStateOf<ProductDetailResponse?>(null) }

    LaunchedEffect(productId) {
        productDetail = viewModel.loadProductDetail(productId)
    }

    var totalPrice by remember { mutableStateOf(15000) }

    if (productDetail != null){
        Scaffold(
            topBar = {
                AppBarImageComponent(
                    productName = productDetail!!.name, // 상품명
                    productInfo = productDetail!!.description, // 상품 설명
                    productImage= productDetail!!.productImage, // 상품 이미지
                    productImageTargetWidth = 300,
                    productImageTargetHeight = 450,
                    onBackButtonClicked = onBackButtonClicked,
                )
            },
            bottomBar = {
                // 주문 버튼
                BottomAppBar(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    containerColor = Color(0xFFFFFFFF),
                ) {
                    Button(
                        onClick = { /* 주문 클릭 시 동작 */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFFFC000))
                    ) {
                        Text(
                            text = "선택 상품 주문 (₩$totalPrice)",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            },

            ) {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                item {
                    // 가격 & 옵션
                    ProductOrderOptionComponent(
                        productNumOfPeople = productDetail!!.standard, // 기준 인원
                        productNumOfPeoplePrice = productDetail!!.price, // 기준 가격
                        productOptions = productDetail!!.addOptions, // 추가 옵션
                        numOfPeople = 1,
                        onDecreaseClicked = {},
                        onIncreaseClicked = {},
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    // 촬영날짜
                    DatePickComponent(
                        date = "2024-11-28", // 임시 데이터
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onValueChanged = { },
                    )
                }
            }

        }
    } else {
       Surface {
           Box(
               modifier= Modifier.fillMaxSize(),
               contentAlignment = Alignment.Center
           ) {
               Text(
                   "화면을 불러오는 중입니다.\n" +
                       "잠시만 기다려주세요.",
                   fontSize = 24.sp
               )

           }
       }
    }
}



/*
onDatePicked: () -> Unit // 날짜 선택 시
 */