package com.example.toucheeseapp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilterChipComponent(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    selectedFilter: String,
    onSelectedItemChange: (Int) -> Unit,
    onSelectedFilterChange: (String) -> Unit,
) {
    val color by animateColorAsState(
        targetValue = if (selectedIndex != -1) Color(0xFFFFFCF5) else Color(0xFFFFFFFF)
    )
    Card(modifier = modifier) {
        Column(
            // Animation
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessHigh,
                    )
                )
                .background(color = color)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, // 세로 기준 중앙 정렬
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                listOf("가격순", "인기순", "지역순").forEachIndexed { index, filterName ->
                    FilterChipItem(
                        filterName = filterName,
                        expanded = selectedIndex == index,
                        onClick = { onSelectedItemChange(index) }
                    )
                }
            }

            val filterList = when (selectedIndex) {
                0 -> listOf("전체", "10만원 미만", "20만원 미만", "20만원 이상")
                1 -> listOf("전체", "3.0점 이상", "4.0점 이상", "4.5점 이상")
                2 -> listOf("전체", "강남구", "서초구","송파구", "강서구", "마포구","영등포구", "강북구","용산구", "성동구")
                else -> emptyList()
            }
            LazyRow(
                modifier= Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(filterList) {
                    Box(
                        modifier= Modifier
                            .fillMaxWidth(0.25f)
                            .padding(4.dp)
                    ){
                        RadioButtonComponent(
                            selected = selectedFilter == it,
                            filterItemName = it,
                            onClick = {
                                onSelectedItemChange(-1)
                                onSelectedFilterChange(it)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterChipItem(
    filterName: String,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FilterChip(
        modifier = modifier,
        selected = expanded,
        shape = CircleShape,
        border = BorderStroke(
            2.dp,
            color = if (expanded) Color(0xFFFFC618) else Color(0xFFFFE38E)
        ),
        onClick = onClick,
        label = {
            Text(
                text = filterName,
                fontSize = 16.sp,
                color = Color.White
            )
        },
        trailingIcon = {
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.White
            )
        },
        elevation = FilterChipDefaults.filterChipElevation(
            elevation = 0.dp,
            pressedElevation = 8.dp,
        ),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color(0xFFFFE38E),
            selectedContainerColor = Color(0xFFFFC618)
        )
    )
}