package com.pinkcompose.presentation.recipe_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinkcompose.domain.use_case.RecipeCategoryUseCase
import com.pinkcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeCategoryViewModel @Inject constructor(private val useCase: RecipeCategoryUseCase) :
    ViewModel() {

    private val _recipeCategory = MutableStateFlow(RecipeCategoryUiState())
    val recipeCategory: StateFlow<RecipeCategoryUiState> = _recipeCategory

    init {
        getRecipeCategory()
    }

    fun getRecipeCategory() {
        viewModelScope.launch {
            _recipeCategory.value = _recipeCategory.value.copy(isLoading = true, error = null)
            when (val result = useCase()) {
                is Resource.Success -> {
                    _recipeCategory.value = _recipeCategory.value.copy(
                        isLoading = false, recipeCategory = result.data ?: emptyList(), error = null
                    )
                }

                is Resource.Error -> {
                    _recipeCategory.value = _recipeCategory.value.copy(
                        isLoading = false, error = result.message ?: "An unknown error occurred"
                    )
                }

                is Resource.Loading -> {
                    _recipeCategory.value = _recipeCategory.value.copy(
                        isLoading = true
                    )
                }
            }
        }
    }
}