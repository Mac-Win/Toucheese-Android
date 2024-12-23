package com.toucheese.app.ui.screens.tab_Home

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.toucheese.app.R
import com.toucheese.app.data.model.home.search_studio.SearchResponseItem
import com.toucheese.app.ui.components.BottomNavigationBarComponent
import com.toucheese.app.ui.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    selectedTab: Int,
    viewModel: HomeViewModel = hiltViewModel(),
    onCardClick: (Int) -> Unit,
    onStudioClick: (Int) -> Unit,
    onTabSelected: (Int) -> Unit
) {
    val searchResults by viewModel.searchStudios.collectAsState()
    // 검색 내역
    val (searchText, setSearchText) = remember { mutableStateOf("") }
    // 검색 상태
    val (isSearching, setSearchState) = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                ReusableTopBar(
                    showBackButton = false, // 홈 화면에서는 뒤로가기 버튼 없음
                    showCartButton = false  // 홈 화면에서는 장바구니 버튼 없음
                )

                // Search
                Row(modifier = Modifier.fillMaxWidth().background(Color.White).padding(horizontal = 16.dp)) {
                    SuggestionChip(
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFF0F0F0)),
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = Color(0xFFFAFAFA),
                            labelColor = Color(0xFF595959)
                        ),
                        label = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    tint = Color(0xFF262626),
                                    contentDescription = null
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "스튜디오 찾기",
                                    color = Color(0xFF595959)
                                )
                            }
                        },
                        onClick = { setSearchState(true) }
                    )
                }
            }
        },
        bottomBar = {
            BottomNavigationBarComponent(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            // 홈 화면 콘텐츠
            HomeContent(
                onCardClick = {
                    // 카드 클릭 시 선택된 스튜디오 컨셉 id 전달
                    onCardClick(it)
                },
                modifier = Modifier.fillMaxSize()
            )

            // 검색 결과 리스트 (조건부로 표시)
            if (isSearching) {
                Dialog(
                    properties = DialogProperties(
                        dismissOnClickOutside = true,
                        dismissOnBackPress = true,
                    ),
                    onDismissRequest = {
                        // 다이얼로그 닫기
                        setSearchState(false)
                        // 검색 초기화
                        setSearchText("")
                        viewModel.stopSearch()
                    },
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clip(shape = RoundedCornerShape(12.dp))
                            .heightIn(min=300.dp)
                            .background(Color.White),
                    ) {
                        // SearchBar
                        OutlinedTextField(
                            value = searchText,
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    tint = Color(0xFF262626),
                                    contentDescription = null
                                )
                            },
                            placeholder = {
                                Text(
                                    text = "스튜디오 찾기",
                                    color = Color(0xFF595959)
                                )
                            },

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                focusedBorderColor = Color.White,
                                focusedLabelColor = Color(0xFFF0F0F0),
                                unfocusedContainerColor = Color.White,
                                unfocusedBorderColor = Color.White,
                                unfocusedLabelColor = Color(0xFFF0F0F0),
                                cursorColor = Color.Black,
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = { searchText ->
                                setSearchText(searchText)
                                viewModel.searchStudios(searchText) // 검색 API 호출
                            },
                        )

                        SearchResultBox(
                            searchResults = searchResults,
                            modifier = Modifier.fillMaxWidth(),
                            onRowClick = { studio ->
                                // studioId와 address를 추출
                                val studioId = studio.id
                                // 검색창 닫아주기
                                viewModel.stopSearch()
                                // 검색 내용 클리어
                                // StudioDetailScreen으로 이동
                                onStudioClick(studioId)
                            }
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: String,
    viewModel: HomeViewModel,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    setText: (String) -> Unit,
) {

    TextField(
        value = searchText,
        keyboardOptions = keyboardOptions,
        onValueChange = {
            setText(it)
            viewModel.searchStudios(it)
        }, // 사용자가 입력한 텍스트를 업데이트
        leadingIcon = { // 왼쪽 아이콘
            Icon(
                imageVector = Icons.Default.Search, // 검색 아이콘
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
        placeholder = {
            Text(
                text = "스튜디오 찾기",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp // 한글 텍스트가 잘리지 않도록 설정
                ),
                color = Color.Gray,
                modifier = Modifier.alpha(0.5f)
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFFFF2CC),
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp)

    )
}

@Composable
private fun CardGrid(modifier: Modifier = Modifier, onCardClick: (Int) -> Unit) {
    val cardData = listOf(
        Triple(R.drawable.image1, "생동감 있는", 1), // ID 추가
        Triple(R.drawable.image2, "플래쉬/유광", 2),
        Triple(R.drawable.image3, "흑백/블루 배우", 3),
        Triple(R.drawable.image6, "내추럴 화보", 4),
        Triple(R.drawable.image5, "선명한", 5),
        Triple(R.drawable.image4, "수채화 그림체", 6)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(cardData) { (imageRes, title, id) ->
            PhotoCard(
                imageRes = imageRes,
                title = title,
                modifier = modifier.fillMaxSize(),
                onCardClick = { onCardClick(id) }, // 클릭 시 ID 전달
            )
        }
    }
}

@Composable
private fun PhotoCard(
    imageRes: Int,
    title: String,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit
) {
    // 기기의 스크린 높이를 계산
    val screenHeight = LocalConfiguration.current.screenHeightDp
    // Card 높이를 기기 높이의 70%로 지정
    val cardHeight = (screenHeight * 0.7).dp
    Card(
        modifier = modifier
            .height(cardHeight / 3), // 카드 전체 높이
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = onCardClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // 메인 이미지
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // 텍스트와 블러 배경 처리
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp) // 텍스트 박스 높이
                    .align(Alignment.BottomCenter) // 하단 중앙에 배치
                    .background(Color.Black.copy(alpha = 0.5f)) // 투명한 검은색 배경 추가
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White, // 텍스트를 흰색으로 설정
                    modifier = Modifier
                        .align(Alignment.Center) // 텍스트를 중앙 정렬
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReusableTopBar(
    showBackButton: Boolean = false,
    showCartButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onCartClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.toucheese_logo),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                contentScale = ContentScale.Fit
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, // 뒤로가기 버튼
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (showCartButton) {
                IconButton(onClick = onCartClick) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart, // 장바구니 버튼
                        contentDescription = "Cart"
                    )
                }
            }
        }
    )
}

@Composable
fun SearchResultBox(
    searchResults: List<SearchResponseItem>,
    modifier: Modifier = Modifier,
    onRowClick: (SearchResponseItem) -> Unit // SearchResponseItem을 전달
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            searchResults.forEach { studio ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            onRowClick(studio) // SearchResponseItem 전달
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = studio.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = studio.address,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .alpha(0.5f),
                    color = Color.Gray,
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    onCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        CardGrid(onCardClick = onCardClick, modifier = modifier.fillMaxSize()) // 기존 홈 콘텐츠 (카드 그리드)
    }
}