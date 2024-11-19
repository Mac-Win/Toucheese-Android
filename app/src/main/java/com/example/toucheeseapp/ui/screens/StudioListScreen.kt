package com.example.toucheeseapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.toucheeseapp.ui.components.TopAppBarComponent
import com.example.toucheeseapp.ui.theme.ToucheeseAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudioListScreen() {
    Scaffold(
        topBar = {
            TopAppBarComponent()
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {

        }
    }

}

@Preview
@Composable
fun StudioListPreview() {
    ToucheeseAppTheme{
        StudioListScreen()
    }
}