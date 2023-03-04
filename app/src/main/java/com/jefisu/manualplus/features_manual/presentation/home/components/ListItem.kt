package com.jefisu.manualplus.features_manual.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jefisu.manualplus.core.ui.theme.spacing
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ListItem(
    text: String,
    image: Any?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    hasLoading: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF888888).copy(0.1f))
            .clickable(onClick = onClick)
            .padding(MaterialTheme.spacing.medium)
            .then(modifier)
    ) {
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                imageModel = { image },
                loading = {
                    if (hasLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                failure = {
                    Text(
                        text = "No image",
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Fit
                ),
                modifier = Modifier.matchParentSize()
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Text(
            text = text,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}