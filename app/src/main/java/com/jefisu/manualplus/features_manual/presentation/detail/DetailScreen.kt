package com.jefisu.manualplus.features_manual.presentation.detail

import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.jefisu.manualplus.R
import com.jefisu.manualplus.core.ui.theme.seed
import com.jefisu.manualplus.core.ui.theme.spacing
import com.jefisu.manualplus.core.util.ProfileTransitions
import com.jefisu.manualplus.features_manual.domain.Equipment
import com.jefisu.manualplus.features_manual.presentation.detail.components.ListSteps
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMotionApi::class)
@Destination(style = ProfileTransitions::class)
@Composable
fun DetailScreen(
    equipment: Equipment,
    imageEquipment: Uri?,
    navigator: DestinationsNavigator
) {
    var showSeeMore by remember { mutableStateOf(false) }
    var animateToEnd by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (animateToEnd) 1f else 0f,
        animationSpec = tween(2000),
        label = ""
    )

    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene_collapse)
            .readBytes()
            .decodeToString()
    }

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val contentProp by motionProperties("content")
        val navigationIconProp by motionProperties("navigation_icon")
        val equipmentNameProp by motionProperties("equipment_name")

        Box(
            modifier = Modifier
                .layoutId(contentProp.id())
                .clip(CutCornerShape(topStart = contentProp.distance("corner")))
                .background(seed)
        )
        IconButton(
            onClick = navigator::popBackStack,
            modifier = Modifier.layoutId(navigationIconProp.id())
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = navigationIconProp.color("icon_color")
            )
        }
        GlideImage(
            imageModel = { imageEquipment },
            failure = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color(0xFFC9C9C9))
                ) {
                    Text(
                        text = "No image",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            },
            modifier = Modifier
                .layoutId("image")
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = equipment.name,
            fontSize = equipmentNameProp.fontSize("fontSize"),
            color = equipmentNameProp.color("textColor"),
            modifier = Modifier.layoutId(equipmentNameProp.id())
        )
        Text(
            text = "N॰ ${equipment.serialNumber}",
            fontSize = 18.sp,
            color = seed,
            modifier = Modifier.layoutId("equipment_serie")
        )
        Text(
            text = equipment.description,
            color = Color(0xFF888888),
            textAlign = TextAlign.Justify,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            modifier = Modifier.layoutId("equipment_description"),
            maxLines = 7
        )
        Text(
            text = "Setup step by step",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.layoutId("text_setup_aux")
        )

        ListSteps(
            steps = equipment.steps,
            showAllList = animateToEnd,
            stepsNotDisplayed = { showSeeMore = it || animateToEnd }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .layoutId("see_more")
                .padding(horizontal = MaterialTheme.spacing.extraSmall)
                .clickable(
                    enabled = showSeeMore,
                    onClick = { animateToEnd = !animateToEnd }
                )
        ) {
            if (showSeeMore) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.layoutId("arrow_down")
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                Text(
                    text = if (animateToEnd) "See less" else "See more",
                    fontSize = 12.sp
                )
            }
        }
    }
}