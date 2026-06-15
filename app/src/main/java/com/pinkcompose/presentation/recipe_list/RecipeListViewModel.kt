package com.pinkcompose.presentation.recipe_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinkcompose.domain.use_case.RecipeListUseCase
import com.pinkcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeListUseCase: RecipeListUseCase, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _recipeList = MutableStateFlow(RecipeListUiState())
    val recipeList: StateFlow<RecipeListUiState> = _recipeList

    init {
        // Read categoryId from nav arguments — must match the key used in NavHost
        val categoryId: Int = savedStateHandle.get<Int>("categoryId") ?: 0
        getRecipeList(categoryId)
    }

    fun getRecipeList(categoryId: Int) {
        viewModelScope.launch {
            _recipeList.value = _recipeList.value.copy(isLoading = true)
            when (val result = recipeListUseCase(categoryId)) {
                is Resource.Success -> {
                    _recipeList.value = _recipeList.value.copy(
                        isLoading = false,
                        recipeList = result.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _recipeList.value = _recipeList.value.copy(
                        isLoading = false,
                        error = result.message ?: "An unknown error occurred"
                    )
                }

                is Resource.Loading -> {
                    _recipeList.value = _recipeList.value.copy(isLoading = true)
                }
            }
        }
    }
}