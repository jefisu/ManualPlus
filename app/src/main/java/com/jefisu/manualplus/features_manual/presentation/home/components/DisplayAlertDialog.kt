package com.jefisu.manualplus.features_manual.presentation.home.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    isOpened: Boolean,
    onCloseDialog: () -> Unit,
    onConfirmClick: () -> Unit
) {
    if (isOpened) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(onClick = onConfirmClick) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onCloseDialog) {
                    Text(text = "No")
                }
            },
            onDismissRequest = onCloseDialog
        )
    }
}