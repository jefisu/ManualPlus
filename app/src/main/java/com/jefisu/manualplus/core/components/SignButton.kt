package com.jefisu.manualplus.core.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jefisu.manualplus.core.ui.theme.seed
import com.jefisu.manualplus.core.ui.theme.spacing

@Composable
fun SignButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = seed,
    heightButton: Dp = 50.dp,
    shape: Shape = RoundedCornerShape(16.dp),
    textSize: TextUnit = 14.sp,
    @DrawableRes iconLeading: Int? = null,
    isLoading: Boolean = false,
    clickIsEnabled: Boolean = true
) {
    Button(
        onClick = {
            if (!isLoading && clickIsEnabled) {
                onClick()
            }
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = Color.White
        ),
        shape = shape,
        modifier = Modifier
            .height(heightButton)
            .then(modifier)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(24.dp)
            )
        } else {
            if (iconLeading != null) {
                Image(
                    painter = painterResource(iconLeading),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            }
            Text(
                text = text,
                fontSize = textSize
            )
        }
    }
}