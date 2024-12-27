package com.toucheese.app.ui.screens.tab_Home

import android.os.Build
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.toucheese.app.data.model.home.calendar_studio.CalendarTimeResponseItem
import com.toucheese.app.data.model.home.carts_list.CartListResponseItem
import com.toucheese.app.data.model.home.carts_optionChange.ChangedCartItem
import com.toucheese.app.data.token_manager.TokenManager
import com.toucheese.app.ui.components.AppBarImageComponent
import com.toucheese.app.ui.components.CartItemComponent
import com.toucheese.app.ui.components.DatePickComponent
import com.toucheese.app.ui.components.ProductOrderOptionComponent
import com.toucheese.app.ui.components.SquareRadioButton
import com.toucheese.app.ui.components.calendar.CustomDatePickerComponent
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.max

@RequiresApi(Build.VERSION_CODES.O)
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
    // context
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    // 토큰 받아오기
    val token = tokenManager.getAccessToken()
    Log.d("CartScreen", "Current Token: $token")

    // `selectedOptionIds` 상태 변수 정의
    var selectedOptionIds by remember { mutableStateOf(setOf<Int>()) }
    // 옵션변경하는 카트 id
    var optionChangedCartId by remember { mutableStateOf(-1) }
    // 옵션변경하는 카트 아이템
    var optionChangedCartItem by remember { mutableStateOf<CartListResponseItem?>(null) }
    // `selectedItem`이 변경될 때마다 `selectedOptionIds` 업데이트
    LaunchedEffect(selectedItem) {
        selectedOptionIds =
            selectedItem?.selectAddOptions?.map { it.selectOptionId }?.toSet() ?: setOf()
        // 토큰에 해당하는 장바구니 목록
        viewModel.loadCartList(token)
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
    // 삭제 Dialog 상태
    var isDialogShowed by remember { mutableStateOf(false) }

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
                                borderColor = if (isAllItemClicked) Color.Black else Color(
                                    0xFFBFBFBF
                                ),
                                onClick = {
                                    if (isAllItemClicked) {
                                        // 전체 선택을 해제하는 경우
                                        selectedCartItem = emptySet()
                                        Toast.makeText(
                                            context,
                                            "전체 상품이 선택해제되었습니다",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        // 전체 선택을 설정하는 경우
                                        selectedCartItem = selectedCartItem.toMutableSet().apply {
                                            clear() // 초기화
                                            cartItems.forEach { cartItem -> add(cartItem.cartId) }
                                        }
                                        Toast.makeText(
                                            context,
                                            "전체 상품이 선택되었습니다",
                                            Toast.LENGTH_SHORT
                                        ).show()
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
                                enabled = selectedCartItem.isNotEmpty(),
                                shape = RoundedCornerShape(8.dp),
                                label = {
                                    Text(
                                        text = "선택 상품 삭제",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = if (selectedCartItem.isNotEmpty()) Color(0xFF1F1F1F) else Color(
                                            0xFFBFBFBF
                                        )
                                    )
                                },
                                border = BorderStroke(1.dp, Color(0xFFF0F0F0)),
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = Color(0xFFFAFAFA),
                                    labelColor = Color(0xFF1F1F1F)
                                ),
                                onClick = {
                                    // Dialog를 띄워준다
                                    isDialogShowed = true
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
                            onOptionChangeClicked = {
                                // 예약 변경 띄워줌
                                isBottomSheetVisible = true
                                // id 저장
                                optionChangedCartId = cartId
                                // 카트 아이템 저장
                                optionChangedCartItem = cartItem
                                Log.d("CartScreen", "선택된 아이템 : ${optionChangedCartItem}")
                                if (optionChangedCartItem != null){
                                    // 선택한 옵션 값 전달
                                    selectedOptionIds = selectedOptionIds.toMutableSet().apply {
                                        // 기존 선택 옵션에 새로운 옵션 추가
                                        optionChangedCartItem!!.selectAddOptions.forEach { item ->
                                            this.add(item.selectOptionId) // `this`는 MutableSet
                                        }
                                    }
                                }
                            },
                        )
                    }
                }
            }
        }
    )

    // 상품 삭제 시 나타나는 Dialog
    if (isDialogShowed) {
        Dialog(
            properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = true,
            ),
            onDismissRequest = { isDialogShowed = false }
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),

                    ) {
                    // 삭제 문구
                    Text(
                        text = "선택한 상품을 삭제하시겠습니까?",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 24.dp)
                    )

                    // 버튼
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // 아니오
                        Button(
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF3F3F3),
                                contentColor = Color(0xFF7A7A7A),
                            ),
                            modifier = Modifier.weight(1f),
                            onClick = { isDialogShowed = false }
                        ) {
                            Text(
                                text = "아니오",
                                textAlign = TextAlign.Center,

                                )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // 예
                        Button(
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color(0xFF1F1F1F),
                            ),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                isDialogShowed = false
                                // 선택 상품 제거
                                viewModel.deleteCartItem(
                                    token = token,
                                    cartIds = selectedCartItem.toList()
                                )
                                // 삭제 알림 Toast
                                Toast.makeText(context, "상품이 삭제되었습니다", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Text(
                                text = "예",
                                textAlign = TextAlign.Center,
                            )
                        }
                    }

                }
            }
        }

    }

    // 예약 변경
    if (optionChangedCartId != -1 && optionChangedCartItem != null && isBottomSheetVisible) {
        // 바텀시트 상태
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
            confirmValueChange = { sheetValue ->
                // 풀 확장 or 닫힘만 허용
                sheetValue.name == SheetValue.Expanded.name || sheetValue.name == SheetValue.Hidden.name
            }
        )

        // 옵션 변경 데이터 보유
        Log.d("CartScreen", "장바구니 추가 옵션 변경 전 조회 : ${selectedOptionIds}")
        // 선택한 옵션
        var selectedOption by remember { mutableStateOf(selectedOptionIds) }
        // 선택 인원
        var selectedPersonnel by remember { mutableIntStateOf(optionChangedCartItem!!.personnel) }
        // 총 가격
        var selectedTotalPrice by remember { mutableIntStateOf(optionChangedCartItem!!.totalPrice) }

        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            sheetState = sheetState,
            onDismissRequest = {
                isBottomSheetVisible = false
            }
        ) {
            LazyColumn {
                item {
                    AppBarImageComponent(
                        productName = optionChangedCartItem!!.productName, // 상품명
                        productInfo = "", // 상품 설명
                        productImage = optionChangedCartItem!!.productImage, // 상품 이미지,
                        showReviewButton = false,
                        reviewCount = 0,
                        onReviewButtonClicked = {},
                    )
                }
                item {
                    // 가격 & 옵션
                    ProductOrderOptionComponent(
                        productNumOfPeople = optionChangedCartItem!!.productStandard, // 기준 인원
                        productNumOfPeoplePrice = optionChangedCartItem!!.productPrice, // 기준 가격
                        productOptions = optionChangedCartItem!!.addOptions, // 추가 옵션
                        numOfPeople = selectedPersonnel,
                        reviewCount = 0, // 리뷰 갯수
                        isOverFlow = optionChangedCartItem!!.personnel > optionChangedCartItem!!.productStandard, // 화면에 표시된 인원수가 상품 기준인원보다 높은지 여부
                        isOnlyOne = optionChangedCartItem!!.productStandard == 1, // 기준 인원이 1명인지 여부
                        onDecreaseClicked = {
                            // 기준 인원보다 큰 경우에만 작동
                            if (selectedPersonnel > optionChangedCartItem!!.productStandard) {
                                selectedPersonnel - 1 // 클릭 시 인원 -1
                                selectedTotalPrice -= (selectedTotalPrice / optionChangedCartItem!!.productStandard)// 기준 인원 금액으로 감소
                            } else {
                                // Toast 메시지를 띄워줌
                                Toast.makeText(
                                    context,
                                    "기준 인원보다 적은 인원을 선택하실 수 없습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        onIncreaseClicked = {
                            selectedPersonnel++
                            selectedTotalPrice += (selectedTotalPrice / optionChangedCartItem!!.productStandard)
                        }, // 클릭 시 인원 +1
                        onReviewButtonClicked = { },
                        selectedOption = selectedOption,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onOptionClicked = { optionPrice ->
                            // 옵션 상품을 금액에 추가 및 제거한다
                            selectedTotalPrice += optionPrice
                        },
                        selectedOptionChanged = { index ->
                            selectedOption =
                                if (selectedOption.contains(index)) {
                                    selectedOption - index
                                } else {
                                    selectedOption + index
                                }
                        },
                    )

                    // 촬영날짜
                    DatePickComponent(
                        date = "${optionChangedCartItem!!.reservationDate} ${optionChangedCartItem!!.reservationTime}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onDateClicked = {
                            Toast.makeText(context, "촬영날짜는 변경할 수 없습니다", Toast.LENGTH_SHORT).show()
                        },
                    )

                    // 옵션 변경하기 버튼
                    Button(
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color(0xFF1F1F1F)
                        ),
                        onClick = {
                            // 서버에 변경된 옵션 전송
                            viewModel.updateCartItem(
                                token = token,
                                cartId = optionChangedCartId,
                                changedCartItem = ChangedCartItem(
                                    addOptions = selectedOption.toList(),
                                    personnel = selectedPersonnel,
                                    totalPrice = selectedTotalPrice,
                                )
                            )
                            // 새로운 장바구니 리스트 조회
                            viewModel.loadCartList(token)
                            // 데이터 초기화
                            optionChangedCartId = -1
                            optionChangedCartItem = null
                            // 창닫기
                            isBottomSheetVisible = false
                        },
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        Text(
                            text = "옵션 변경하기 (총 ${selectedTotalPrice / 1000},000원)",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

    }
}
