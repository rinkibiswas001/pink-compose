package com.pinkcompose.presentation.home

import com.pinkcompose.domain.model.RecipeCategory
import com.pinkcompose.domain.model.RecipeList

data class HomeUiState(
    val isLoading: Boolean = false,
    val recipeCategory: List<RecipeCategory> = emptyList(),
    val recipeList: List<RecipeList> = emptyList(),
    val error: String? = null
)
