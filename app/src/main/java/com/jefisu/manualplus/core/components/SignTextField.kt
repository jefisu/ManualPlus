package com.jefisu.manualplus.core.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jefisu.manualplus.R
import com.jefisu.manualplus.core.ui.theme.md_theme_light_outline
import com.jefisu.manualplus.core.ui.theme.md_theme_light_surfaceVariant
import com.jefisu.manualplus.core.ui.theme.spacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SignTextField(
    value: String,
    onTextChange: (String) -> Unit,
    @DrawableRes iconStart: Int,
    hintText: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    backgroundColor: Color = md_theme_light_surfaceVariant,
    isSearchField: Boolean = false,
    imeAction: ImeAction = ImeAction.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardAction: () -> Unit = {},
    error: String? = null,
    readOnly: Boolean = false
) {
    var hasFocus by remember { mutableStateOf(false) }
    val imeIsVisible = WindowInsets.isImeVisible
    val focusManager = LocalFocusManager.current
    val iconColor = when {
        error != null && value.isNotBlank() -> Color.Red
        value.isNotBlank() -> Color.Black
        else -> md_theme_light_outline
    }
    var showPassword by remember { mutableStateOf(false) }
    val isPassword = keyboardType == KeyboardType.Password

    LaunchedEffect(key1 = imeIsVisible) {
        if (!imeIsVisible) {
            hasFocus = false
            focusManager.clearFocus()
        }
    }

    LaunchedEffect(key1 = hasFocus) {
        if (readOnly && hasFocus) {
            focusManager.clearFocus()
        }
    }

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onTextChange,
            shape = shape,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = if (error != null) Color.Red else Color.Black,
                leadingIconColor = iconColor,
                backgroundColor = if (hasFocus) backgroundColor else Color.Transparent,
                placeholderColor = md_theme_light_outline,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorLeadingIconColor = iconColor,
                trailingIconColor = iconColor
            ),
            singleLine = true,
            keyboardActions = KeyboardActions { keyboardAction() },
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = keyboardType
            ),
            visualTransformation = if (showPassword) VisualTransformation.None else visualTransformation,
            placeholder = {
                Text(
                    text = hintText,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(iconStart),
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (isSearchField && value.isNotBlank()) {
                    IconButton(onClick = {
                        if (isPassword) {
                            showPassword = !showPassword
                        } else {
                            onTextChange("")
                        }
                    }) {
                        Icon(
                            imageVector = when {
                                isPassword -> if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility
                                else -> Icons.Default.Close
                            },
                            contentDescription = null,
                        )
                    }
                }
            },
            readOnly = readOnly,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    hasFocus = it.hasFocus
                }
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Preview
@Composable
fun PreviewSignTextField() {
    SignTextField(
        value = "",
        onTextChange = { },
        iconStart = R.drawable.email,
        hintText = "Email address",
    )
}