package com.pinkcompose.presentation.recipe_details

import com.pinkcompose.domain.model.RecipeDetails

data class RecipeDetailsUiState(
    val isLoading: Boolean = false,
    val recipeDetails: RecipeDetails? = null,
    val error: String? = null
)
