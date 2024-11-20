package com.example.toucheeseapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.toucheeseapp.ui.components.FilterChipComponent
import com.example.toucheeseapp.ui.components.StudioListItemComponent
import com.example.toucheeseapp.ui.components.TopAppBarComponent
import com.example.toucheeseapp.ui.theme.ToucheeseAppTheme

@Composable
fun StudioListScreen(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(-1) }
    var selectedFilter by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBarComponent()
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(it),
        ) {
            // 필터
            FilterChipComponent(
                selectedIndex= selectedIndex,
                selectedFilter = selectedFilter,
                onSelectedItemChange = { selectedIndex = if (selectedIndex == it) -1 else it },
                onSelectedFilterChange = {selectedFilter = it },
                modifier = Modifier.padding(16.dp)
            )

            // 스튜디오 리스트
            LazyColumn() {
                item {
                    StudioListItemComponent()
                }
            }

        }
    }

}

@Preview
@Composable
fun StudioListPreview() {
    ToucheeseAppTheme{
    }
}