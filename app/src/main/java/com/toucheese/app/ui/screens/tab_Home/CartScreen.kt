package com.toucheese.app.ui.screens.tab_Home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.data.model.carts_list.CartListResponseItem
import com.toucheese.app.data.model.carts_list.SelectAddOption
import com.toucheese.app.data.model.carts_optionChange.ChangedCartItem
import com.toucheese.app.data.token_manager.TokenManager
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.components.ChangeOptionBottomSheetComponent
import com.toucheese.app.ui.components.CartItemComponent
import com.toucheese.app.ui.viewmodel.StudioViewModel
import kotlinx.coroutines.launch
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    tokenManager: TokenManager,
    viewModel: StudioViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onCheckoutClick: (List<Int>) -> Unit,
    onBackClick: () -> Unit,
) {

    val cartItems by viewModel.cartItems.collectAsState() // ViewModel에서 상태 관찰
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<CartListResponseItem?>(null) }
    // 장바구니 내역이 있는지 확인
    val isCartItemsExists = cartItems.isNotEmpty()
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

    // 총합 계산
    val totalAmount = "₩${cartItems.sumOf { it.totalPrice }}"

    // 장바구니 아이템 삭제 로직
    fun onDeleteCartItem(cartItem: CartListResponseItem) {
        viewModel.deleteCartItem(token, cartItem.cartId)
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
        selectedOptionIds = selectedItem?.selectAddOptions?.map { it.selectOptionId }?.toSet() ?: setOf()
        Log.d("CartScreen", "SelectedOptionIds initialized: $selectedOptionIds")
    }

    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        topBar = {
            TopAppBarComponent(
                title = "장바구니",
                leadingIcon = Icons.AutoMirrored.Default.ArrowBack,
                showLeadingIcon = true,
                showTrailingIcon = false,
                onClickLeadingIcon = onBackClick,
                onClickTrailingIcon = { /* 필요시 구현 */}
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFFFFCF5),
                contentColor = Color.Black
            ) {
                Button(
                    enabled = isCartItemsExists,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color(0xFFECECEC)
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
                        text = "예약하기 ($totalAmount)",
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
                    .padding(paddingValues)
                    .background(Color(0xFFFFFCF5)),
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
                    items(
                        items = cartItems,
                    ) { cartItem ->
                        CartItemComponent(
                            cartItem = cartItem,
                            onDeleteClick = { cartListResponseItem ->
                                viewModel.deleteCartItem(token, cartListResponseItem.cartId)
                            },
                            onOptionChangeClick = {
                                selectedItem = cartItem
                                // 선택된 옵션 ID를 현재 선택된 옵션으로 초기화
                                selectedOptionIds = cartItem.selectAddOptions.map { it.selectOptionId }.toSet()
                                Log.d("CartScreen", "Opening Bottom Sheet with SelectedOptionIds: $selectedOptionIds")
                                isBottomSheetVisible = true
                                coroutine.launch {
                                    if (bottomSheetState.isVisible) {
                                        bottomSheetState.hide()
                                    } else {
                                        bottomSheetState.expand()
                                    }
                                }
                            },
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    )

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            sheetState = bottomSheetState, // 바텀시트 상태 전달
            onDismissRequest = {
                Log.d("CartScreen", "Bottom sheet dismissed")
                coroutine.launch {
                    bottomSheetState.hide()
                }
                Log.d("CartScreen", "Bottom sheet is visible? : ${bottomSheetState.isVisible}")
                isBottomSheetVisible = false
            },
            modifier = Modifier.wrapContentHeight(),
            containerColor = Color(0xFFFFFCF5),
        ) {
            selectedItem?.let { item ->
                ChangeOptionBottomSheetComponent(
                    cartItem = item,
                    productNumOfPeople = item.productStandard,
                    productNumOfPeoplePrice = item.productPrice,
                    productOptions = item.addOptions,
                    numOfPeople = item.personnel,
                    reviewCount = 5,
                    isOverFlow = item.personnel > item.productStandard,
                    isOnlyOne = item.productStandard == 1,
                    selectedOption = selectedOptionIds,
                    onDecreaseClicked = {
                        if (item.personnel > 1) {
                            val updatedPersonnel = item.personnel - 1
                            val changedCartItem = ChangedCartItem(
                                personnel = updatedPersonnel,
                                addOptions = selectedOptionIds.toList(),
                                totalPrice = recalcTotalPrice(updatedPersonnel, selectedOptionIds)
                            )
                            Log.d("CartScreen", "ChangedCartItem onDecrease: $changedCartItem")
                            viewModel.updateCartItem(token, item.cartId, changedCartItem)
                            isBottomSheetVisible = false
                        }
                    },
                    onIncreaseClicked = {
                        val updatedPersonnel = item.personnel + 1
                        val changedCartItem = ChangedCartItem(
                            personnel = updatedPersonnel,
                            addOptions = selectedOptionIds.toList(),
                            totalPrice = recalcTotalPrice(updatedPersonnel, selectedOptionIds)
                        )
                        Log.d("CartScreen", "ChangedCartItem onIncrease: $changedCartItem")
                        viewModel.updateCartItem(token, item.cartId, changedCartItem)
                        isBottomSheetVisible = false
                    },
                    onReviewButtonClicked = {
                        Log.d("CartScreen", "Review button clicked in bottom sheet")
                        /* 리뷰 보기 로직 */
                    },
                    onOptionClicked = { optionId ->
                        // 옵션 선택/해제 로직
                        val updatedOptions = if (item.selectAddOptions.any { it.selectOptionId == optionId }) {
                            item.selectAddOptions.filterNot { it.selectOptionId == optionId }
                        } else {
                            val selectedOption = item.addOptions.find { it.id == optionId }
                            if (selectedOption != null) {
                                item.selectAddOptions + SelectAddOption(
                                    selectOptionId = selectedOption.id,
                                    selectOptionName = selectedOption.name,
                                    selectOptionPrice = selectedOption.price
                                )
                            } else {
                                item.selectAddOptions
                            }
                        }
                        val updatedOptionIds = updatedOptions.map { it.selectOptionId }.toSet()
                        val changedCartItem = ChangedCartItem(
                            personnel = item.personnel,
                            addOptions = updatedOptionIds.toList(),
                            totalPrice = recalcTotalPrice(item.personnel, updatedOptionIds)
                        )
                        Log.d("CartScreen", "ChangedCartItem onOptionClicked: $changedCartItem")
                        viewModel.updateCartItem(token, item.cartId, changedCartItem)
                    },
                    onDeleteClick = {
                        Log.d("CartScreen", "Delete button clicked in bottom sheet")
                        onDeleteCartItem(it)
                        isBottomSheetVisible = false
                    },
                    onOptionChangeClick = { updatedCartItem: ChangedCartItem ->
                        // 업데이트 할 때 최신 상태 반영
                        Log.d("CartScreen", "OptionChangeClick: $updatedCartItem")
                        viewModel.updateCartItem(token, item.cartId, updatedCartItem)
                        isBottomSheetVisible = false
                    },
                    onClose = {
                        Log.d("CartScreen", "Close button clicked in bottom sheet")
                        isBottomSheetVisible = false
                    },
                    onConfirm = {
                        // 확인 시 ChangedCartItem 전송
                        val changedCartItem = ChangedCartItem(
                            personnel = item.personnel,
                            addOptions = selectedOptionIds.toList(),
                            totalPrice = recalcTotalPrice(item.personnel, selectedOptionIds)
                        )
                        Log.d("CartScreen", "ChangedCartItem onConfirm: $changedCartItem")
                        viewModel.updateCartItem(token, item.cartId, changedCartItem)
                        isBottomSheetVisible = false
                    },
                    selectedOptionChanged = { optionId ->
                        selectedOptionIds = if (selectedOptionIds.contains(optionId)) {
                            selectedOptionIds - optionId
                        } else {
                            selectedOptionIds + optionId
                        }
                        Log.d("CartScreen", "SelectedOptionIds after change: $selectedOptionIds")
                    },

                )
            }
        }
    }
}
