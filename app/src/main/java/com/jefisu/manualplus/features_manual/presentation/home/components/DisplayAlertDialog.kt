package com.jefisu.manualplus.features_manual.presentation.home.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jefisu.manualplus.core.ui.theme.buttonColor
import com.jefisu.manualplus.core.ui.theme.seed

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
            backgroundColor = seed,
            shape = RoundedCornerShape(16.dp),
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
                Button(
                    onClick = onConfirmClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = buttonColor,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = onCloseDialog,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "No",
                        color = Color.White
                    )
                }
            },
            onDismissRequest = onCloseDialog
        )
    }
}