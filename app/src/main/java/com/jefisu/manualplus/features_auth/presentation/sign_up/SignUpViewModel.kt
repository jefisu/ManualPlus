package com.jefisu.manualplus.features_auth.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.manualplus.core.connectivity.ConnectivityObserver
import com.jefisu.manualplus.core.util.Resource
import com.jefisu.manualplus.core.util.UiEvent
import com.jefisu.manualplus.core.util.UiText
import com.jefisu.manualplus.core.util.ValidateData
import com.jefisu.manualplus.features_auth.domain.AuthClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authClient: AuthClient,
     connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _noInternet = MutableStateFlow(true)

    init {
        connectivityObserver.observe().onEach { status ->
            _noInternet.update { status != ConnectivityObserver.Status.Available }
        }.launchIn(viewModelScope)
    }

    fun enterEmail(value: String) {
        val emailResult = ValidateData.validateEmail(_state.value.email)
        _state.update {
            it.copy(
                email = value,
                emailError = emailResult.error
            )
        }
    }

    fun enterPassword(value: String) {
        val passwordResult = ValidateData.validatePassword(value)
        _state.update {
            it.copy(
                password = value,
                passwordError = passwordResult.error
            )
        }
    }

    fun enterConfirmPassword(value: String) {
        val repeatedPasswordResult = ValidateData.validateRepeatedPassword(
            password = _state.value.password,
            repeatedPassword = value
        )
        _state.update {
            it.copy(
                repeatedPassword = value,
                repeatedPasswordError = repeatedPasswordResult.error
            )
        }
    }

    fun createAccount() {
        if (_noInternet.value) {
            viewModelScope.launch {
                _uiEvent.send(UiEvent.ShowError(UiText.internetUnavailable()))
            }
            return
        }
        if (_state.value.isLoading) {
            return
        }

        val emailResult = ValidateData.validateEmail(_state.value.email)
        val passwordResult = ValidateData.validatePassword(_state.value.password)
        val repeatedPasswordResult = ValidateData.validateRepeatedPassword(
            password = _state.value.password,
            repeatedPassword = _state.value.repeatedPassword
        )
        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult
        ).any { it.error != null }
        if (hasError) {
            _state.update {
                it.copy(
                    emailError = emailResult.error,
                    passwordError = passwordResult.error,
                    repeatedPasswordError = repeatedPasswordResult.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = authClient.signUp(_state.value.email, _state.value.password)
            _uiEvent.send(
                when (result) {
                    is Resource.Success -> UiEvent.Navigate()
                    is Resource.Error -> UiEvent.ShowError(result.uiText)
                }
            )
            _state.update { it.copy(isLoading = false) }
        }
    }
}