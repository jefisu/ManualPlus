package com.jefisu.manualplus.features_auth.presentation.sign_in.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jefisu.manualplus.BuildConfig
import com.jefisu.manualplus.R
import com.jefisu.manualplus.core.util.Resource
import com.jefisu.manualplus.core.util.UiText
import com.jefisu.manualplus.core.components.SignButton
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState

@Composable
fun GoogleButton(
    onResult: (Resource<String>) -> Unit,
    clickIsEnabled: Boolean,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    val oneTapSignInState = rememberOneTapSignInState()

    OneTapSignInWithGoogle(
        state = oneTapSignInState,
        clientId = BuildConfig.GOOGLE_CLIENT_ID,
        onTokenIdReceived = { token ->
            onResult(
                Resource.Success(token)
            )
        },
        onDialogDismissed = { error ->
            onResult(
                Resource.Error(UiText.DynamicString(error))
            )
        }
    )

    SignButton(
        text = stringResource(R.string.sign_in_with, "Google"),
        iconLeading = R.drawable.google_logo,
        onClick = oneTapSignInState::open,
        isLoading = isLoading,
        clickIsEnabled = clickIsEnabled,
        modifier = modifier
    )
}