package com.toucheese.app.ui.screens.tab_Home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.data.model.home.carts_list.CartListResponseItem
import com.toucheese.app.data.token_manager.TokenManager
import com.toucheese.app.ui.components.CartItemComponent
import com.toucheese.app.ui.components.SquareRadioButton
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    tokenManager: TokenManager,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onCheckoutClick: (List<Int>) -> Unit,
    onBackClick: () -> Unit,
) {

    val cartItems by viewModel.cartItems.collectAsState() // ViewModel에서 상태 관찰
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<CartListResponseItem?>(null) }
    // BottomSheetModal 상태 관리
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutine = rememberCoroutineScope()
    // 토큰 받아오기
    val token = tokenManager.getAccessToken()
    Log.d("CartScreen", "Current Token: $token")

    LaunchedEffect(Unit) {
        // 토큰에 해당하는 장바구니 목록
        viewModel.loadCartList(token)
        // 바텀시트 처음에 expanded 상태로 설정
        coroutine.launch {
            bottomSheetState.expand()
        }
    }

    // 장바구니 아이템 삭제 로직
    fun onDeleteCartItem(cartItem: CartListResponseItem) {
//        viewModel.deleteCartItem(token, cartItem.cartId)
    }

    // 총 가격 재계산 함수
    fun recalcTotalPrice(personnel: Int, selectedOptionIds: Set<Int>): Int {
        val productPrice = selectedItem?.productPrice ?: 0
        val productStandard = selectedItem?.productStandard ?: 1

        val chargeablePeople = max(personnel, productStandard)
        val pricePerPerson = if (productStandard != 0) productPrice / productStandard else 0
        val peoplePrice = pricePerPerson * chargeablePeople

        val selectedOptionsPrice = selectedItem?.addOptions
            ?.filter { selectedOptionIds.contains(it.id) }
            ?.sumOf { it.price } ?: 0

        return peoplePrice + selectedOptionsPrice
    }

    // `selectedOptionIds` 상태 변수 정의
    var selectedOptionIds by remember { mutableStateOf(setOf<Int>()) }

    // `selectedItem`이 변경될 때마다 `selectedOptionIds` 업데이트
    LaunchedEffect(selectedItem) {
        selectedOptionIds =
            selectedItem?.selectAddOptions?.map { it.selectOptionId }?.toSet() ?: setOf()
        Log.d("CartScreen", "SelectedOptionIds initialized: $selectedOptionIds")
    }

    // 선택한 장바구니 아이템 데이터
    var selectedCartItem by remember { mutableStateOf<Set<Int>>(emptySet()) }
    // 전체 선택 여부
    val isAllItemClicked = selectedCartItem.size == cartItems.size
    // 선택된 아이템 총합 계산
    val cartTotalPrice = cartItems.filter { cartItem ->
        // 불러온 장바구니 데이터 중에서 선택된 Id에 포함되는 데이터만 추출
        selectedCartItem.contains(cartItem.cartId)
    }.sumOf { cartItem ->
        // 정제된 장바구니 데이터 중 총 가격의 총합을 구함
        cartItem.totalPrice
    }
    // 장바구니 선택내역이 있는지 확인
    val isSelectedCartItemsExists = selectedCartItem.isNotEmpty()

    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        topBar = {
            TopAppBarComponent(
                title = "장바구니",
                leadingIcon = Icons.AutoMirrored.Default.ArrowBack,
                showLeadingIcon = true,
                showTrailingIcon = false,
                onClickLeadingIcon = onBackClick,
                onClickTrailingIcon = { /* 필요시 구현 */ }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Button(
                    enabled = isSelectedCartItemsExists,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color(0xFFE0E0E0),
                        contentColor = Color(0xFF1F1F1F),
                        disabledContentColor = Color(0xFFA2A2A2),
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (isSelectedCartItemsExists) MaterialTheme.colorScheme.primary else Color(
                            0xFFD9D9D9
                        )
                    ),
                    onClick = {
                        val cartIds = cartItems.map { it.cartId }
                        onCheckoutClick(cartIds)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    Text(
                        text = "예약하기 (총 ${cartTotalPrice}원)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (cartItems.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "장바구니가 비어있습니다.",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                        }
                    }
                } else {
                    item {
                        // 전체 선택 및 선택 상품 삭제
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            // 선택 버튼
                            SquareRadioButton(
                                selected = selectedCartItem.size == cartItems.size,
                                selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = Color.Transparent,
                                borderColor = if (isAllItemClicked) Color.Black else Color(0xFFBFBFBF),
                                onClick = {
                                    if (isAllItemClicked) {
                                        // 전체 선택을 해제하는 경우
                                        selectedCartItem = emptySet()
                                    } else {
                                        // 전체 선택을 설정하는 경우
                                        selectedCartItem = selectedCartItem.toMutableSet().apply {
                                            clear() // 초기화
                                            cartItems.forEach { cartItem -> add(cartItem.cartId) }
                                        }
                                    }
                                },
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            // 전체 선택 버튼
                            Text(
                                text = "전체 선택",
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            // 선택 상품 삭제
                            SuggestionChip(
                                shape = RoundedCornerShape(8.dp),
                                label = {
                                    Text(
                                        text = "선택 상품 삭제",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                },
                                border = BorderStroke(1.dp, Color(0xFFF0F0F0)),
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = Color(0xFFFAFAFA),
                                    labelColor = Color(0xFF1F1F1F)
                                ),
                                onClick = {
                                    // 선택 상품 제거
                                    viewModel.deleteCartItem(
                                        token = token,
                                        cartIds = selectedCartItem.toList()
                                    )
                                }
                            )
                        }
                    }
                    items(
                        items = cartItems,
                    ) { cartItem ->
                        val cartId = cartItem.cartId
                        val isCardClicked = selectedCartItem.contains(cartId)

                        CartItemComponent(
                            isCardClicked = isCardClicked,
                            studioName = cartItem.studioName,
                            productName = cartItem.productName,
                            productImage = cartItem.productImage,
                            personal = cartItem.personnel,
                            createDate = cartItem.reservationDate,
                            createTime = cartItem.reservationTime,
                            totalPrice = cartItem.totalPrice,
                            onCardClicked = {
                                selectedCartItem = if (isCardClicked) {
                                    selectedCartItem.minus(cartId)
                                } else {
                                    selectedCartItem.plus(cartId)
                                }
                            },
                            onOptionChangeClicked = {},
                        )
                    }
                }
            }
        }
    )

    if (isBottomSheetVisible) {

//        ModalBottomSheet(
//            sheetState = bottomSheetState, // 바텀시트 상태 전달
//            onDismissRequest = {
//                Log.d("CartScreen", "Bottom sheet dismissed")
//                coroutine.launch {
//                    bottomSheetState.hide()
//                }
//                Log.d("CartScreen", "Bottom sheet is visible? : ${bottomSheetState.isVisible}")
//                isBottomSheetVisible = false
//            },
//            modifier = Modifier.wrapContentHeight(),
//            containerColor = Color(0xFFFFFCF5),
//        ) {
//            selectedItem?.let { item ->
//                ChangeOptionBottomSheetComponent(
//                    cartItem = item,
//                    productNumOfPeople = item.productStandard,
//                    productNumOfPeoplePrice = item.productPrice,
//                    productOptions = item.addOptions,
//                    numOfPeople = item.personnel,
//                    reviewCount = 5,
//                    isOverFlow = item.personnel > item.productStandard,
//                    isOnlyOne = item.productStandard == 1,
//                    selectedOption = selectedOptionIds,
//                    onDecreaseClicked = {
//                        if (item.personnel > 1) {
//                            val updatedPersonnel = item.personnel - 1
//                            val changedCartItem =
//                                com.toucheese.app.data.model.home.carts_optionChange.ChangedCartItem(
//                                    personnel = updatedPersonnel,
//                                    addOptions = selectedOptionIds.toList(),
//                                    totalPrice = recalcTotalPrice(
//                                        updatedPersonnel,
//                                        selectedOptionIds
//                                    )
//                                )
//                            Log.d("CartScreen", "ChangedCartItem onDecrease: $changedCartItem")
//                            viewModel.updateCartItem(token, item.cartId, changedCartItem)
//                            isBottomSheetVisible = false
//                        }
//                    },
//                    onIncreaseClicked = {
//                        val updatedPersonnel = item.personnel + 1
//                        val changedCartItem =
//                            com.toucheese.app.data.model.home.carts_optionChange.ChangedCartItem(
//                                personnel = updatedPersonnel,
//                                addOptions = selectedOptionIds.toList(),
//                                totalPrice = recalcTotalPrice(updatedPersonnel, selectedOptionIds)
//                            )
//                        Log.d("CartScreen", "ChangedCartItem onIncrease: $changedCartItem")
//                        viewModel.updateCartItem(token, item.cartId, changedCartItem)
//                        isBottomSheetVisible = false
//                    },
//                    onReviewButtonClicked = {
//                        Log.d("CartScreen", "Review button clicked in bottom sheet")
//                        /* 리뷰 보기 로직 */
//                    },
//                    onOptionClicked = { optionId ->
//                        // 옵션 선택/해제 로직
//                        val updatedOptions =
//                            if (item.selectAddOptions.any { it.selectOptionId == optionId }) {
//                                item.selectAddOptions.filterNot { it.selectOptionId == optionId }
//                            } else {
//                                val selectedOption = item.addOptions.find { it.id == optionId }
//                                if (selectedOption != null) {
//                                    item.selectAddOptions + com.toucheese.app.data.model.home.carts_list.SelectAddOption(
//                                        selectOptionId = selectedOption.id,
//                                        selectOptionName = selectedOption.name,
//                                        selectOptionPrice = selectedOption.price
//                                    )
//                                } else {
//                                    item.selectAddOptions
//                                }
//                            }
//                        val updatedOptionIds = updatedOptions.map { it.selectOptionId }.toSet()
//                        val changedCartItem =
//                            com.toucheese.app.data.model.home.carts_optionChange.ChangedCartItem(
//                                personnel = item.personnel,
//                                addOptions = updatedOptionIds.toList(),
//                                totalPrice = recalcTotalPrice(item.personnel, updatedOptionIds)
//                            )
//                        Log.d("CartScreen", "ChangedCartItem onOptionClicked: $changedCartItem")
//                        viewModel.updateCartItem(token, item.cartId, changedCartItem)
//                    },
//                    onDeleteClick = {
//                        Log.d("CartScreen", "Delete button clicked in bottom sheet")
//                        onDeleteCartItem(it)
//                        isBottomSheetVisible = false
//                    },
//                    onOptionChangeClick = { updatedCartItem: com.toucheese.app.data.model.home.carts_optionChange.ChangedCartItem ->
//                        // 업데이트 할 때 최신 상태 반영
//                        Log.d("CartScreen", "OptionChangeClick: $updatedCartItem")
//                        viewModel.updateCartItem(token, item.cartId, updatedCartItem)
//                        isBottomSheetVisible = false
//                    },
//                    onClose = {
//                        Log.d("CartScreen", "Close button clicked in bottom sheet")
//                        isBottomSheetVisible = false
//                    },
//                    onConfirm = {
//                        // 확인 시 ChangedCartItem 전송
//                        val changedCartItem =
//                            com.toucheese.app.data.model.home.carts_optionChange.ChangedCartItem(
//                                personnel = item.personnel,
//                                addOptions = selectedOptionIds.toList(),
//                                totalPrice = recalcTotalPrice(item.personnel, selectedOptionIds)
//                            )
//                        Log.d("CartScreen", "ChangedCartItem onConfirm: $changedCartItem")
//                        viewModel.updateCartItem(token, item.cartId, changedCartItem)
//                        isBottomSheetVisible = false
//                    },
//                    selectedOptionChanged = { optionId ->
//                        selectedOptionIds = if (selectedOptionIds.contains(optionId)) {
//                            selectedOptionIds - optionId
//                        } else {
//                            selectedOptionIds + optionId
//                        }
//                        Log.d("CartScreen", "SelectedOptionIds after change: $selectedOptionIds")
//                    },
//
//                    )
//            }
//        }
    }
}
