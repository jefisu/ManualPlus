package com.jefisu.manualplus.features_manual.domain

import android.net.Uri
import com.jefisu.manualplus.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface ManualRepository {
    suspend fun setUpRealm()
    fun getEquipments(): Flow<Resource<List<Equipment>>>
    fun getEquipmentsByCategory(category: String): Flow<Resource<List<Equipment>>>
}