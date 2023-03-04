package com.jefisu.manualplus.core.di

import android.app.Application
import com.jefisu.manualplus.BuildConfig
import com.jefisu.manualplus.core.connectivity.ConnectivityObserver
import com.jefisu.manualplus.core.connectivity.NetworkConnectivityObserver
import com.jefisu.manualplus.features_manual.data.ManualRepositoryImpl
import com.jefisu.manualplus.features_manual.domain.ManualRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.mongodb.App
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideConnectivityObserver(app: Application): ConnectivityObserver {
        return NetworkConnectivityObserver(app)
    }

    @Provides
    @Singleton
    fun provideRealmApp(): App {
        val mongoAppId = BuildConfig.MONGO_APP_ID
        return App.create(mongoAppId)
    }

    @Provides
    @Singleton
    fun provideManualRepository(realmApp: App): ManualRepository {
        return ManualRepositoryImpl(realmApp)
    }
}