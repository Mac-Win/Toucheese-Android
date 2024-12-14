package com.example.toucheeseapp.ui.screens.tab_Qna

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toucheeseapp.ui.components.BottomNavigationBarComponent
import com.example.toucheeseapp.ui.components.topbar.TopAppBarComponent
import com.example.toucheeseapp.ui.viewmodel.QnaViewModel

@Composable
fun QnaScreen(
    selectedTab: Int,
    viewModel: QnaViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = "문의하기",
                showLeadingIcon = false,
                showTrailingIcon = false,
            )
        },

        bottomBar = {
            BottomNavigationBarComponent(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
            )
        }
    ) {

    }
}