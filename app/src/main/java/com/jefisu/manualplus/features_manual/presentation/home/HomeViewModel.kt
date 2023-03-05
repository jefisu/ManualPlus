package com.jefisu.manualplus.features_manual.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.manualplus.core.util.Resource
import com.jefisu.manualplus.features_manual.domain.Category
import com.jefisu.manualplus.features_manual.domain.ManualRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ManualRepository,
    private val app: App
) : ViewModel() {


    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories = _categories.asStateFlow()

    init {
        viewModelScope.launch {
            repository.setUpRealm()
            repository.getCategories().collect { result ->
                if (result is Resource.Success) {
                    val categories = Category.values().filter { category ->
                        result.data!!.any { it == category.name }
                    }
                    _categories.update { categories }
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            app.currentUser?.logOut()
        }
    }
}