package com.pinkcompose.presentation.recipe_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinkcompose.domain.use_case.RecipeDetailsUseCase
import com.pinkcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val useCase: RecipeDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Recipe Steps
    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep

    fun nextStep(maxIndex: Int) {
        if (_currentStep.value < maxIndex) {
            _currentStep.value++
        }
    }

    fun previousStep() {
        if (_currentStep.value > 0) {
            _currentStep.value--
        }
    }

    // Recipe Details
    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState

    init {
        // Read recipeId from nav arguments — must match the key used in NavHost
        val recipeId: Int = savedStateHandle.get<Int>("recipeId") ?: 0
        recipeDetails(recipeId)
    }

    private fun recipeDetails(recipeId: Int) {
        viewModelScope.launch {
            _uiState.value = RecipeDetailsUiState(isLoading = true)
            when (val result = useCase(recipeId)) {
                is Resource.Success -> {
                    _uiState.value = RecipeDetailsUiState(recipeDetails = result.data)
                }
                is Resource.Error -> {
                    _uiState.value = RecipeDetailsUiState(error = result.message)
                }
                is Resource.Loading -> {
                    _uiState.value = RecipeDetailsUiState(isLoading = true)
                }
            }
        }
    }
}