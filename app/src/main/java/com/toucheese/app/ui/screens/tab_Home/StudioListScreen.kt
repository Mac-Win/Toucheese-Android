package com.toucheese.app.ui.screens.tab_Home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.ui.components.FilterChipComponent
import com.toucheese.app.ui.components.StudioListItemComponent
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.HomeViewModel

@Composable
fun StudioListScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    conceptId: Int,
    onClickLeadingIcon: () -> Unit,
    onClickTrailingIcon: () -> Unit,
    onStudioItemClicked: (Int) -> Unit,
) {
    var selectedIndex by remember { mutableStateOf(-1) } // 선택된 chip Index
    var selectedFilters by remember { mutableStateOf(mapOf<Int, Int>()) } // 다중 선택을 위한 Set
    val studios by viewModel.studios.collectAsState()

    LaunchedEffect(conceptId) {
        viewModel.getConceptStudio(conceptId)
    }

    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = "검색",
                leadingIcon = Icons.AutoMirrored.Default.ArrowBack,
                trailingIcon = Icons.Default.ShoppingCart,
                showLeadingIcon = true,
                showTrailingIcon = true,
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
                selectedIndex = selectedIndex,
                selectedFilters = selectedFilters,
                onSelectedItemChange = { selectedIndex = if (selectedIndex == it) -1 else it },
                onSelectedFilterChange = { chipIndex, selectedFilterIndex ->
                    selectedFilters = selectedFilters.toMutableMap().apply {
                        this[chipIndex] = selectedFilterIndex
                    }
                    // 선택 후 자동으로 필터 닫힘
                    selectedIndex = -1
                    // 필터링 로직 호출
                    viewModel.filterStudio(conceptId, selectedFilters)
                    Log.d("Filter", "selectedFilters = ${selectedFilters}")
                },
                modifier = Modifier.padding(16.dp)
            )

            // 스튜디오 리스트
            LazyColumn() {
                items(studios) { studio ->
                    StudioListItemComponent(
                        studio = studio,
                        isMarked = true,
                        modifier = Modifier.clickable {
                            onStudioItemClicked(studio.id)
                        }
                    )
                }
            }

        }
    }

}