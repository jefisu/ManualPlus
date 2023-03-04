package com.jefisu.manualplus.features_auth.data

import com.jefisu.manualplus.R
import com.jefisu.manualplus.core.util.Resource
import com.jefisu.manualplus.core.util.SimpleResource
import com.jefisu.manualplus.core.util.UiText
import com.jefisu.manualplus.features_auth.domain.AuthClient
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.exceptions.InvalidCredentialsException
import io.realm.kotlin.mongodb.exceptions.UserAlreadyExistsException

class MongoDbClient(
    private val realmApp: App
) : AuthClient {
    override suspend fun signInGoogle(token: String): SimpleResource {
        return try {
            val isLogged = realmApp.login(
                Credentials.jwt(token)
            ).loggedIn
            if (isLogged) Resource.Success(Unit) else Resource.Error(UiText.unknownError())
        } catch (e: InvalidCredentialsException) {
            Resource.Error(UiText.DynamicString(e.message.toString()))
        }
    }

    override suspend fun signIn(email: String, password: String): SimpleResource {
        return try {
            val isLogged = realmApp.login(
                Credentials.emailPassword(email, password)
            ).loggedIn
            if (isLogged) Resource.Success(Unit) else Resource.Error(UiText.unknownError())
        } catch (e: Exception) {
            if (e is InvalidCredentialsException) {
                Resource.Error(UiText.StringResource(R.string.invalid_e_mail_or_password))
            } else {
                Resource.Error(UiText.unknownError())
            }
        }
    }

    override suspend fun signUp(email: String, password: String): SimpleResource {
        return try {
            realmApp.emailPasswordAuth.registerUser(email, password)
            signIn(email, password)
        } catch (e: Exception) {
            if (e is UserAlreadyExistsException) {
                Resource.Error(UiText.StringResource(R.string.email_already_used_by_another_user))
            } else {
                Resource.Error(UiText.unknownError())
            }
        }
    }
}