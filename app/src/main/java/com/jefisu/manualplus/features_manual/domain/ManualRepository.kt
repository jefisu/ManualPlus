package com.jefisu.manualplus.features_manual.domain

import com.jefisu.manualplus.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface ManualRepository {
    suspend fun setUpRealm()
    fun getCategories(): Flow<Resource<List<String>>>
    fun getEquipmentsByCategory(category: String): Flow<Resource<List<Equipment>>>
}