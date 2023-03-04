package com.jefisu.manualplus.features_auth.presentation.sign_up

import com.jefisu.manualplus.core.util.UiText

data class SignUpState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: UiText? = null,
    val isLoading: Boolean = false
)