package com.example.toucheeseapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.toucheeseapp.R
import com.example.toucheeseapp.data.model.concept_studio.Studio
import com.example.toucheeseapp.data.model.search_studio.SearchResponseItem
import com.example.toucheeseapp.ui.viewmodel.StudioViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: StudioViewModel = hiltViewModel(), onCardClick: () -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }
    val studios = viewModel.studios.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val searchResults by viewModel.searchStudios.collectAsState()
    // 데이터 수신 확인
    Log.d("HomeScreen", "${studios.value}")
    Log.d("UI State", "isSearching: $isSearching, searchResults: $searchResults")


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                ReusableTopBar(
                    showBackButton = false, // 홈 화면에서는 뒤로가기 버튼 없음
                    showCartButton = false  // 홈 화면에서는 장바구니 버튼 없음
                )
                SearchBar(viewModel) // 서치바를 탑바 아래에 추가
            }
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) {  innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 홈 화면 콘텐츠
            HomeContent(
                studios = studios,
                onCardClick = onCardClick,
                modifier = Modifier.fillMaxSize()
            )

            // 검색 결과 리스트 (조건부로 표시)
            if (isSearching) {
                SearchResultBox(
                    searchResults = searchResults,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                        .padding(horizontal = 16.dp)// 화면 상단에 정렬
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(viewModel: StudioViewModel) {
    var searchText by remember { mutableStateOf("") }
    TextField(
        value = searchText,
        onValueChange = { searchText = it
                        viewModel.searchStudios(searchText) }, // 사용자가 입력한 텍스트를 업데이트
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
                )
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFFFF2CC),
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp)
    )
}

@Composable
fun CardGrid(onCardClick: () -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 8.dp,
            top = 16.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(6) { index ->
            val cardData = listOf(
                Pair(R.drawable.image1, "생동감 있는"),
                Pair(R.drawable.image2, "플래쉬/유광"),
                Pair(R.drawable.image3, "흑백/블루 배우"),
                Pair(R.drawable.image4, "수채화 그림체"),
                Pair(R.drawable.image5, "내추럴 화보"),
                Pair(R.drawable.image6, "선명한")
            )[index]

            PhotoCard(imageRes = cardData.first, title = cardData.second, onCardClick = onCardClick)
        }
    }
}

@Composable
fun PhotoCard(imageRes: Int, title: String, onCardClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(188.dp), // 카드 전체 높이
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

@Composable
fun BottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = Color(0xFFFFF2CC) // 바텀 내비의 기본 배경색 설정
    ) {
        val items = listOf(
            BottomNavItem("홈", R.drawable.home_36px),
            BottomNavItem("예약일정", R.drawable.calendar_36px),
            BottomNavItem("문의하기", R.drawable.qna_36px),
            BottomNavItem("내정보", R.drawable.myinfo_36px)
        )

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon), // painterResource를 사용하여 아이콘 렌더링
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black, // 선택된 아이콘 색상
                    selectedTextColor = Color.Black, // 선택된 텍스트 색상
                    indicatorColor = Color(0xFFFFFCF5), // 선택된 탭 배경색 설정
                    unselectedIconColor = Color.Gray, // 선택되지 않은 아이콘 색상
                    unselectedTextColor = Color.Gray  // 선택되지 않은 텍스트 색상
                )
            )
        }
    }
}

data class BottomNavItem(val title: String, val icon: Int) // icon 타입을 Int로 유지

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
fun SearchResultBox(searchResults: List<SearchResponseItem>, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF2CC))
            .padding(16.dp)
    ) {
        if (searchResults.isEmpty()) {
            // 검색 결과가 없을 때 메시지 표시
            Text(
                text = "검색된 내용이 없습니다.",
                modifier = Modifier.align(Alignment.TopStart),
                fontSize = 16.sp,
                color = Color.Gray
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                searchResults.forEach { studio ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(studio.profileImage),
                                contentDescription = "${studio.name} 프로필 이미지",
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(Color.LightGray)
                                    .padding(8.dp)
                            )

                            Column(
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .fillMaxWidth()
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
                    }
                }
            }
        }
    }
}

@Composable
fun HomeContent(studios: State<List<Studio>>, onCardClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        CardGrid(onCardClick = onCardClick) // 기존 홈 콘텐츠 (카드 그리드)
    }
}