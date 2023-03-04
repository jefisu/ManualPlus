package com.jefisu.manualplus.features_auth.presentation.sign_up

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jefisu.manualplus.R
import com.jefisu.manualplus.core.components.SignButton
import com.jefisu.manualplus.core.components.SignTextField
import com.jefisu.manualplus.core.ui.theme.md_theme_light_surface
import com.jefisu.manualplus.core.ui.theme.seed
import com.jefisu.manualplus.core.ui.theme.spacing
import com.jefisu.manualplus.core.util.ProfileTransitions
import com.jefisu.manualplus.core.util.UiEvent
import com.jefisu.manualplus.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState

@Destination(style = ProfileTransitions::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    messageBarState: MessageBarState,
    context: Context,
    focusManager: FocusManager,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val topColor = seed
    val bottomColor = md_theme_light_surface
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.backQueue.clear()
                    navController.navigate(HomeScreenDestination)
                }
                is UiEvent.ShowError -> {
                    messageBarState.addError(
                        Exception(event.uiText?.asString(context))
                    )
                }
            }
        }
    }

    ContentWithMessageBar(messageBarState = messageBarState) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(bottomColor)
                    .background(
                        color = topColor,
                        shape = RoundedCornerShape(bottomEnd = 50.dp)
                    )
                    .padding(horizontal = 16.dp)
            ) {
                IconButton(
                    onClick = navController::popBackStack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Text(
                    text = stringResource(R.string.create_account),
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(topColor)
                    .background(
                        color = bottomColor,
                        shape = RoundedCornerShape(topStart = 50.dp)
                    )
                    .padding(top = 34.dp)
                    .padding(horizontal = MaterialTheme.spacing.medium)
            ) {
                SignTextField(
                    value = state.email,
                    onTextChange = viewModel::enterEmail,
                    iconStart = R.drawable.email,
                    hintText = stringResource(R.string.email_address),
                    imeAction = ImeAction.Next,
                    keyboardAction = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                    error = state.emailError?.asString(),
                    isSearchField = true,
                    readOnly = state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                SignTextField(
                    value = state.password,
                    onTextChange = viewModel::enterPassword,
                    iconStart = R.drawable.lock,
                    hintText = stringResource(R.string.password),
                    imeAction = ImeAction.Next,
                    keyboardAction = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                    error = state.passwordError?.asString(),
                    isSearchField = true,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation(),
                    readOnly = state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                SignTextField(
                    value = state.repeatedPassword,
                    onTextChange = viewModel::enterConfirmPassword,
                    iconStart = R.drawable.lock,
                    hintText = stringResource(R.string.confirm_password),
                    imeAction = ImeAction.Done,
                    keyboardAction = focusManager::clearFocus,
                    error = state.repeatedPasswordError?.asString(),
                    isSearchField = true,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation(),
                    readOnly = state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                SignButton(
                    text = stringResource(R.string.create_account),
                    onClick = viewModel::createAccount,
                    isLoading = state.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            }
        }
    }
}