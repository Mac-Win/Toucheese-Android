package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toucheeseapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopAppBarComponent(
    showLeadingIcon: Boolean = false,
    showTrailingIcon: Boolean = false,
    modifier: Modifier = Modifier,
    onClickLeadingIcon: () -> Unit,
    onClickTrailingICon: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxSize()
            ) {
                Text(
                    text = "장바구니",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.weight(1f),
                )
            }
        },
        navigationIcon = {
            if (showLeadingIcon) {
                IconButton(onClick = onClickLeadingIcon) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, // 뒤로가기 버튼
                        contentDescription = "Back"
                    )
                }
            } else {
                // 빈 공간 차지
                IconButton(
                    enabled = false,
                    onClick = {}
                ) { }
            }
        },
        actions = {
            if (showTrailingIcon) {
                IconButton(onClick = onClickTrailingICon) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart, // 장바구니 버튼
                        contentDescription = "Cart"
                    )
                }
            } else{
                // 빈 공간 차지
                IconButton(
                    enabled = false,
                    onClick = {}
                ) { }
            }
        }
    )
}
