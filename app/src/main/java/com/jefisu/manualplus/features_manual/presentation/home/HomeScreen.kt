package com.jefisu.manualplus.features_manual.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jefisu.manualplus.R
import com.jefisu.manualplus.core.ui.theme.spacing
import com.jefisu.manualplus.core.util.ProfileTransitions
import com.jefisu.manualplus.destinations.CategoryScreenDestination
import com.jefisu.manualplus.destinations.SignInScreenDestination
import com.jefisu.manualplus.features_manual.presentation.home.components.DisplayAlertDialog
import com.jefisu.manualplus.features_manual.presentation.home.components.ListItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate

@Destination(style = ProfileTransitions::class)
@Composable
fun HomeScreen(
    navController: NavController,
    onDataLoaded: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    var showSignOutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = categories) {
        if (categories.isNotEmpty()) {
            onDataLoaded()
        }
    }

    DisplayAlertDialog(
        title = stringResource(R.string.sign_out),
        message = stringResource(R.string.alert_sign_out_message),
        isOpened = showSignOutDialog,
        onCloseDialog = { showSignOutDialog = false },
        onConfirmClick = {
            viewModel.signOut()
            navController.backQueue.clear()
            navController.navigate(SignInScreenDestination)
        }
    )
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                title = { Text(text = stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = { showSignOutDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = MaterialTheme.spacing.medium)
        ) {
            items(categories) { category ->
                ListItem(
                    text = category.name,
                    image = category.image,
                    onClick = {
                        navController.navigate(
                            CategoryScreenDestination(category.name)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}