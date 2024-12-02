package com.example.toucheeseapp.ui.screens.tab_Home

import android.widget.Toast
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.toucheeseapp.data.model.product_detail.ProductDetailResponse
import com.example.toucheeseapp.ui.components.AppBarImageComponent
import com.example.toucheeseapp.ui.components.DatePickComponent
import com.example.toucheeseapp.ui.components.ProductOrderOptionComponent
import com.example.toucheeseapp.ui.viewmodel.StudioViewModel

@Composable
fun ProductOrderDetailScreen(
    viewModel: StudioViewModel = hiltViewModel(),
    productId: Int,
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit,
    onReviewButtonClicked: () -> Unit,

    ) {
    var productDetail by remember { mutableStateOf<ProductDetailResponse?>(null) }
    LaunchedEffect(productId) {
        productDetail = viewModel.loadProductDetail(productId)
    }
    // 참여 인원
    val (numOfPerson, setPerson) = remember { mutableIntStateOf(productDetail?.standard ?: 1) }

    if (productDetail != null) {
        // 기준 인원이 1인지 여부
        val isOnlyOne: Boolean = productDetail!!.standard == 1
        // 최종 가격
        var totalPrice by remember { mutableIntStateOf(productDetail!!.price) }

        Scaffold(
            topBar = {
                AppBarImageComponent(
                    productName = productDetail!!.name, // 상품명
                    productInfo = productDetail!!.description, // 상품 설명
                    productImage = productDetail!!.productImage, // 상품 이미지
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
                            fontSize = 16.sp,
                        )
                    }
                }
            },

            ) {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                item {
                    // Toast 메시지 호출을 위한 context
                    val context = LocalContext.current
                    // 가격 & 옵션
                    ProductOrderOptionComponent(
                        productNumOfPeople = productDetail!!.standard, // 기준 인원
                        productNumOfPeoplePrice = productDetail!!.price, // 기준 가격
                        productOptions = productDetail!!.addOptions, // 추가 옵션
                        numOfPeople = numOfPerson,
                        reviewCount = productDetail!!.reviewCount, // 리뷰 갯수
                        isOverFlow = numOfPerson > productDetail!!.standard, // 화면에 표시된 인원수가 상품 기준인원보다 높은지 여부
                        isOnlyOne = isOnlyOne, // 기준 인원이 1명인지 여부
                        onDecreaseClicked = {
                            // 기준 인원보다 큰 경우에만 작동
                            if (numOfPerson > productDetail!!.standard) {
                                setPerson(numOfPerson - 1) // 클릭 시 인원 -1
                                totalPrice -= (totalPrice / productDetail!!.standard)// 기준 인원 금액으로 감소
                            } else {
                                // Toast 메시지를 띄워줌
                                Toast.makeText(context, "기준 인원보다 적은 인원을 선택하실 수 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                        },
                        onIncreaseClicked = {
                            setPerson(numOfPerson + 1)
                            totalPrice += (totalPrice / productDetail!!.standard) }, // 클릭 시 인원 +1
                        onReviewButtonClicked = onReviewButtonClicked,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onOptionClicked = { optionPrice ->
                            // 옵션 상품을 금액에 추가 및 제거한다
                            totalPrice += optionPrice
                        }
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
                modifier = Modifier.fillMaxSize(),
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