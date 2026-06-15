package com.pinkcompose.presentation.recipe_category

import com.pinkcompose.domain.model.RecipeCategory

data class RecipeCategoryUiState(
    val isLoading: Boolean = false,
    val recipeCategory: List<RecipeCategory> = emptyList(),
    val error: String? = null
)
