package com.example.toucheeseapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(leadingIcon: Int = 0, title: String, trailingIcon: Int = 0) {
    CenterAlignedTopAppBar(
        navigationIcon = { },
        title = {
            Text(text = title)
        }
    )
}
