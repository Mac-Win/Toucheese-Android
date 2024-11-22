package com.example.toucheeseapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.toucheeseapp.ui.components.FilterChipComponent
import com.example.toucheeseapp.ui.components.StudioListItemComponent
import com.example.toucheeseapp.ui.components.TopAppBarComponent
import com.example.toucheeseapp.ui.theme.ToucheeseAppTheme
import com.example.toucheeseapp.ui.viewmodel.StudioViewModel

@Composable
fun StudioListScreen(viewModel: StudioViewModel = hiltViewModel(), conceptId: Int, onClickLeadingIcon: () -> Unit, onClickTrailingIcon: () -> Unit) {
    var selectedIndex by remember { mutableStateOf(-1) }
    var selectedFilter by remember { mutableStateOf("") }
    val studios by viewModel.studios.collectAsState()
    Log.d("StudioListScreen", "불러온 스튜디오 리스트 조회 ${studios}")

    LaunchedEffect(conceptId) {
        viewModel.loadStudiosByConcept(conceptId)
    }

    Scaffold(
        topBar = {
            TopAppBarComponent(
                onClickLeadingIcon = onClickLeadingIcon,
                onClickTrailingIcon = onClickTrailingIcon,
            )
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
                items(studios){
                    StudioListItemComponent(it, true)
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