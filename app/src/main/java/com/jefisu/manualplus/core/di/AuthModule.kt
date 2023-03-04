package com.jefisu.manualplus.core.di

import com.jefisu.manualplus.features_auth.data.MongoDbClient
import com.jefisu.manualplus.features_auth.domain.AuthClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.realm.kotlin.mongodb.App

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    @ViewModelScoped
    fun provideAuthClient(realmApp: App): AuthClient {
        return MongoDbClient(realmApp)
    }
}