package com.jefisu.manualplus.features_manual.presentation.detail

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jefisu.manualplus.core.ui.theme.spacing
import com.jefisu.manualplus.core.util.ProfileTransitions
import com.jefisu.manualplus.features_manual.domain.Equipment
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.glide.GlideImage

@Destination(style = ProfileTransitions::class)
@Composable
fun DetailScreen(
    equipment: Equipment,
    imageEquipment: Uri?,
    navigator: DestinationsNavigator
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = MaterialTheme.spacing.small)
        ) {
            IconButton(onClick = navigator::popBackStack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(217.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(150.dp, 217.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFC9C9C9))
                ) {
                    GlideImage(
                        imageModel = { imageEquipment },
                        failure = {
                            Text(
                                text = "No image",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier.align(Alignment.Center),
                            )
                        },
                        modifier = Modifier
                            .padding(MaterialTheme.spacing.medium)
                            .fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium + 4.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = MaterialTheme.spacing.extraSmall)
                ) {
                    Text(
                        text = equipment.name,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "N॰ ${equipment.serialNumber}",
                        fontSize = 14.sp
                    )
                    Text(
                        text = equipment.description,
                        color = Color(0xFF888888),
                        textAlign = TextAlign.Justify,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        lineHeight = 20.sp,
                        maxLines = 7
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Text(
                text = "Setup step by step",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            equipment.steps.forEachIndexed { index, step ->
                Text(
                    text = buildAnnotatedString {
                        append(text = "${index + 1}. ")
                        withStyle(
                            style = SpanStyle(color = Color(0xFF888888))
                        ) {
                            append(text = step)
                        }
                    },
                    fontSize = 14.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = MaterialTheme.spacing.medium + 4.dp)
                )
            }
        }
    }
}