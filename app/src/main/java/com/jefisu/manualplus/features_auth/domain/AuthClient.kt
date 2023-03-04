package com.jefisu.manualplus.features_auth.domain

import com.jefisu.manualplus.core.util.SimpleResource

interface AuthClient {
    suspend fun signInGoogle(token: String): SimpleResource
    suspend fun signIn(email: String, password: String): SimpleResource
    suspend fun signUp(email: String, password: String): SimpleResource
}