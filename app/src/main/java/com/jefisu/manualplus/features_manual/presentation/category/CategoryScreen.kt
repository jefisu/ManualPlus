@file:OptIn(ExperimentalMaterialApi::class)

package com.jefisu.manualplus.features_manual.presentation.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jefisu.manualplus.core.ui.theme.spacing
import com.jefisu.manualplus.core.util.ProfileTransitions
import com.jefisu.manualplus.destinations.DetailScreenDestination
import com.jefisu.manualplus.features_manual.presentation.home.components.ListItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(style = ProfileTransitions::class)
@Composable
fun CategoryScreen(
    category: String,
    navigator: DestinationsNavigator,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = MaterialTheme.spacing.small)
        ) {
            IconButton(
                onClick = navigator::popBackStack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
            Text(
                text = category,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                items(state.equipments) { (equipment, imageUri) ->
                    ListItem(
                        text = equipment.name, image = imageUri, onClick = {
                            navigator.navigate(DetailScreenDestination(equipment, imageUri))
                        }, modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}