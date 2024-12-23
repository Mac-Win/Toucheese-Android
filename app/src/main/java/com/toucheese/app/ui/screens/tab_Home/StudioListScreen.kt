package com.toucheese.app.ui.screens.tab_Home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.ui.components.SquareRadioButton
import com.toucheese.app.ui.components.StudioListItemComponent
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudioListScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    conceptId: Int,
    onClickLeadingIcon: () -> Unit,
    onClickTrailingIcon: () -> Unit,
    onStudioItemClicked: (Int) -> Unit,
) {
    // 가격순 필터 라벨
    var priceLabel by remember { mutableStateOf("가격순") }
    var isSelectedPrice by remember { mutableStateOf(priceLabel != "가격순") }
    // 인기순 필터 표시 라벨
    var ratingLabel by remember { mutableStateOf("인기순") }
    var isSelectedRating by remember { mutableStateOf(ratingLabel != "인기순") }
    // 지역순 필터 표시 라벨
    var locationLabel by remember { mutableStateOf("지역선택") }
    var isSelectedLocation by remember { mutableStateOf(locationLabel != "지역선택") }

    val studios by viewModel.studios.collectAsState()
    val conceptName by viewModel.conceptName.collectAsState()
    // BottomSheetModal 상태 관리
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutine = rememberCoroutineScope()
    LaunchedEffect(conceptId) {
        viewModel.getConceptStudio(conceptId)
        viewModel.loadConceptName(conceptId)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBarComponent(
                title = conceptName,
                leadingIcon = Icons.AutoMirrored.Default.ArrowBack,
                trailingIcon = Icons.Default.ShoppingCart,
                showLeadingIcon = true,
                showTrailingIcon = true,
                onClickLeadingIcon = onClickLeadingIcon,
                onClickTrailingIcon = onClickTrailingIcon,
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(it),
        ) {
            // 필터
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                // 필터를 선택한 경우
                if (isFilterSelected(priceLabel, ratingLabel, locationLabel)) {
                    item {
                        SuggestionChip(
                            shape = CircleShape,
                            label = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                )
                            },
                            modifier = Modifier.wrapContentSize(),
                            onClick = {
                                // 필터 초기화 메소드 호출
                                viewModel.filterStudio(
                                    conceptId = conceptId,
                                    selectedPrice = 0,
                                    selectedRating = 0,
                                    selectedLocations = setOf(0)
                                )
                                // 필터 초기화 로직
                                priceLabel = "가격순"
                                ratingLabel = "인기순"
                                locationLabel = "지역선택"
                                // 필터 선택 상태 초기화
                                isSelectedPrice = false
                                isSelectedRating = false
                                isSelectedLocation = false
                            },
                        )
                    }
                }

                // 가격순
                item {
                    FilterChip(
                        selected = isSelectedPrice,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary
                        ),
                        label = {
                            Text(text = priceLabel)
                        },
                        onClick = {
                            isBottomSheetVisible = true
                            coroutine.launch { bottomSheetState.expand() }
                        },
                        leadingIcon = {
                            if (isSelectedPrice) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }

                // 인기순
                item {
                    FilterChip(
                        selected = isSelectedRating,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary
                        ),
                        label = {
                            Text(text = ratingLabel)
                        },
                        onClick = {
                            isBottomSheetVisible = true
                            coroutine.launch { bottomSheetState.expand() }
                        },
                        leadingIcon = {
                            if (isSelectedRating) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }

                item {
                    FilterChip(
                        selected = isSelectedLocation,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary
                        ),
                        label = {
                            Text(text = locationLabel)
                        },
                        onClick = {
                            isBottomSheetVisible = true
                            coroutine.launch { bottomSheetState.expand() }
                        },
                        leadingIcon = {
                            if (isSelectedLocation) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }

            }

            // 스튜디오 리스트
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(studios) { studio ->
                    StudioListItemComponent(
                        studio = studio,
                        modifier = Modifier.clickable {
                            onStudioItemClicked(studio.id)
                        },
                    )
                }
            }
        }

        // Modal 바텀 시트
        if (isBottomSheetVisible) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp), // 바텀시트 모양 적용
                modifier = Modifier.wrapContentHeight(),
                onDismissRequest = { isBottomSheetVisible = false },
            ) {
                // 가격순
                var selectedPrice by remember { mutableStateOf(0) }
                // 인기순
                var selectedRating by remember { mutableStateOf(0) }
                // 지역 선택
                var selectedLocations by remember { mutableStateOf(setOf(0)) } // 선택된 지역의 Index를 저장
                // 인기 점수
                val ratings = listOf("전체", "3.0점 이상", "4.0점 이상", "4.5점 이상")
                // 가격
                val prices = listOf("전체", "10만원 미만", "20만원 미만", "20만원 이상")
                // 지역들
                val regions =
                    listOf("전체", "강남구", "서초구", "송파구", "강서구", "마포구", "영등포", "강북구", "용산구", "성동구")
                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(16.dp)
                ) {
                    // 필터 및 X 아이콘
                    item {
                        Row(Modifier.fillMaxWidth()) {
                            // 필터
                            Text(
                                text = "필터"
                            )
                        }
                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )
                        // 구분선
                        HorizontalDivider(thickness = 1.dp, color = Color(0xFFF0F0F0))
                    }

                    // 지역선택
                    item {
                        Text(
                            text = "지역 선택",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    // 지역선택 필터
                    item {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.heightIn(min = 150.dp, max = 300.dp)
                        ) {

                            itemsIndexed(regions) { index, localName ->
                                Row(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    //
                                    SquareRadioButton(
                                        selected = selectedLocations.contains(index),
                                        unselectedColor = Color.Transparent,
                                        onClick = {
                                            // 전체를 누른 경우
                                            if (index == 0) {
                                                // 이미 전체인 경우
                                                if (selectedLocations.contains(0)) {
                                                    selectedLocations = emptySet()
                                                } else {
                                                    selectedLocations = setOf(0) // 전체 클릭
                                                }
                                            }
                                            // 전체 이외의 버튼을 누른 경우
                                            else {
                                                // 전체가 눌러져 있는 경우
                                                if (selectedLocations.contains(0)) {
                                                    // 전체 버튼을 해제한다
                                                    selectedLocations =
                                                        selectedLocations.toMutableSet().apply {
                                                            remove(0) // 전체 해제
                                                            add(index) // 클릭한 요소 추가
                                                        }
                                                }
                                                // 전체가 안 눌러져 있는 경우
                                                else {
                                                    //
                                                    selectedLocations =
                                                        selectedLocations.toMutableSet().apply {
                                                            add(index)
                                                            // 선택하고 보니 나머지를 다 클릭한 경우
                                                            if (selectedLocations.size == 8) {
                                                                clear() // 선택한 지역을 비워주고
                                                                add(0) // 전체를 넣어준다
                                                            }
                                                        }

                                                }

                                            }
                                            Log.d(
                                                "StudioListScreen",
                                                "선택한 지역 필터 : ${selectedLocations.sorted()}"
                                            )
                                        }
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    // 지역
                                    Text(
                                        text = localName,
                                    )
                                }
                            }
                        }
                        // 구분선
                        HorizontalDivider(thickness = 1.dp, color = Color(0xFFF0F0F0))
                    }

                    // 인기
                    item {
                        Text(
                            text = "인기",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    // 인기 필터
                    itemsIndexed(ratings) { index, ratingName ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            RadioButton(
                                selected = selectedRating == index,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    unselectedColor = Color(0xFFBFBFBF)
                                ),
                                onClick = { selectedRating = index },
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = ratingName,
                            )
                        }
                    }

                    // 가격
                    item {
                        // 구분선
                        HorizontalDivider(thickness = 1.dp, color = Color(0xFFF0F0F0))

                        Text(
                            text = "가격",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    // 가격 필터
                    itemsIndexed(prices) { index, priceName ->
                        val isSelected = selectedPrice == index
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            RadioButton(
                                selected = isSelected,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    unselectedColor = Color(0xFFBFBFBF)
                                ),
                                onClick = { selectedPrice = index },
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = priceName,
                            )
                        }
                        Log.d("StudioListScreen", "선택한 가격 필터: ${selectedPrice}")
                    }

                    // 하단 버튼
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                        ) {
                            // 초기화 버튼
                            Button(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFFF0F0F0
                                    )
                                ),
                                onClick = {
                                    // 지역선택 초기화
                                    selectedLocations = setOf(0) // 전체
                                    // 인기 초기화
                                    selectedRating = 0
                                    // 가격 초기화
                                    selectedPrice = 0
                                }
                            ) {
                                Text(
                                    text = "초기화"
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // 적용하기 버튼
                            Button(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(2f),
                                onClick = {
                                    // 각 데이터를 모아 전송
                                    viewModel.filterStudio(
                                        conceptId = conceptId,
                                        selectedPrice = selectedPrice,
                                        selectedRating = selectedRating,
                                        selectedLocations = if (selectedLocations.isEmpty()) setOf(0) else selectedLocations
                                    )
                                    // 선택된 데이터 전달하기
                                    priceLabel =
                                        if (selectedPrice == 0) "가격순" else prices[selectedPrice]
                                    ratingLabel =
                                        if (selectedRating == 0) "인기순" else ratings[selectedRating]
                                    locationLabel =
                                        if (selectedLocations.isEmpty() || selectedLocations.contains(
                                                0
                                            )
                                        ) "지역선택" else if (selectedLocations.size == 1) regions[selectedLocations.sorted()[0]] else "${regions[selectedLocations.sorted()[0]]} · ${selectedLocations.size}"

                                    // 필터 선택 상태 적용
                                    isSelectedPrice = selectedPrice != 0
                                    isSelectedRating = selectedRating != 0
                                    isSelectedLocation = !selectedLocations.contains(0)

                                    // 바텀시트 닫기
                                    isBottomSheetVisible = false
                                    coroutine.launch { bottomSheetState.hide() }
                                }
                            ) {
                                Text(
                                    text = "적용하기"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun isFilterSelected(price: String, rating: String, location: String) =
    price != "가격순" || rating != "인기순" || location != "지역선택"