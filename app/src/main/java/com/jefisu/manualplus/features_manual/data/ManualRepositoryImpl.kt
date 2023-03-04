package com.jefisu.manualplus.features_manual.data

import com.jefisu.manualplus.core.util.Resource
import com.jefisu.manualplus.core.util.UiText
import com.jefisu.manualplus.features_manual.domain.Equipment
import com.jefisu.manualplus.features_manual.domain.ManualRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.subscriptions
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ManualRepositoryImpl(
    app: App
) : ManualRepository {

    private lateinit var realm: Realm
    private val user = app.currentUser

    override suspend fun setUpRealm() {
        if (user != null) {
            val config =
                SyncConfiguration.Builder(user, setOf(EquipmentDto::class))
                    .initialSubscriptions { sub ->
                        add(sub.query<EquipmentDto>())
                    }
                    .build()
            realm = Realm.open(config)
            realm.subscriptions.waitForSynchronization()
        }
    }

    override fun getEquipments(): Flow<Resource<List<Equipment>>> {
        return try {
            realm
                .query<EquipmentDto>()
                .sort("name", Sort.ASCENDING)
                .asFlow()
                .map { result ->
                    Resource.Success(
                        result.list.map { it.toEquipment() }
                    )
                }
        } catch (_: Exception) {
            flowOf(Resource.Error(UiText.unknownError()))
        }
    }

    override fun getEquipmentsByCategory(category: String): Flow<Resource<List<Equipment>>> {
        return try {
            realm
                .query<EquipmentDto>("category == $0", category)
                .sort("name", Sort.ASCENDING)
                .asFlow()
                .map { result ->
                    Resource.Success(
                        result.list.map { it.toEquipment() }
                    )
                }
        } catch (e: Exception) {
            flowOf(Resource.Error(UiText.unknownError()))
        }
    }
}