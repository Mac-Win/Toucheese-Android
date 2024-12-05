package com.example.toucheeseapp.ui.components.textbutton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextButtonCheckboxComponent(
    text: String,
    checked: Boolean,
    fontSize: Int,
    modifier: Modifier = Modifier,
    onCheckedChanged: (Boolean) -> Unit,
) {

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier,
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckedChanged(!checked)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFFFFC000),
                uncheckedColor = Color.Gray,
            ),
            modifier = modifier,
        )

        Text(
            text= text,
            fontSize = fontSize.sp,
            color = Color.Gray,
            modifier = modifier.padding(start = 4.dp)
        )


    }
}

@Preview
@Composable
private fun TextButtonCheckBoxPreview() {
    val (checked, setChecked) = remember { mutableStateOf(false) }
    Surface{
        TextButtonCheckboxComponent(
            text = "자동 로그인",
            checked = checked,
            fontSize = 24,
            onCheckedChanged = setChecked,
        )
    }
}