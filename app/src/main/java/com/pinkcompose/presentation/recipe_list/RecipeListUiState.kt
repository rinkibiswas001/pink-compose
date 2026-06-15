package com.pinkcompose.presentation.recipe_list

import com.pinkcompose.domain.model.RecipeList

data class RecipeListUiState(
    val isLoading: Boolean = false,
    val recipeList: List<RecipeList> = emptyList(),
    val error: String? = null
)
