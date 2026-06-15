package com.pinkcompose.domain.use_case

import com.pinkcompose.domain.model.RecipeList
import com.pinkcompose.domain.repository.RecipeRepository
import com.pinkcompose.util.Resource
import javax.inject.Inject

class RecipeListUseCase @Inject constructor(private val recipeRepository: RecipeRepository) {
    suspend operator fun invoke(categoryId: Int): Resource<List<RecipeList>> {
        return recipeRepository.getRecipeList(categoryId)
    }
}