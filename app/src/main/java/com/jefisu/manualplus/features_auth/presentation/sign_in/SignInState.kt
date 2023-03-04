package com.jefisu.manualplus.features_auth.presentation.sign_in

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoadingGoogleButton: Boolean = false
)