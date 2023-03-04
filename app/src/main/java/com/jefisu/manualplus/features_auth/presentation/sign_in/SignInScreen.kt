package com.jefisu.manualplus.features_auth.presentation.sign_in

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.jefisu.manualplus.destinations.SignUpScreenDestination
import com.jefisu.manualplus.features_auth.presentation.sign_in.components.GoogleButton
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState

@RootNavGraph(start = true)
@Destination(style = ProfileTransitions::class)
@Composable
fun SignInScreen(
    navController: NavController,
    messageBarState: MessageBarState,
    context: Context,
    focusManager: FocusManager,
    topColor: Color = seed,
    bottomColor: Color = md_theme_light_surface,
    viewModel: SignInViewModel = hiltViewModel()
) {
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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(bottomColor)
                    .clip(RoundedCornerShape(bottomEnd = 50.dp))
                    .background(topColor)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(
                    text = "Welcome Back",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(topColor)
                    .clip(RoundedCornerShape(topStart = 50.dp))
                    .background(bottomColor)
                    .padding(horizontal = MaterialTheme.spacing.medium + 8.dp)
                    .padding(
                        top = MaterialTheme.spacing.medium + 8.dp,
                        bottom = MaterialTheme.spacing.small
                    )
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
                    readOnly = state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                SignTextField(
                    value = state.password,
                    onTextChange = viewModel::enterPassword,
                    iconStart = R.drawable.lock,
                    hintText = stringResource(R.string.password),
                    imeAction = ImeAction.Done,
                    keyboardAction = focusManager::clearFocus,
                    isSearchField = true,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation(),
                    readOnly = state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(
                    text = stringResource(R.string.forgot_password),
                    color = seed,
                    modifier = Modifier.align(Alignment.End),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                SignButton(
                    text = stringResource(R.string.login),
                    isLoading = state.isLoading,
                    onClick = viewModel::login,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(
                        text = stringResource(R.string.don_t_have_an_account),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = stringResource(R.string.sign_up),
                        fontSize = 14.sp,
                        color = seed,
                        modifier = Modifier.clickable {
                            navController.navigate(SignUpScreenDestination)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.sign_in_with, ""),
                        color = seed,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                    Divider(color = seed)
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                GoogleButton(
                    isLoading = state.isLoadingGoogleButton,
                    clickIsEnabled = !state.isLoading && !state.isLoadingGoogleButton,
                    onResult = viewModel::loginWithGoogle,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}