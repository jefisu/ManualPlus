package com.jefisu.manualplus.features_manual.presentation.detail.components

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListSteps(
    steps: List<String>,
    showAllList: Boolean,
    stepsNotDisplayed: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val heightText = 30.dp
    val space = 20.dp
    val lazyState = rememberLazyListState()

    LaunchedEffect(key1 = showAllList) {
        if (!showAllList && lazyState.firstVisibleItemIndex != 0) {
            lazyState.animateScrollToItem(0)
        }
    }

    BoxWithConstraints(modifier = modifier) {
        val numberStepVisible = remember(maxHeight) {
            if (showAllList) {
                steps.size
            } else {
                (maxHeight / (heightText + space)).toInt()
            }
        }

        LaunchedEffect(key1 = numberStepVisible) {
            stepsNotDisplayed((steps.size - numberStepVisible) > 0)
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(space),
            userScrollEnabled = showAllList,
            state = lazyState
        ) {
            val stepsVisible = steps.take(numberStepVisible)
            itemsIndexed(stepsVisible) { index, step ->
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(Color.White)
                        ) {
                            append(text = "${index + 1}. ")
                        }
                        withStyle(
                            style = SpanStyle(color = Color(0xFF888888))
                        ) {
                            append(text = step)
                        }
                    },
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = heightText)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewListSteps() {
    ListSteps(
        steps = (1..15).map {
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                    " Lorem Ipsum has been the industry's standard dummy text"
        },
        showAllList = false,
        stepsNotDisplayed = { n -> }
    )
}