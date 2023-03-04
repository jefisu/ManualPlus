package com.jefisu.manualplus.features_manual.presentation.category

import android.net.Uri
import com.jefisu.manualplus.features_manual.domain.Equipment

data class CategoryUiState(
    val equipments: List<Pair<Equipment, Uri?>> = emptyList(),
    val isLoading: Boolean = false
)