package com.toucheese.app.ui.screens.tab_Home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.toucheese.app.R
import com.toucheese.app.data.model.concept_studio.Studio
import com.toucheese.app.data.model.search_studio.SearchResponseItem
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
    val studios = viewModel.studios.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val searchResults by viewModel.searchStudios.collectAsState()
    var searchText by remember { mutableStateOf("") }
    // 데이터 수신 확인
    Log.d("HomeScreen", "${studios.value}")
    Log.d("HomeScreen", "isSearching: $isSearching, searchResults: $searchResults")


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                ReusableTopBar(
                    showBackButton = false, // 홈 화면에서는 뒤로가기 버튼 없음
                    showCartButton = false  // 홈 화면에서는 장바구니 버튼 없음
                )
                SearchBar(
                    searchText = searchText,
                    setText = { searchText = it },
                    viewModel = viewModel,
                    keyboardOptions = KeyboardOptions().copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                ) // 서치바를 탑바 아래에 추가
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
        ) {
            // 홈 화면 콘텐츠
            HomeContent(
                studios = studios,
                onCardClick = {
                    // 카드 클릭 시 선택된 스튜디오 컨셉 id 전달
                    onCardClick(it)
                    // 검색창 닫아주기
                    viewModel.stopSearch(isSearching)
                    // 검색 내용 클리어
                    searchText = ""
                },
                modifier = Modifier.fillMaxSize()
            )

            // 검색 결과 리스트 (조건부로 표시)
            if (isSearching) {
                SearchResultBox(
                    searchResults = searchResults,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.TopStart)// 화면 상단에 정렬
                        .background(Color(0xFFFFF2CC)),
                    onRowClick = { studio ->
                        // studioId와 address를 추출
                        val studioId = studio.id
                        // 검색창 닫아주기
                        viewModel.stopSearch(isSearching)
                        // 검색 내용 클리어
                        searchText = ""
                        // StudioDetailScreen으로 이동
                        onStudioClick(studioId)
                    }
                )
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
                imageVector = Icons.Default.Menu, // 햄버거 메뉴 아이콘
                contentDescription = "Menu Icon",
                tint = Color.Gray
            )
        },
        trailingIcon = { // 오른쪽 아이콘
            Icon(
                imageVector = Icons.Default.Search, // 돋보기 아이콘
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
        placeholder = {
            Text(
                text = "스튜디오 검색",
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
fun CardGrid(modifier: Modifier = Modifier, onCardClick: (Int) -> Unit) {
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
fun PhotoCard(
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
        shape = RoundedCornerShape(8.dp),
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
                painter = painterResource(id = R.drawable.toucheeselogo),
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
                    Image(
                        painter = rememberAsyncImagePainter(studio.profileImage),
                        contentDescription = "${studio.name} 프로필 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(50.dp))
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth(),
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
fun HomeContent(
    studios: State<List<Studio>>,
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