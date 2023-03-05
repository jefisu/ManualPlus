package com.jefisu.manualplus.features_manual.presentation.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.jefisu.manualplus.core.util.Resource
import com.jefisu.manualplus.features_manual.domain.Equipment
import com.jefisu.manualplus.features_manual.domain.ManualRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: ManualRepository
) : ViewModel() {


    private val _state = MutableStateFlow(CategoryUiState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("category")?.let { category ->
            _state.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                repository.getEquipmentsByCategory(category).collect { result ->
                    if (result is Resource.Success) {
                        getImageFromEquipments(result.data!!)
                    }
                }
            }
        }
    }

    private fun getImageFromEquipments(equipments: List<Equipment>) {
        for (equipment in equipments) {
            FirebaseStorage.getInstance()
                .reference
                .child(equipment.imageRemotePath)
                .downloadUrl
                .addOnSuccessListener { uri ->
                    _state.update {
                        it.copy(equipments = it.equipments + (equipment to uri))
                    }
                }
                .addOnFailureListener { _ ->
                    _state.update {
                        it.copy(equipments = it.equipments + (equipment to null))
                    }
                }

            if (equipment == equipments.last()) {
                viewModelScope.launch {
                    delay(1000)
                    _state.update { state ->
                        state.copy(
                            equipments = state.equipments.sortedBy {
                                it.first.name.lowercase().take(1)
                            },
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}