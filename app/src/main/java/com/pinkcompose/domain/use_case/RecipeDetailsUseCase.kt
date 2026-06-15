package com.pinkcompose.domain.use_case

import com.pinkcompose.domain.model.RecipeDetails
import com.pinkcompose.domain.repository.RecipeRepository
import com.pinkcompose.util.Resource
import javax.inject.Inject

class RecipeDetailsUseCase@Inject constructor(private val recipeRepository: RecipeRepository) {
    suspend operator fun invoke(recipeId: Int): Resource<RecipeDetails> {
        return recipeRepository.getRecipeDetails(recipeId)
    }
}