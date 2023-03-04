package com.jefisu.manualplus.features_auth.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.manualplus.core.connectivity.ConnectivityObserver
import com.jefisu.manualplus.core.util.Resource
import com.jefisu.manualplus.core.util.UiEvent
import com.jefisu.manualplus.core.util.UiText
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
class SignInViewModel @Inject constructor(
    private val authClient: AuthClient,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
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
        _state.update {
            it.copy(
                email = value
            )
        }
    }

    fun enterPassword(value: String) {
        _state.update {
            it.copy(
                password = value
            )
        }
    }

    fun login() {
        viewModelScope.launch {
            if (_noInternet.value) {
                _uiEvent.send(UiEvent.ShowError(UiText.internetUnavailable()))
                return@launch
            }
            if (_state.value.email.isBlank() || _state.value.password.isBlank()) {
                _uiEvent.send(
                    UiEvent.ShowError(UiText.DynamicString("Fields cannot be left blank."))
                )
                return@launch
            }

            _state.update { it.copy(isLoading = true) }
            val result = authClient.signIn(_state.value.email, _state.value.password)
            _uiEvent.send(
                when (result) {
                    is Resource.Success -> UiEvent.Navigate()
                    is Resource.Error -> UiEvent.ShowError(result.uiText)
                }
            )
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun loginWithGoogle(response: Resource<String>) {
        viewModelScope.launch {
            if (_noInternet.value) {
                _uiEvent.send(UiEvent.ShowError(UiText.internetUnavailable()))
                return@launch
            }

            _state.update { it.copy(isLoadingGoogleButton = true) }
            when (response) {
                is Resource.Error -> {
                    _uiEvent.send(
                        UiEvent.ShowError(response.uiText)
                    )
                }
                is Resource.Success -> {
                    val result = authClient.signInGoogle(response.data!!)
                    _uiEvent.send(
                        when (result) {
                            is Resource.Success -> UiEvent.Navigate()
                            is Resource.Error -> UiEvent.ShowError(result.uiText)
                        }
                    )
                }
            }
            _state.update { it.copy(isLoadingGoogleButton = false) }
        }
    }
}