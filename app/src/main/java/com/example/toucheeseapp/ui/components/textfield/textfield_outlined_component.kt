package com.example.toucheeseapp.ui.components.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TextFieldOutlinedComponent(
    textFieldValue: String,
    placeholder: String,
    leadingIcon: ImageVector,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit,
) {
    OutlinedTextField(
        value = textFieldValue,
        onValueChange = onValueChanged,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
        ),
        placeholder = {
            Text(
                text = placeholder,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = "id"
            )
        },
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )

}