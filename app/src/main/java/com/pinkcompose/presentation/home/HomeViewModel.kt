package com.pinkcompose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinkcompose.domain.use_case.HomeUseCase
import com.pinkcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: HomeUseCase) : ViewModel() {

    private val _recipes = MutableStateFlow(HomeUiState())
    val recipes: StateFlow<HomeUiState> = _recipes

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _recipes.value = _recipes.value.copy(isLoading = true, error = null)

            // both run concurrently, not sequentially
            val categoryDeferred = async { useCase.getRecipeCategory() }
            val recipeListDeferred = async { useCase.getAllRecipes() }

            val categoryResult = categoryDeferred.await()
            val recipeListResult = recipeListDeferred.await()

            val categories = (categoryResult as? Resource.Success)?.data ?: emptyList()
            val recipeList = (recipeListResult as? Resource.Success)?.data ?: emptyList()

            val error = (categoryResult as? Resource.Error)?.message
                ?: (recipeListResult as? Resource.Error)?.message

            _recipes.value = _recipes.value.copy(
                isLoading = false,
                recipeCategory = categories,
                recipeList = recipeList,
                error = error
            )
        }
    }
}